package com.sumit.notificationservice.controller;

import com.sumit.notificationservice.exception.NotificationError;
import com.sumit.notificationservice.exception.NotificationServiceException;
import com.sumit.notificationservice.model.BatchRequest;
import com.sumit.notificationservice.model.BatchResponse;
import com.sumit.notificationservice.model.Request;
import com.sumit.notificationservice.model.Response;
import com.sumit.notificationservice.service.NotificationService;
import com.sumit.notificationservice.util.EmailValidator;
import com.sumit.notificationservice.util.NotificationServiceHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.sumit.notificationservice.constants.NotificationServiceConstants.INVALID_REQUEST_PARAMETERS;

@RestController
@RequestMapping("/v1")
public class NotificationController {

    private static final Logger LOG = LogManager.getLogger(NotificationController.class);
    @Autowired
    EmailValidator emailValidator;
    @Autowired
    private NotificationService service;

    @PostMapping(value = "/notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchResponse> notify(@RequestBody BatchRequest request) throws ExecutionException, InterruptedException {


        BatchResponse batchResponse = new BatchResponse();
        if (request.getRequests() == null || request.getRequests().length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(batchResponse);
        }

        Response[] responses = new Response[request.getRequests().length];
        batchResponse.setResponses(responses);
        List<CompletableFuture<ResponseEntity<Response>>> futureList = new ArrayList<>();

        for (int i = 0; i < request.getRequests().length; i++) {
            try {

                if (!isRequestValid(request.getRequests()[i])) {
                    NotificationError notificationError = new NotificationError();
                    notificationError.setRetryAble(true);
                    notificationError.setHttpStatus(400);
                    notificationError.setError(INVALID_REQUEST_PARAMETERS);
                    LOG.error("Invalid request for channel={},from={},to={},subject={}",
                            request.getRequests()[i].getChannel(),
                            request.getRequests()[i].getFrom(),
                            request.getRequests()[i].getTo(),
                            request.getRequests()[i].getSubject()

                    );
                    throw new NotificationServiceException(notificationError);
                }

                CompletableFuture<ResponseEntity<Response>> future = service.notifyAsync(request.getRequests()[i].getChannel(), request.getRequests()[i]);
                futureList.add(future);
            } catch (NotificationServiceException e) {
                Response response = NotificationServiceHelper.getResponse(request.getRequests()[i], "fail");
                ResponseEntity<Response> rEntity = new ResponseEntity<Response>(response, HttpStatus.OK);
                futureList.add(CompletableFuture.completedFuture(rEntity));
            }
        }

        //Waiting for all threads to complete
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();

        int ind = 0;
        ResponseEntity<Response> response = null;
        for (CompletableFuture<ResponseEntity<Response>> future : futureList) {
            response = future.get();
            responses[ind++] = response.getBody();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(batchResponse);
    }


    private boolean isRequestValid(Request request) {
        return !StringUtils.isBlank(request.getChannel()) &&
                !StringUtils.isBlank(request.getFrom()) &&
                !StringUtils.isBlank(request.getTo()) &&
                !StringUtils.isBlank(request.getSubject()) &&
                !StringUtils.isBlank(request.getBody()) &&
                emailValidator.isValid(request.getFrom()) &&
                emailValidator.isValid(request.getTo())
                ;
    }

}
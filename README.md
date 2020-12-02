## Notification Service

## Tech Stack:
SpringBoot 
Maven


## Installation

1. Import project using mvn
2. Start the application

Usage:
 This spring boot service allow the user to send bulk request for multiple notification channel.
 Service is mocking the email and slack notification. For actual implementation you need to implement smtp and slack webhook.
 
URL: http://localhost:8080/v1/notify

## Postman Curl Request

```javascript 
curl --location --request POST 'http://localhost:8080/v1/notify' \
--header 'Content-Type: application/json' \
--data-raw '
{
"requests":[
	{
	"channel": "email",
	"to": "xyz@gmail.com",
	"from": "abc@gmail.com",
	"subject": "sub",
	"body": "body" 
	},
	{
	"channel": "slack",
	"to": "xyz@gmail.com",
	"from": "abc",
	"subject": "sub",
	"body": "body" 
	},
		{
	"channel": "email",
	"to": "xyz",
	"from": "abc1@gmail.com",
	"subject": "sub1",
	"body": "body1" 
	}
	]
}'
```


## Postman Curl Response

```javascript
{
    "responses": [
        {
            "result": "success",
            "uuid": "d151e0b1-64f3-4221-9ab4-116f629490e8",
            "from": "abc@gmail.com",
            "to": "xyz@gmail.com"
        },
        {
            "result": "fail",
            "uuid": "8dd7236f-cf3e-4be1-b125-fac5d91c1d32",
            "from": "abc",
            "to": "xyz@gmail.com"
        },
        {
            "result": "fail",
            "uuid": "cf984821-9310-4a9a-9a66-84af8737bbca",
            "from": "abc1@gmail.com",
            "to": "xyz"
        }
    ]
}
```
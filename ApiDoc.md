
# SDA Catalogue

Rest API for SDA Catalogue based on Spring Boot

<!--- If we have only one group/collection, then no need for the "ungrouped" heading -->


## Variables

| Key | Value | Type |
| --- | ------|-------------|
| url_local | localhost | string |
| port | 8080 | string |
| token |  | string |



## Endpoints

* [WebApp](#webapp)
    1. [Get WebApp](#1-get-webapp)
    1. [Get WebApp By UUID](#2-get-webapp-by-uuid)
    1. [Create WebApp](#3-create-webapp)
    1. [Update WebApp](#4-update-webapp)
    1. [Delete WebApp](#5-delete-webapp)
* [PIC Developer](#pic-developer)
    1. [GET PIC Developer](#1-get-pic-developer)
        * [Get PIC Developer](#i-example-request-get-pic-developer)
    1. [GET PIC Developer By UUID](#2-get-pic-developer-by-uuid)
        * [GET PIC Developer By UUID](#i-example-request-get-pic-developer-by-uuid)
    1. [CREATE PIC Developer](#3-create-pic-developer)
        * [CREATE PIC Developer](#i-example-request-create-pic-developer)
    1. [UPDATE PIC Developer](#4-update-pic-developer)
        * [UPDATE PIC Developer](#i-example-request-update-pic-developer)
    1. [DELETE PIC Developer](#5-delete-pic-developer)
        * [DELETE PIC Developer](#i-example-request-delete-pic-developer)
* [Type Database](#type-database)
    1. [GET Type Database](#1-get-type-database)
    1. [GET Type Database By UUID](#2-get-type-database-by-uuid)

--------



## WebApp



### 1. Get WebApp



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/web-app
```



***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



### 2. Get WebApp By UUID



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/web-app/3f1a6040-8979-49b8-8a77-5da7a47e75f9
```



### 3. Create WebApp



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/web-app
```



***Body:***

```js        
{
    "applicationName": "Nex Knowledge Management",
    "description": "TH Application for all employes",
    "functionApplication": "Untuk melakukan manajemen manpower agar lebih interaktif",
    "address": "https://nex-km.gmf-aeroasia.co.id",
    "dinas": "TH",
    "mappingFunction": "Base Maintenance",
    "businessImpactPriority": "Medium",
    "status": "Active",
    "sdaCloud": "Cloud"
}
```



### 4. Update WebApp



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/web-app/3f1a6040-8979-49b8-8a77-5da7a47e75f9
```



***Body:***

```js        
{
    "applicationName": "NEX Knowledge Management",
    "description": "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur",
    "functionApplication": "Untuk melakukan manajemen manpower agar lebih interaktif",
    "address": "https://nex-km.gmf-aeroasia.co.id",
    "dinas": "TH",
    "mappingFunction": "Base Maintenance",
    "businessImpactPriority": "High",
    "status": "Inactive",
    "sdaCloud": "Cloud"
}

```



### 5. Delete WebApp



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{url_local}}:{{port}}/api/v1/web-app/3f1a6040-8979-49b8-8a77-5da7a47e75f9
```



## PIC Developer



### 1. GET PIC Developer


This endpoint makes an HTTP GET request to retrieve a list of PIC developers. The request includes query parameters for searchTerm, page, and size. The searchTerm parameter can be used for filtering the results, while the page and size parameters are used for pagination.

The response returns a status indicating the success of the request, a message, and a result object containing an array of PIC developer data. Each developer object includes an ID, UUID, personal number, name, creation and update timestamps, and an empty webAppEntityList. Additionally, the response includes pagination details such as the size, total number of developers, and the current page.

Example response:

``` json
{
    "status": "OK",
    "message": "Successfully retrieved data pic developers!",
    "result": {
        "data": [
            {
                "idPicDeveloper": 4,
                "uuid": "8d63263e-153b-42a5-bea6-7de001ef5a56",
                "personalNumber": "123478",
                "personalName": "ROHMAT",
                "createdAt": "2023-12-16T01:16:17.112868",
                "updatedAt": "2023-12-16T01:16:17.112868",
                "webAppEntityList": []
            },
            {
                "idPicDeveloper": 3,
                "uuid": "e5d856ab-d869-4e61-bebf-832e4f863d08",
                "personalNumber": "879596",
                "personalName": "ADE",
                "createdAt": "2023-12-16T01:16:07.852277",
                "updatedAt": "2023-12-16T01:16:07.852277",
                "webAppEntityList": []
            },
            {
                "idPicDeveloper": 2,
                "uuid": "6c04e304-ede5-4f8f-8b62-4d257e6bbd2b",
                "personalNumber": "819266",
                "personalName": "ANTON",
                "createdAt": "2023-12-16T01:15:29.035862",
                "updatedAt": "2023-12-16T01:15:29.035862",
                "webAppEntityList": []
            }
        ],
        "page": {
            "size": 10,
            "total": 3,
            "current": 1
        }
    }
}

 ```


***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/pic-developer
```



***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



***More example Requests/Responses:***


#### I. Example Request: Get PIC Developer



***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



***Body: None***



#### I. Example Response: Get PIC Developer
```js
{
    "status": "OK",
    "message": "Successfully retrieved data pic developers!",
    "result": {
        "data": [
            {
                "idPicDeveloper": 4,
                "uuid": "8d63263e-153b-42a5-bea6-7de001ef5a56",
                "personalNumber": "123478",
                "personalName": "ROHMAT",
                "createdAt": "2023-12-16T01:16:17.112868",
                "updatedAt": "2023-12-16T01:16:17.112868",
                "webAppEntityList": []
            },
            {
                "idPicDeveloper": 3,
                "uuid": "e5d856ab-d869-4e61-bebf-832e4f863d08",
                "personalNumber": "879596",
                "personalName": "ADE",
                "createdAt": "2023-12-16T01:16:07.852277",
                "updatedAt": "2023-12-16T01:16:07.852277",
                "webAppEntityList": []
            },
            {
                "idPicDeveloper": 2,
                "uuid": "6c04e304-ede5-4f8f-8b62-4d257e6bbd2b",
                "personalNumber": "819266",
                "personalName": "ANTON",
                "createdAt": "2023-12-16T01:15:29.035862",
                "updatedAt": "2023-12-16T01:15:29.035862",
                "webAppEntityList": []
            }
        ],
        "page": {
            "size": 10,
            "total": 3,
            "current": 1
        }
    }
}
```


***Status Code:*** 200

<br>



### 2. GET PIC Developer By UUID



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/pic-developer/6c04e304-ede5-4f8f-8b62-4d257e6bbd2b
```



***More example Requests/Responses:***


#### I. Example Request: GET PIC Developer By UUID



***Body: None***



#### I. Example Response: GET PIC Developer By UUID
```js
{
    "status": "OK",
    "message": "Success retrieved data pic developer!",
    "result": {
        "idPicDeveloper": 2,
        "uuid": "6c04e304-ede5-4f8f-8b62-4d257e6bbd2b",
        "personalNumber": "819266",
        "personalName": "ANTON",
        "createdAt": "2023-12-16T01:15:29.035862",
        "updatedAt": "2023-12-16T01:15:29.035862",
        "webAppEntityList": []
    }
}
```


***Status Code:*** 200

<br>



### 3. CREATE PIC Developer



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/pic-developer
```



***Body:***

```js        
{
    "personalNumber": "819266",
    "personalName": "ANTON"
}
```



***More example Requests/Responses:***


#### I. Example Request: CREATE PIC Developer



***Body:***

```js        
{
    "personalNumber": "819266",
    "personalName": "ANTON"
}
```



#### I. Example Response: CREATE PIC Developer
```js
{
    "status": "OK",
    "message": "Success created data pic developer!",
    "result": {
        "idPicDeveloper": 1,
        "uuid": "c3108313-331c-49d7-9ee1-f8bea9e94df6",
        "personalNumber": "819266",
        "personalName": "ANTON",
        "createdAt": "2023-12-16T01:14:02.697",
        "updatedAt": "2023-12-16T01:14:02.697",
        "webAppEntityList": null
    }
}
```


***Status Code:*** 200

<br>



### 4. UPDATE PIC Developer



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/pic-developer/ebfdd34a-5afc-4a81-9b58-db31be3f12ed
```



***Body:***

```js        
{
    "personalNumber": "819266",
    "personalName": "ANTON"
}
```



***More example Requests/Responses:***


#### I. Example Request: UPDATE PIC Developer



***Body:***

```js        
{
    "personalNumber": "819266",
    "personalName": "ANTON"
}
```



#### I. Example Response: UPDATE PIC Developer
```js
{
    "status": "ACCEPTED",
    "message": "Success updated data pic developer!",
    "result": {
        "idPicDeveloper": 1,
        "uuid": "ebfdd34a-5afc-4a81-9b58-db31be3f12ed",
        "personalNumber": "ANTON",
        "personalName": "819266",
        "createdAt": "2023-12-16T01:09:03.467874",
        "updatedAt": "2023-12-16T01:09:03.467874",
        "webAppEntityList": []
    }
}
```


***Status Code:*** 202

<br>



### 5. DELETE PIC Developer



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{url_local}}:{{port}}/api/v1/pic-developer/21d930de-b5ea-4727-9c6c-cc3b3363b919
```



***More example Requests/Responses:***


#### I. Example Request: DELETE PIC Developer



***Body: None***



#### I. Example Response: DELETE PIC Developer
```js
{
    "status": "OK",
    "message": "Success deleted data pic developer!",
    "result": {
        "idPicDeveloper": 5,
        "uuid": "21d930de-b5ea-4727-9c6c-cc3b3363b919",
        "personalNumber": "819266",
        "personalName": "ANTON",
        "createdAt": "2023-12-16T02:09:39.666317",
        "updatedAt": "2023-12-16T02:09:39.666317",
        "webAppEntityList": []
    }
}
```


***Status Code:*** 200

<br>



## Type Database



### 1. GET Type Database



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/type-database
```



***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



### 2. GET Type Database By UUID



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/type-database/6c04e304-ede5-4f8f-8b62-4d257e6bbd2b
```



---
[Back to top](#sda-catalogue)

>Generated at 2023-12-16 03:09:03 by [docgen](https://github.com/thedevsaddam/docgen)

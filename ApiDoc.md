
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
        * [GET Type Database](#i-example-request-get-type-database)
    1. [GET Type Database By UUID](#2-get-type-database-by-uuid)
        * [GET Type Database By UUID](#i-example-request-get-type-database-by-uuid)
    1. [CREATE Type Database](#3-create-type-database)
        * [CREATE Type Database](#i-example-request-create-type-database)
    1. [UPDATE Type Database](#4-update-type-database)
        * [UPDATE Type Database](#i-example-request-update-type-database)
    1. [DELETE Type Database](#5-delete-type-database)
        * [UPDATE Type Database Copy](#i-example-request-update-type-database-copy)
* [Front End](#front-end)
    1. [GET Front End](#1-get-front-end)
        * [GET Front End](#i-example-request-get-front-end)
    1. [GET Front End By UUID](#2-get-front-end-by-uuid)
        * [GET Front End By UUID](#i-example-request-get-front-end-by-uuid)
    1. [CREATE Front End](#3-create-front-end)
        * [CREATE Front End](#i-example-request-create-front-end)
    1. [UPDATE Front End](#4-update-front-end)
        * [UPDATE Front End](#i-example-request-update-front-end)
    1. [DELETE Front End](#5-delete-front-end)
        * [DELETE Front End](#i-example-request-delete-front-end)
* [Back End](#back-end)
    1. [GET Back End](#1-get-back-end)
        * [GET Back End](#i-example-request-get-back-end)
    1. [GET Back End By UUID](#2-get-back-end-by-uuid)
        * [GET Back End By UUID](#i-example-request-get-back-end-by-uuid)
    1. [CREATE Back End](#3-create-back-end)
        * [CREATE Back End](#i-example-request-create-back-end)
    1. [UPDATE Back End](#4-update-back-end)
        * [UPDATE Back End](#i-example-request-update-back-end)
    1. [DELETE Back End](#5-delete-back-end)
        * [DELETE Back End](#i-example-request-delete-back-end)

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
| searchTerm |  | String (Optional) |
| page | 1 | Number |
| size | 10 | Number |



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
| searchTerm |  | (String) e.g "Postgres" |
| page | 1 |  |
| size | 10 |  |



***More example Requests/Responses:***


#### I. Example Request: GET Type Database



***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



***Body: None***



#### I. Example Response: GET Type Database
```js
{
    "status": "OK",
    "message": "Successfully retrieved data type database!",
    "result": {
        "data": [
            {
                "idTypeDatabase": 155,
                "uuid": "b7a7b0d7-18c7-41d1-9ea2-2b7f20e58c20",
                "typeDatabase": "Microsoft SQL Syntax",
                "createdAt": "2023-12-19T13:24:21.998563",
                "updatedAt": "2023-12-19T13:24:21.998563"
            },
            {
                "idTypeDatabase": 154,
                "uuid": "2f60f57c-7877-4d24-9f6c-8e4ea3e74c4b",
                "typeDatabase": "Cassandra",
                "createdAt": "2023-12-19T13:23:51.659629",
                "updatedAt": "2023-12-19T13:23:51.659629"
            },
            {
                "idTypeDatabase": 153,
                "uuid": "efd8f3d5-10f9-436d-bf06-b67ef2ec4ee7",
                "typeDatabase": "MySQL",
                "createdAt": "2023-12-19T13:23:43.250557",
                "updatedAt": "2023-12-19T13:23:43.250557"
            },
            {
                "idTypeDatabase": 152,
                "uuid": "005a1f5d-e879-429e-8a50-f9b5698a74b0",
                "typeDatabase": "PostgresSQL",
                "createdAt": "2023-12-19T13:23:32.169173",
                "updatedAt": "2023-12-19T13:23:32.169173"
            }
        ],
        "page": {
            "size": 10,
            "total": 4,
            "current": 1
        }
    }
}
```


***Status Code:*** 200

<br>



### 2. GET Type Database By UUID



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/type-database/2f60f57c-7877-4d24-9f6c-8e4ea3e74c4b
```



***More example Requests/Responses:***


#### I. Example Request: GET Type Database By UUID



***Body: None***



#### I. Example Response: GET Type Database By UUID
```js
{
    "status": "OK",
    "message": "Success retrieved data type database!",
    "result": {
        "idTypeDatabase": 154,
        "uuid": "2f60f57c-7877-4d24-9f6c-8e4ea3e74c4b",
        "typeDatabase": "Cassandra",
        "createdAt": "2023-12-19T13:23:51.659629",
        "updatedAt": "2023-12-19T13:23:51.659629"
    }
}
```


***Status Code:*** 200

<br>



### 3. CREATE Type Database



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/type-database
```



***Body:***

```js        
{
    "typeDatabase": "Microsoft SQL Syntax"
}
```



***More example Requests/Responses:***


#### I. Example Request: CREATE Type Database



***Body:***

```js        
{
    "typeDatabase": "Microsoft SQL Syntax"
}
```



#### I. Example Response: CREATE Type Database
```js
{
    "status": "CREATED",
    "message": "Success create data type database!",
    "result": {
        "idTypeDatabase": 1,
        "uuid": "6a86698f-7b78-4c85-9d66-8278bc50a02a",
        "typeDatabase": "Microsoft SQL Syntax",
        "createdAt": "2023-12-19T15:11:40.448713",
        "updatedAt": "2023-12-19T15:11:40.448713"
    }
}
```


***Status Code:*** 201

<br>



### 4. UPDATE Type Database



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/type-database/6a86698f-7b78-4c85-9d66-8278bc50a02a
```



***Body:***

```js        
{
    "typeDatabase": "Microsoft SQL Server"
}
```



***More example Requests/Responses:***


#### I. Example Request: UPDATE Type Database



***Body:***

```js        
{
    "typeDatabase": "Microsoft SQL Server"
}
```



#### I. Example Response: UPDATE Type Database
```js
{
    "status": "ACCEPTED",
    "message": "Success update data type database!",
    "result": {
        "idTypeDatabase": 1,
        "uuid": "6a86698f-7b78-4c85-9d66-8278bc50a02a",
        "typeDatabase": "Microsoft SQL Server",
        "createdAt": "2023-12-19T15:11:40.448713",
        "updatedAt": "2023-12-19T15:11:40.448713"
    }
}
```


***Status Code:*** 202

<br>



### 5. DELETE Type Database



***Endpoint:***

```bash
Method: DELETE
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/type-database/b7a7b0d7-18c7-41d1-9ea2-2b7f20e58c20
```



***Body:***

```js        
{
    "typeDatabase": "PostgresSQL"
}
```



***More example Requests/Responses:***


#### I. Example Request: UPDATE Type Database Copy



***Body:***

```js        
{
    "typeDatabase": "PostgresSQL"
}
```



#### I. Example Response: UPDATE Type Database Copy
```js
{
    "status": "OK",
    "message": "Success delete data type database!",
    "result": {
        "idTypeDatabase": 155,
        "uuid": "b7a7b0d7-18c7-41d1-9ea2-2b7f20e58c20",
        "typeDatabase": "Microsoft SQL Server",
        "createdAt": "2023-12-19T13:24:21.998563",
        "updatedAt": "2023-12-19T13:24:21.998563"
    }
}
```


***Status Code:*** 200

<br>



## Front End



### 1. GET Front End



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/front-end
```



***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  | (String) e.g "Angular" |
| page | 1 |  |
| size | 10 |  |



***More example Requests/Responses:***


#### I. Example Request: GET Front End



***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



***Body: None***



#### I. Example Response: GET Front End
```js
{
    "status": "OK",
    "message": "Success retrieved data front end!",
    "result": {
        "data": [
            {
                "idFrontEnd": 4,
                "uuid": "c3b4860c-69a1-4b61-bab1-e04057f94a61",
                "frontEnd": "VueJs",
                "createdAt": "2023-12-19T14:08:57.612602",
                "updatedAt": "2023-12-19T14:08:57.612602"
            },
            {
                "idFrontEnd": 3,
                "uuid": "2cc179f4-60da-498f-bb61-d4dc97b05d54",
                "frontEnd": "Laravel",
                "createdAt": "2023-12-19T14:08:51.26874",
                "updatedAt": "2023-12-19T14:08:51.26874"
            },
            {
                "idFrontEnd": 2,
                "uuid": "7abc6b63-ceca-4a53-afd0-5b51fa51ecd5",
                "frontEnd": "NestJs",
                "createdAt": "2023-12-19T14:08:30.542172",
                "updatedAt": "2023-12-19T14:08:30.542172"
            },
            {
                "idFrontEnd": 1,
                "uuid": "cee8c471-e980-4cf7-816a-62e78682c13e",
                "frontEnd": "Angular",
                "createdAt": "2023-12-19T14:08:20.586334",
                "updatedAt": "2023-12-19T14:08:20.586334"
            }
        ],
        "page": {
            "size": 10,
            "total": 4,
            "current": 1
        }
    }
}
```


***Status Code:*** 200

<br>



### 2. GET Front End By UUID



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/front-end/b89ff75d-7323-4823-bced-fbb9f74d7cc2
```



***More example Requests/Responses:***


#### I. Example Request: GET Front End By UUID



***Body: None***



#### I. Example Response: GET Front End By UUID
```js
{
    "status": "OK",
    "message": "Success retrieved data front end!",
    "result": {
        "idFrontEnd": 2,
        "uuid": "b89ff75d-7323-4823-bced-fbb9f74d7cc2",
        "frontEnd": "Angular",
        "createdAt": "2023-12-19T15:08:46.688521",
        "updatedAt": "2023-12-19T15:08:46.688521"
    }
}
```


***Status Code:*** 200

<br>



### 3. CREATE Front End



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/front-end
```



***Body:***

```js        
{
    "frontEnd": "Angular"
}
```



***More example Requests/Responses:***


#### I. Example Request: CREATE Front End



***Body:***

```js        
{
    "frontEnd": "Flutter"
}
```



#### I. Example Response: CREATE Front End
```js
{
    "status": "CREATED",
    "message": "Success create data front end!",
    "result": {
        "idFrontEnd": 5,
        "uuid": "2dacd1df-2abc-49cd-9bdf-6b2bdc59b4c3",
        "frontEnd": "Flutter",
        "createdAt": "2023-12-19T14:09:30.212406",
        "updatedAt": "2023-12-19T14:09:30.212406"
    }
}
```


***Status Code:*** 201

<br>



### 4. UPDATE Front End



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/front-end/3510c510-5448-49e0-a38b-f2e8c9a22b6a
```



***Body:***

```js        
{
    "frontEnd": "Ionic"
}
```



***More example Requests/Responses:***


#### I. Example Request: UPDATE Front End



***Body:***

```js        
{
    "frontEnd": "Ionic"
}
```



#### I. Example Response: UPDATE Front End
```js
{
    "status": "ACCEPTED",
    "message": "Success update data front end!",
    "result": {
        "idFrontEnd": 1,
        "uuid": "3510c510-5448-49e0-a38b-f2e8c9a22b6a",
        "frontEnd": "Ionic",
        "createdAt": "2023-12-19T15:06:58.380772",
        "updatedAt": "2023-12-19T15:06:58.380772"
    }
}
```


***Status Code:*** 202

<br>



### 5. DELETE Front End



***Endpoint:***

```bash
Method: DELETE
Type: 
URL: {{url_local}}:{{port}}/api/v1/front-end/3510c510-5448-49e0-a38b-f2e8c9a22b6a
```



***More example Requests/Responses:***


#### I. Example Request: DELETE Front End



***Body: None***



#### I. Example Response: DELETE Front End
```js
{
    "status": "OK",
    "message": "Success delete data front end!",
    "result": {
        "idFrontEnd": 1,
        "uuid": "3510c510-5448-49e0-a38b-f2e8c9a22b6a",
        "frontEnd": "Ionic",
        "createdAt": "2023-12-19T15:06:58.380772",
        "updatedAt": "2023-12-19T15:06:58.380772"
    }
}
```


***Status Code:*** 200

<br>



## Back End



### 1. GET Back End



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/back-end
```



***Query params:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



***More example Requests/Responses:***


#### I. Example Request: GET Back End



***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| searchTerm |  |  |
| page | 1 |  |
| size | 10 |  |



***Body: None***



#### I. Example Response: GET Back End
```js
{
    "status": "OK",
    "message": "Success retrieved data back end!",
    "result": {
        "data": [
            {
                "idFrontEnd": 3,
                "uuid": "5cd80a47-5729-4788-93b2-d75445d98ac8",
                "backEnd": "CodeIgniter",
                "createdAt": "2023-12-19T14:57:30.022275",
                "updatedAt": "2023-12-19T14:57:30.022275"
            },
            {
                "idFrontEnd": 2,
                "uuid": "81cff7db-78d4-46c5-ab45-1c9eac03bf38",
                "backEnd": "Laravel",
                "createdAt": "2023-12-19T14:57:17.467774",
                "updatedAt": "2023-12-19T14:57:17.467774"
            },
            {
                "idFrontEnd": 1,
                "uuid": "04c502c7-d5f2-4d75-9063-3c5f1bde5469",
                "backEnd": "Spring Boot",
                "createdAt": "2023-12-19T14:55:36.885107",
                "updatedAt": "2023-12-19T14:55:36.885107"
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



### 2. GET Back End By UUID



***Endpoint:***

```bash
Method: GET
Type: 
URL: {{url_local}}:{{port}}/api/v1/back-end/8d37d629-15cc-4ada-8c34-7897f56e8e1b
```



***More example Requests/Responses:***


#### I. Example Request: GET Back End By UUID



***Body: None***



#### I. Example Response: GET Back End By UUID
```js
{
    "status": "OK",
    "message": "Success retrieved data back end!",
    "result": {
        "idFrontEnd": 2,
        "uuid": "8d37d629-15cc-4ada-8c34-7897f56e8e1b",
        "backEnd": "CodeIgniter",
        "createdAt": "2023-12-19T15:00:29.564585",
        "updatedAt": "2023-12-19T15:00:29.564585"
    }
}
```


***Status Code:*** 200

<br>



### 3. CREATE Back End



***Endpoint:***

```bash
Method: POST
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/back-end
```



***Body:***

```js        
{
    "backEnd": "CodeIgniter"
}
```



***More example Requests/Responses:***


#### I. Example Request: CREATE Back End



***Body:***

```js        
{
    "backEnd": "NestJs"
}
```



#### I. Example Response: CREATE Back End
```js
{
    "status": "CREATED",
    "message": "Success create data back end!",
    "result": {
        "idFrontEnd": 1,
        "uuid": "04c502c7-d5f2-4d75-9063-3c5f1bde5469",
        "backEnd": "NestJs",
        "createdAt": "2023-12-19T14:55:36.885107",
        "updatedAt": "2023-12-19T14:55:36.885107"
    }
}
```


***Status Code:*** 201

<br>



### 4. UPDATE Back End



***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/back-end/04c502c7-d5f2-4d75-9063-3c5f1bde5469
```



***Body:***

```js        
{
    "backEnd": "Spring Boot"
}
```



***More example Requests/Responses:***


#### I. Example Request: UPDATE Back End



***Body:***

```js        
{
    "backEnd": "Spring Boot"
}
```



#### I. Example Response: UPDATE Back End
```js
{
    "status": "ACCEPTED",
    "message": "Success update data back end!",
    "result": {
        "idFrontEnd": 1,
        "uuid": "04c502c7-d5f2-4d75-9063-3c5f1bde5469",
        "backEnd": "Spring Boot",
        "createdAt": "2023-12-19T14:55:36.885107",
        "updatedAt": "2023-12-19T14:55:36.885107"
    }
}
```


***Status Code:*** 202

<br>



### 5. DELETE Back End



***Endpoint:***

```bash
Method: DELETE
Type: RAW
URL: {{url_local}}:{{port}}/api/v1/back-end/bf120fb0-8c95-4d58-9961-b4d48f581be2
```



***Body:***

```js        
{
    "backEnd": "Spring Boot"
}
```



***More example Requests/Responses:***


#### I. Example Request: DELETE Back End



***Body:***

```js        
{
    "backEnd": "Spring Boot"
}
```



#### I. Example Response: DELETE Back End
```js
{
    "status": "OK",
    "message": "Success delete data back end!",
    "result": {
        "idFrontEnd": 1,
        "uuid": "bf120fb0-8c95-4d58-9961-b4d48f581be2",
        "backEnd": "CodeIgniter",
        "createdAt": "2023-12-19T14:59:24.833304",
        "updatedAt": "2023-12-19T14:59:24.833304"
    }
}
```


***Status Code:*** 200

<br>



---
[Back to top](#sda-catalogue)

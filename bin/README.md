Car Servicing API Endpoints
This section provides details about the available API endpoints for the Car Servicing project. The endpoints are organized based on their functionality, such as user registration, servicing requests, and admin actions.

1. User Registration & Authentication
1.1 POST /api/users/register
http://localhost:8080/api/users/register
Purpose: Register a new user.

Request Body:

{
    "firstName": "chandan",
    "lastName": "london",
    "age": 28,
    "gender": "Male",
    "contactNumber": "9704056894",
    "userId": "chandan",
    "password": "london123",
    "role":"USER"
}

Response:

409 Conflict: UserId already exists: chandan

201 Created: Registration successful.

400 Bad Request: Validation error (e.g., missing mandatory fields).
---------------------------------------------------------------------------------------------------
1.2 POST /api/users/login
http://localhost:8080/api/users/login?userId=chandan&password=london123
Purpose: Authenticate a registered user.

Request Body:

Response:

200 OK: Authentication successful.
{
    "id": 5,
    "firstName": "chandan",
    "lastName": "london",
    "age": 28,
    "gender": "Male",
    "contactNumber": "9704056894",
    "userId": "chandan",
    "password": "london123",
    "role": "USER",
    "createdAt": "2025-04-18T12:47:37.530957"
}

401 Unauthorized: Invalid credentials.
-------------------------------------------------------------------------------------------------------------
2. Service Center & Servicing Request
2.1 GET /api/service-centers
http://localhost:8080/api/service-centers
Purpose: Retrieve a list of all service centers.

Response:
[
    {
        "id": 1,
        "name": "Service Center 1",
        "address": "123 Main St, City A",
        "rating": 4.5,
        "available": true
    },
    {
        "id": 2,
        "name": "Service Center 2",
        "address": "456 Elm St, City B",
        "rating": 4.2,
        "available": true
    },
    {
        "id": 3,
        "name": "Service Center 3",
        "address": "789 Oak St, City C",
        "rating": 3.8,
        "available": true
    }
]
---------------------------------------------------------------------------
2.2 GET /api/service-centers/{id}
http://localhost:8080/api/service-centers/1
Purpose: Get details of a specific service center by ID.

Response:
{
    "id": 1,
    "name": "Service Center 1",
    "address": "123 Main St, City A",
    "rating": 4.5,
    "available": true
}
--------------------------------------------------------------------------
2.3 POST /api/servicing-request
http://localhost:8080/api/servicing-request
Purpose: Create a new servicing request.

Request Body:
{
  "userId": 5,
  "serviceCenterId": 1,
  "serviceTypeId": 2,
  "startDate": "2025-04-20",
  "endDate": "2025-04-22"
}
Response:

201 Created: Servicing request successfully created.
{
    "id": 3,
    "userId": 5,
    "serviceCenterId": 1,
    "serviceTypeId": 2,
    "startDate": "2025-04-20",
    "endDate": "2025-04-22",
    "status": "PENDING",
    "deliveryBoyId": null,
    "createdAt": "2025-04-18T13:28:46.692512"
}

400 Bad Request: Validation error (e.g., start date after end date).
-----------------------------------------------------------------------------
2.4 GET /api/servicing-request/{id}

http://localhost:8080/api/servicing-request/user/5

Purpose: Get details of a specific servicing request by ID.

Response:

[
    {
        "id": 3,
        "userId": 5,
        "serviceCenterId": 1,
        "serviceTypeId": 2,
        "startDate": "2025-04-20",
        "endDate": "2025-04-22",
        "status": "PENDING",
        "deliveryBoyId": null,
        "createdAt": "2025-04-18T13:28:46.692512"
    },
    {
        "id": 4,
        "userId": 5,
        "serviceCenterId": 1,
        "serviceTypeId": 2,
        "startDate": "2025-04-20",
        "endDate": "2025-04-22",
        "status": "PENDING",
        "deliveryBoyId": null,
        "createdAt": "2025-04-18T13:30:30.182413"
    }
]
------------------------------------------------------------------------------
3. Admin Operations
3.1 POST api/admin/servicing-request/4?status=ACCEPTED

http://localhost:8080/api/admin/servicing-request/4?status=ACCEPTED
Purpose: Admin accepts the servicing request.

Response:

200 OK: Request accepted successfully.
{
    "id": 4,
    "userId": 5,
    "serviceCenterId": 1,
    "serviceTypeId": 2,
    "startDate": "2025-04-20",
    "endDate": "2025-04-22",
    "status": "ACCEPTED",
    "deliveryBoyId": null,
    "createdAt": "2025-04-18T13:30:30.182413"
}

400 Bad Request: Invalid servicing request.
----------------------------------------------------------------------------
3.2 POST /api/admin/servicing-request/{id}/reject
Purpose: Admin rejects the servicing request.

Response:

200 OK: Request rejected successfully.

{
    "id": 4,
    "userId": 5,
    "serviceCenterId": 1,
    "serviceTypeId": 2,
    "startDate": "2025-04-20",
    "endDate": "2025-04-22",
    "status": "REJECTED",
    "deliveryBoyId": null,
    "createdAt": "2025-04-18T13:30:30.182413"
}

400 Bad Request: Invalid servicing request.
----------------------------------------------------------------------------------------------------
3.3 PUT /api/admin/servicing-request/updateServiceCenterStatus?id=1&status=false
http://localhost:8080/api/admin/servicing-request/updateServiceCenterStatus?id=1&status=false
Purpose: Update availability status of a service center.

Response:
{
    "id": 1,
    "name": "Service Center 1",
    "address": "123 Main St, City A",
    "rating": 4.5,
    "available": false
}

200 OK: Service center availability updated.

400 Bad Request: Invalid service center ID.
--------------------------------------------------------------------------------------------
4. Service Type & Cost
4.1 GET /api/service-types

http://localhost:8080/api/service-types

Purpose: Retrieve a list of all service types.

Response:

[
    {
        "id": 1,
        "serviceName": "Oil Change",
        "description": "Changing the engine oil and replacing the filter"
    },
    {
        "id": 2,
        "serviceName": "Brake Repair",
        "description": "Repair or replacement of the brake system"
    },
    {
        "id": 3,
        "serviceName": "Tire Rotation",
        "description": "Rotating the tires for better wear and tear balance"
    },
    {
        "id": 4,
        "serviceName": "Battery Check",
        "description": "Checking and replacing car battery"
    },
    {
        "id": 5,
        "serviceName": "Air Conditioning Service",
        "description": "Checking and refilling air conditioning gas"
    }
]

----------------------------------------------------------------------------------------------
4.2 POST /api/service-center-services
Purpose: Assign a service type to a service center with associated cost.

Request Body:

json
Copy
Edit
{
  "serviceCenterId": 1,
  "serviceTypeId": 2,
  "cost": 150.00
}
Response:

201 Created: Service type assigned successfully with cost.

400 Bad Request: Invalid data.
-----------------------------------------------------------------------
5. Delivery Boy Management
5.1 GET /api/delivery-boys
Purpose: Retrieve a list of all delivery boys.

Response:

json
Copy
Edit
[
  {
    "id": 1,
    "name": "John Doe",
    "contactNumber": "1234567890",
    "serviceCenterId": 1,
    "isAvailable": true
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "contactNumber": "0987654321",
    "serviceCenterId": 2,
    "isAvailable": true
  }
]
5.2 POST /api/delivery-boys
Purpose: Add a new delivery boy to a service center.

Request Body:

json
Copy
Edit
{
  "name": "Alice Brown",
  "contactNumber": "1112223333",
  "serviceCenterId": 1,
  "isAvailable": true
}
Response:

201 Created: Delivery boy added successfully.

400 Bad Request: Invalid data.

5.3 PUT /api/delivery-boys/{id}/availability
Purpose: Update availability of a specific delivery boy.

Request Body:

json
Copy
Edit
{
  "isAvailable": false
}
Response:

200 OK: Delivery boy availability updated.

400 Bad Request: Invalid delivery boy ID.

6. User Servicing Request Status
6.1 GET /api/servicing-requests/user/{userId}
Purpose: Retrieve a list of all servicing requests for a specific user.

Response:

json
Copy
Edit
[
  {
    "id": 1,
    "serviceCenterId": 1,
    "serviceTypeId": 2,
    "startDate": "2025-04-20",
    "endDate": "2025-04-22",
    "status": "PENDING",
    "deliveryBoyId": 5,
    "createdAt": "2025-04-18T10:00:00"
  },
  {
    "id": 2,
    "serviceCenterId": 2,
    "serviceTypeId": 1,
    "startDate": "2025-04-21",
    "endDate": "2025-04-23",
    "status": "ACCEPTED",
    "deliveryBoyId": 3,
    "createdAt": "2025-04-19T09:00:00"
  }
]
Notes:
All date fields should be in YYYY-MM-DD format for simplicity.

The status field for servicing requests can be PENDING, ACCEPTED, or REJECTED.

Admin endpoints are protected and require Admin credentials.

This document should be a helpful guide for the Car Servicing API. Let me know if you need any further assistance!







You said:
make it a single file so that i will add in readme file


ChatGPT said:
markdown
Copy
Edit
## API Endpoints

### 1. User Registration & Authentication

#### 1.1 Register a new user
POST /api/users/register Content-Type: application/json

{ "firstName": "John", "lastName": "Doe", "age": 28, "gender": "Male", "contactNumber": "1234567890", "userId": "john_doe_123", "password": "password123" }

markdown
Copy
Edit
- **201 Created**: returns created user object  
- **400 Bad Request**: validation error  

#### 1.2 Authenticate a user
POST /api/users/login Content-Type: application/json

{ "userId": "john_doe_123", "password": "password123" }

yaml
Copy
Edit
- **200 OK**: returns user object  
- **401 Unauthorized**: invalid credentials  

---

### 2. Service Center & Service Types

#### 2.1 List all service centers
GET /api/service-centers

pgsql
Copy
Edit
- **200 OK**:  
  ```json
  [
    {
      "id": 1,
      "name": "Service Center 1",
      "address": "123 Main St, City A",
      "rating": 4.5,
      "available": true
    },
    { … }
  ]
2.2 Get a service center by ID
bash
Copy
Edit
GET /api/service-centers/{id}
200 OK: returns one service center object

2.3 List all service types
pgsql
Copy
Edit
GET /api/service-types
200 OK:

json
Copy
Edit
[
  { "id": 1, "serviceName": "Oil Change", "description": "…" },
  { … }
]
3. Service Center Pricing
3.1 Assign a service type and cost to a center
bash
Copy
Edit
POST /api/service-center-services
Content-Type: application/json

{
  "serviceCenterId": 1,
  "serviceTypeId": 2,
  "cost": 150.00
}
201 Created: returns created mapping

400 Bad Request: invalid data

3.2 List services & pricing for a center
swift
Copy
Edit
GET /api/service-center-services/center/{centerId}
200 OK: list of { serviceTypeId, cost }

4. Delivery Boy Management
4.1 List all delivery boys
bash
Copy
Edit
GET /api/delivery-boys
200 OK: list of delivery boy objects

4.2 Add a delivery boy
bash
Copy
Edit
POST /api/delivery-boys
Content-Type: application/json

{
  "name": "Alice Brown",
  "contactNumber": "1112223333",
  "serviceCenterId": 1,
  "isAvailable": true
}
201 Created: returns created delivery boy

400 Bad Request: invalid data

4.3 Update delivery boy availability
bash
Copy
Edit
PUT /api/delivery-boys/{id}/availability
Content-Type: application/json

{
  "isAvailable": false
}
200 OK: returns updated delivery boy

5. Servicing Requests
5.1 Create a new request
bash
Copy
Edit
POST /api/requests
Content-Type: application/json

{
  "userId": 1,
  "serviceCenterId": 1,
  "serviceTypeId": 2,
  "startDate": "2025-05-01",
  "endDate": "2025-05-02"
}
201 Created: returns created request

400 Bad Request: validation error

5.2 List requests for a user
swift
Copy
Edit
GET /api/requests/user/{userId}
200 OK:

json
Copy
Edit
[
  {
    "id": 1,
    "serviceCenterId": 1,
    "serviceTypeId": 2,
    "startDate": "2025-05-01",
    "endDate": "2025-05-02",
    "status": "PENDING",
    "deliveryBoyId": null,
    "createdAt": "2025-04-18T10:00:00"
  },
  { … }
]
5.3 Get a single request by ID
bash
Copy
Edit
GET /api/requests/{id}
200 OK: returns one servicing request object

6. Admin Operations
All admin endpoints require ADMIN role.

6.1 Approve or reject a request
bash
Copy
Edit
PUT /api/admin/requests/{id}/status?status={ACCEPTED|REJECTED}&deliveryBoyId={optional}
200 OK: returns updated request

6.2 List all requests
pgsql
Copy
Edit
GET /api/admin/requests
200 OK: list of all servicing requests

6.3 Update service center availability
bash
Copy
Edit
PUT /api/service-centers/{id}
Content-Type: application/json

{
  "available": false
}
200 OK: returns updated service center

Copy
Edit






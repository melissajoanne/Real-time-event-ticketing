Real Time Event Ticketing System
Overview
This Real Time Event Ticketing System is a comprehensive solution designed to manage ticket release, reservation, and purchase for multiple vendors and customers. The system is thread-safe and scalable, handling multiple concurrent vendors and customers while managing tickets efficiently. The backend technology is Spring Boot, and the frontend is developed using Angular.

Backend (Spring boot)
Features
Customer Management
Add Customer: Customers can provide their details (name, email) and be added to the system.
Get All Customers: Retrieve a list of all customers.
Get Customer by ID: Query a customer by their unique ID.
Update Customer: Update customer details.
Delete Customer: Delete a customer from the system.

Vendor Management
Add Vendor: Vendors can be added with their details such as name and email.
Ticket Release: Vendors can release tickets in batches, specifying the number of tickets per release.

Ticket Management
Release Tickets: Vendors can asynchronously release tickets, Each ticket release follows the vendor's specified interval.
Reserve Tickets: Customers can reserve tickets from the pool.
Finalize Sale: Customers can finalize the purchase of a reserved ticket. Once sold, the ticket’s status is updated.
Count Available Tickets: Retrieve the count of available tickets in the pool.

Concurrency and Thread-Safety
Asynchronous Ticket Release: Tickets are released asynchronously by vendors, with each release following the vendor's configured interval.
Ticket Pool: A thread-safe pool (concurrent linked queue)holds tickets temporarily, with tickets being removed once sold or reserved.
Reservation Expiry: Reserved tickets expire after 1 minutes if not purchased, becoming available again.


API Endpoints
Customer Endpoints
GET /customer/all Retrieve all customers.
GET /customer/find/{customerId} Retrieve customer details by customer ID.
POST /customer/add Add a new customer (requires name and email).
PUT /customer/update Update customer details.
DELETE /customer/delete/{customerId} Delete a customer.

Vendor Endpoints
POST /vendor/add Add a new vendor (requires name and email).
POST /vendor/release Release tickets for an event (requires x-vendor-id and TicketReleaseRequest).
GET /vendor/all Retrieve all customers.
GET /vendor/find/{vendorId} Retrieve customer details by vendor ID.
PUT /vendor/update Update vendor details.
DELETE /vendor/delete/{vendorId} Delete a vendor.


Ticket Endpoints
GET /ticket/available Retrieve all available tickets in the pool.
GET /ticket/count Retrieve the count of available tickets.
POST /ticket/reserve Reserve a ticket for a customer (requires x-customer-id).
POST /ticket/finalize Finalize the purchase of a reserved ticket (requires x-customer-id).
GET /ticket/find/{ticketId} Find a ticket by its ID.
PUT /ticket/update/{ticketId} Update ticket details.
DELETE /ticket/delete/{ticketId} Delete a ticket.



Data Models

Customer
customerId: Unique identifier for the customer.
name: Customer's full name.
email: Customer's email address.

Vendor
vendorId: Unique identifier for the vendor.
name: Vendor's name.
email: Vendor's email address.

Ticket
ticketId: Unique identifier for the ticket.
vendorId: The vendor who released the ticket.
price: The price of the ticket.
status: The current status of the ticket (available, reserved, sold).
type: The type of ticket (e.g., VIP, Normal).

TicketReleaseRequest
ticketsPerRelease: The number of tickets to be released by the vendor.

Frontend (Angular)
.
Features
•	Customer Interface:
Customers provide their details once, and their customer ID is associated with their session (storage) for all interactions.
Customer can reserve and buy one ticket at a time. 
•	Vendor Interface:
Vendors can release tickets in batches.
Vendors can configure their ticket quantities.

Workflow

Customer Flow:
-The customer provides their details when interacting with the system (name, email).
-They can then reserve tickets and finalize the sale when they are ready to purchase.
-Upon reservation, the ticket status is marked as reserved, and once payment is completed, it is updated to sold. If they don’t pay within 1 minute after reservation a session time out error will be shown, and the customer must login from the beginning. 

Vendor Flow:
-The vendor provides their details when interacting with the system (name, email).
-The vendor specifies how many tickets they wish to release .
-The system automatically processes the ticket release and manages the status of each ticket.





Error Handling
The system provides structured error responses using MessageResponse and ErrorResponse classes. Errors include messages for issues like invalid inputs, not found resources, and server errors.
Common Error Responses
-400 Bad Request: Invalid or missing fields.
-404 Not Found: Resource not found.
-500 Internal Server Error: Unexpected server errors.

Asynchronous Processing
The ticket release is handled asynchronously using ExecutorService for multi-threading. This ensures that multiple vendors can release tickets simultaneously, each with their own configurable release interval.

Security Considerations
-Header Authentication: Use x-vendor-id and x-customer-id to authenticate requests.
-Input Validation: Ensure valid customer and vendor information, ticket types, and price data.

Requirements
•	Java 17+
•	Spring Boot 2.7+
•	MySQL Database
•	Angular for Frontend


Setup and Installation

Clone the Repository:
git clone https://github.com/your-username/event-ticketing-system.git

Install Dependencies:
Ensure you have Java 17+ and Maven installed. Navigate to the project directory and run:
mvn clean install

Configure the Database
Update application.properties with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/RealTimeEventTicketing
spring.datasource.username=root
spring.datasource.password=password
Run the Backend Application


To start the backend server, run:
mvn spring-boot:run

The backend API will be available on http://localhost:8080.

Frontend Setup (Angular)
1.	Navigate to the frontend directory.
2.	Install Angular dependencies:
npm install
3.	Run the Angular app:
ng serve
The frontend will be available on http://localhost:4200.

Future Enhancements
•	Payment Gateway Integration: Integrating real payment processing for ticket purchases.
•	User Authentication: Implement JWT-based authentication for vendors and customers.
•	Advanced Search and Filtering: Adding filtering options for ticket types and availability.

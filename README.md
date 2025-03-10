# Secure and Scalable ATM Management System – Spring Boot
This project is a secure and scalable ATM management system developed with Spring Boot. 
It ensures that only users from registered banks can access ATM services, enhancing security and limiting access to authorized customers.

## Key Features:
## User Registration & Authentication:

* Users must select one of the pre-registered banks during the registration process. This ensures that only customers of supported banks can use the ATM.
* After successful registration, users are redirected to the login page.
* Users have three attempts to enter their password correctly. If they fail three times, their account is locked for security reasons.

## Banking Transactions:
*  Successfully authenticated users can perform the following operations:
   * Deposit Money
   * Withdraw Money
   * Transfer Money to other accounts

* Every transaction is logged and recorded for security and auditing purposes.

## Admin Panel:

* Admins have special privileges to:
  * View each user’s transaction history.
  * Access the complete transaction history of all users.
  * Register new banks to the ATM system.
  * List all registered banks.
  * View user balances by bank name and access all users’ banking details.

## User Experience & Error Handling:

* The project is primarily developed with Thymeleaf, providing an intuitive and dynamic web interface.
* Detailed error messages are thrown for incorrect actions, allowing users to understand exactly where they made a mistake.

This project leverages Spring Security for authentication, Thymeleaf for frontend rendering, and robust logging mechanisms to track all transactions, ensuring a secure and efficient ATM system.

## 💻  Technologies Used

* Java 17
* Spring Boot
* Spring Security
* Thymeleaf
* Lombok
* PostgreSQL
* Mapstruct
* Pagination & Sorting

## 🔗  Services
* **RegisterService:** Handles user registration operations, including creating new user accounts and saving user data to the database.
* **LoginService:** Manages user login functionality by validating credentials and redirects the user to a page where they can perform actions after successful authentication.
* **BankingOperationService:** The service where withdrawal, deposit, and money transfer operations are performed by the logged-in user.
* **BankAdminService:** In this service, all operations have been carried out, and only admins have the necessary permissions. 
Pagination has been implemented to display all the information.

# 🌐 API Endpoints

## RegisterController

* URL: `http://localhost:6767/register`
  Users can register on this page by filling out the form here.

## LoginController

* URL: `http://localhost:6767/auth/login`
  Users can log in with their username and password on this page.

## DashboardController

* URL: `http://localhost:6767/dashboard`
  On this page, users can perform withdrawal, deposit, money transfer, and logout operations.

## DepositController

* URL: `http://localhost:6767/deposit`
  Users can deposit money into their account on this page.

## WithdrawController

* URL: `http://localhost:6767/withdraw`
  Users can withdraw money on this page.

## TransferController

* URL: `http://localhost:6767/transfer`
  Users can send money to another user using their IBAN information on this page

## AdminController

* URL: `http://localhost:6767/admin/save/bank`
  Admins can add a new bank to the ATM on this page.

* URL: `http://localhost:6767/admin/bank/list`
  Admins can list all banks on this page.

* URL: `http://localhost:6767/admin/bank/balances/{bankName}`
  On this page, admins can paginate and view the balances of all users belonging to a bank by bank name.

* URL: `http://localhost:6767/admin/bank/all/balances`
  On this page, admins can paginate and view the balances of all users from all banks.

* URL: `http://localhost:6767/admin/bank/audit/history/{username}`
  On this page, admins can paginate and access the entire audit history of a specific user.

* URL: `http://localhost:6767/admin/bank/audit/all/history`
  On this page, admins can paginate and access the entire audit history of all users.

* URL: `http://localhost:6767/admin/bank/transaction/history/{username}`
  On this page, admins can paginate and access the entire transaction history of a specific user.

* URL: `http://localhost:6767/admin/bank/transaction/all/history`
  On this page, admins can paginate and access the entire transaction history of all users.

* URL: `http://localhost:6767/admin/bank/users/{bankName}`
  On this page, admins can paginate and access the information of all users from a specific bank.

* URL: `http://localhost:6767/admin/bank/all/users`
  On this page, admins can paginate and access the information of all users from all banks.

# NOTES
### AdminInitializer
This class automatically creates an admin user with the username `admin` and password `admin` at the beginning of the program

### IBAN Generator:
* IBAN (International Bank Account Number) is used in banking systems to identify a unique bank account. This class automatically generates a user-specific IBAN number.
* The IBAN number is formatted according to the requirements of a specific country and bank.
* You can use the `IbanGenerator` class to generate a random IBAN number.

### SWIFT Kodu Generator:
* SWIFT (Society for Worldwide Interbank Financial Telecommunication) code is a unique code used for international banking transactions.
* This class automatically generates the correct SWIFT code based on the bank name and country code.
* You can use the `SwiftCodeGenerator` class to generate a random SWIFT code.
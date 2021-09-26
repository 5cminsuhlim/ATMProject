# Group 5 ATM

The following file presents a clear guide on how to compile and run the ATM.

## Introduction

In a team of 5, we were contracted to build a ATM system for our client as part of Assignment 1.

The purpose of this assignment was to develop particular skills for working in a cohesive software development team as well as experience building software under the agile methodology.

-   Java for logic and CLI
-   Gradle for building and running the App
-   Jenkins for running JUnit tests and generating JacocoTestReports
-   Git/GitHub for collaboration and version control.


# Prerequisites

* For user file generation, contributors must have the python names module installed
* Gradle
* Java 16 or higher
* Names Library for generation

## Clone

To access the repository on your computer, copy the link provided and git clone

## How to Run

Simply type in `gradle clean build run --console=plain` into your terminal interface.





# How to use

The program must always be run by an administrator first and foremost. This is for security reasons as they not only initialize the balance in the ATM, but also manage the storage.

1.  After the program is first run, the administrator must enter the location of both the user and card file with the correct path names, adhering to regular namespace requirements.
2. Once the administrator has initialized the system, it is now available for use by end users.
3. The system will provide two login options, administrator or user login.

### Should a user be logging in at that moment in time :

- The system will enable customers, who have valid cards and accounts to perform three types of transactions.
    1. Withdrawal of funds
    2. Deposit of Funds
    3. Balance Check
- The User should select their desired transaction type by entering the corresponding number into the command line interface.
    - Each user should note that their card number should only be 5 digits, and these cards are always validated against the database.

-  An ATM card usage shall be considered valid if it meets the following conditions:
    1.  The card entered is valid
    2.  The card is not used after the expiry date
    3.  The card is not used before the set issue date.
    4.  The card is not marked as lost or stolen
    5.  All Identification is correct.

#### All users should note:

- Our ATM's will confiscate cards if it detects that the card is lost or stolen
- If a pin is entered incorrectly 3 times, the card will be considered stolen, and will be blocked
- The ATM **cannot** accept coins from customers, however it **can** dispense coins
-  All transactions will print a receipt containing:
    - Transaction number
    -  Transaction type
    -  Amount withdrawn/deposited
    - Account balance
-  The user can cancel their transaction at any time by selecting cancel

### Should an administrator be logging into the ATM:

All Administrators for a specific ATM must remember their specific administrator code.

#### Once administrators have entered their code:
They will be presented with four options
1. Check ATM Balance
    - This provides the total balance of the ATM and the individual amounts of each coin or note
2. Add Funds
    - This allows the administrator to add funds to the ATM
3. Exit
    - This allows for a user to access the ATM
4. Shut Down
    - Turns off the ATM and prompts the administrator to save data to a file.



# About the team


### Project Lead

Chad Heyman

### Development Team

Eric Lim, Lachlan Murphy, Justin Klass, Sean Kunkler

**Note: All members had equal contribution due to the nature of the project (University Assignment)**

If you would like to contribute to the assignment, please contact us at realemail@gmail.com, where we can discuss your role in the project and potentially add you as a contributor on our GitHub repo.

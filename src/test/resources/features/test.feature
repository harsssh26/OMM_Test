Feature: testAllCases

#Scenario: verify successful creation of new payment arrangement
#  Given  user logs in with valid credentials
#  When  user selects contracts from dropdown and further selects collection
#  And  user selects creating new payment Arrangement and enters valid data
##  Then  new payment arrangement must be created

Scenario: Verify Existing Payment Arrangement Error
  Given user logs in with valid credentials
  And  user selects contracts from dropdown and further selects collection
  When user tries to create payment collection for contract which already has a payment collection
  Then user encounters an error

Scenario: Verify mandatory fields for payment arrangement creation

Feature: testAllCases

Scenario: verify successful creation of new payment arrangement
  Given  user logs in with valid credentials
  When  user selects contracts from dropdown and further selects collection
  And  user selects creating new payment Arrangement and enters valid data
#  Then  new payment arrangement must be created
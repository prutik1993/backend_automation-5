
Feature: As a QE, I validate the TechGlobal CRUD operations

  Scenario Outline: Validating the TechGlobal CRUD operations
    Given create an user with "<firstName>", "<lastName>", email, "<dob>" and "<urlPath>"
    And Validate that status code is 200
    And I make a GET request with "<urlPath>" with id
    And Validate that status code is 200
    And I make a PUT request with following data with "<urlPath>"
      | firstName | Global |
      | lastName  | Tech   |
    And Validate that status code is 200
    And I make a PATCH request with following data with "<urlPath>"
      | firstName | Ali    |
      | lastName  | Martin |
    And Validate that status code is 200
    When I make a DELETE request with following data with "<urlPath>"
    Then Validate that status code is 200
    When I make a DELETE request with following data with "<urlPath>"
    Then Validate that status code is 500

    Examples: TechGlobal Data
      | firstName | lastName | dob        | urlPath   |
      | Batch     | Five     | 2022-08-29 | /students |



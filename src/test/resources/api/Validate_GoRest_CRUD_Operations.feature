@api
Feature: As a QA, I validate GoRest CRUD operations

  Scenario Outline: Validate the POST request
    Given Create a GoRest user with "<name>", "<gender>", email, "<status>" and "<urlPath>"
    Then Status code 201
    And I perform a GET request with "<urlPath>" with id
    And Status code 200
    And I perform PUT request with following data with "<urlPath>"
      | name   | Global   |
      | gender | male     |
      | status | inactive |
    And Status code 200
    And I perform PATCH request with following data with "<urlPath>"
      | name   | Vera   |
      | gender | female |
    And Status code 200
    When I perform DELETE request with following data with "<urlPath>"
    And Status code 204


    Examples: GoRest Data
      | name       | gender | status | urlPath          |
      | Batch Five | female | active | /public/v2/users |

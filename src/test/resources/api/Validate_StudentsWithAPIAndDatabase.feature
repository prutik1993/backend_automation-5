Feature: As a QE, I am validating the data Database and API

  Scenario Outline: Validating the data Database and API
    Given user is able to connect to database
    And user send "<query>" to database and get result
    When I send GET request to "<urlPath>"
    Then I validate data from DB with API

    Examples: Data
      | query                 | urlPath   |
      | select * from student | /students |

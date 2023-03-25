@ui
Feature: As a QE, I am able to create a student under UI Backend page

  Background:
    Given user navigates to "https://techglobal-training.netlify.app/"
    And user clicks on Practices dropdown in the header
    And user selects the "Backend Testing" option

  Scenario Outline: Validating to create a student under UI Backend page
    And user enters "<firstName>", "<lastName>", email and "<dob>" on backend page
    When user click on the add button
    And page displays "Successfully added user!"
    And user can see all students on the page
    Then the system displays user's "<firstName>", "<lastName>", email and "<dob>" on backend page

    Examples: Student data
      | firstName | lastName | dob        |
      | Batch     | Five     | 08/29/2022 |



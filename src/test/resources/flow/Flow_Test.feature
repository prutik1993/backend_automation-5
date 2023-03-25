Feature: As a QE, I validate UI, API and Data flow

  Scenario Outline: Testing data flow
    Given user navigates to "https://techglobal-training.netlify.app/"
    And user clicks on Practices dropdown in the header
    And user selects the "Backend Testing" option
    And user enters "<firstName>", "<lastName>", email and "<dob>" on backend page
    And user click on the add button
    Then i validate matching student was added to the database with the "query"
      |query| select * FROM student where email = |




    Examples: Data
      | firstName | lastName | dob        |
      | Marina    | Fox      | 08/29/2021 |
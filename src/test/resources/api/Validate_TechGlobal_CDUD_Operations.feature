
  Feature: As a QE, I validate the TechGlobal CRUD operations

    Scenario Outline: Validating the TechGlobal CRUD operations
      Given create an user with "<firstName>", "<lastName>", "<email>", "<dob>" and "<urlPath>"
      And Validate that status code is 200
      Examples: TechGlobal Data
      |firstName|lastName|dob|urlPath|
      |Batch|Five|2022-08-29|/students|

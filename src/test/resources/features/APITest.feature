Feature: Request Example

  @Smoke
  Scenario: Testing GET at endpoint.
    Given I send a GET request to the endpoint
    Then I get a 200 status code
    Then I get a list of 10 users

    @API
    Scenario: Test GET to an endpoint.
      Given I send a GET request to the https://api.github.com URI
      Then I get a 400 status code

    @Placeholder
    Scenario: Validate the quantity of entries in the response
      Given I send a GET request to the https://jsonplaceholder.typicode.com URI
      Then I validate there are 10 items on the /users endpoint


 @Other
  Scenario: Validate the quantity of entries in the response
      Given I send a GET request to the https://jsonplaceholder.typicode.com URI
      Then I validate there is a value: John in the response at /users endpoints


@Street
  Scenario: Validate the quantity of entries in the response
    Given I send a GET request to the https://jsonplaceholder.typicode.com URI
    Then I validate the nested value: Kattie Turnpike on the response at /users endpoints
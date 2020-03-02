Feature: Home page
  Home page is working as expected

  Scenario: Home page opens
    Given a user opens the latest chrome on mac
    When they navigate to the Sauce Demo home page
    Then it's visible

  Scenario: Home page opens
    Given a user opens chrome one version behind on mac
    When they navigate to the Sauce Demo home page
    Then it's visible

  Scenario: Home page opens
    Given a user opens the latest chrome on mac
    When they navigate to the Sauce Demo home page
    Then it's visible
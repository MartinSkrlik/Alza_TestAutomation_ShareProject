@test
Feature: Alza Filter Products

  @AlzaFilterProducts
  Scenario Outline:
    Given   Open browser "<BROWSER>"
    When    Go to page "<PAGE>"
    And     Search "<TEXT>"
    And     Verify Page Title "<TITLE>" is visible
    And     Do a screenshot
    And     Remember Item Count before filtering
    And     Set price range between "<MIN>" to "<MAX>"
    Then    Remember Item Count after price filter
    Then    Click checkbox "<NAME_CHECKBOX1>"
    Then    Remember Item Count after brand filter
    Then    Click checkbox "<NAME_CHECKBOX2>"
    Then    Remember Item Count after all filters
    Then    Switch to subpage "<TAB1>"
    Then    Verify subpage "<TAB1>" is active
    Then    Verify first item price between "<MIN>" and "<MAX>"
    And     Do a screenshot
    Then    Switch to subpage "<TAB2>"
    Then    Verify subpage "<TAB2>" is active
    Then    Verify first item price between "<MIN>" and "<MAX>"
    And     Do a screenshot
    Then    Verify product description contains "<NAME_CHECKBOX1>"
    Then    Verify product description contains "<NAME_CHECKBOX2>"
    And     Do a screenshot
    Then    Uncheck checkbox "<NAME_CHECKBOX2>"
    Then    Verify item count after uncheck item filter
    And     Do a screenshot
    Then    Uncheck checkbox "<NAME_CHECKBOX1>"
    And     Do a screenshot
    Then    Verify item count after uncheck brand filter
    Then    Clear parameters
    Then    Verify item count after filter clearing
    And     Do a screenshot

    Examples:
      | BROWSER | PAGE | TEXT   | TITLE   | MIN | MAX  | NAME_CHECKBOX2 | NAME_CHECKBOX1 | TAB1              | TAB2              |
      | CHROME  | Alza | Tablet | Tablets | 100 | 1000 | GPS            | Apple          | Price High to Low | Price Low to High |
      | CHROME  | Alza | Tablet | Tablets | 300 | 700  | Bluetooth      | Samsung        | Price High to Low | Price Low to High |
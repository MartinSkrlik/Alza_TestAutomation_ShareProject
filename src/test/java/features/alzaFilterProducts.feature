@test
Feature: Alza Filter Products

  @AlzaFilterProducts
  Scenario Outline:
    Given   Open browser "<BROWSER>"
    When    Go to page "<PAGE>"
    And     Accept cookies
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
      | CHROME  | Alza | Tablet | Tablets | 10  | 1000 | GPS            | Apple          | Price High to Low | Price Low to High |
#      | CHROME  | Alza | Tablet | Tablets | 300 | 700  | Bluetooth      | Samsung        | Price High to Low | Price Low to High |


  @AlzaProductVanityVariants
  Scenario Outline: Variants Checks
    Given   Open browser "<BROWSER>"
    When    Go to page "<PAGE>"
    And     Accept Cookies
    And     Search "<TEXT>"
    And     Verify Page Title "<TEXT>" is visible
    And     Click checkbox "<NAME_CHECKBOX1>"
    Then    Click on Product with Variants "<INDEX>"
    And     Verify Title Product Page
    Then    Verify Product Variants



    Examples:
      | BROWSER | PAGE | TEXT        | NAME_CHECKBOX1 | INDEX |
      | CHROME  | Alza | phone       | AlzaGuard      | 1     |
      | CHROME  | Alza | black phone | Spigen         | 1     |


  @ProductClaims
  Scenario Outline: Product Claims
    Given    Open browser "<BROWSER>"
    When     Go to page "<PAGE>"
    And      Accept Cookies
    And      Click on "<BUTTON>"
    When     Verify Page Title "<TITLE>"
    Then     Click on "<CLAIMS>" button on contact page
    Then     Verify "<CLAIMS>" is selected on contact page
    When     Click on "<WARRANTY_CLAIM>"
    Then     Verify "<WARRANTY_CLAIM>" is selected from options
    When     Click on "<AUTHORISED>"
    Then     Verify list "<LIST>" of vendors is present
    Then     Verify list is in alphabetical order
    When     Select vendor "<VENDOR>"
    Then     Verify phone number "<PHONE_NUMBER>" is present



    Examples:
      | BROWSER | PAGE | BUTTON          | TITLE                       | CLAIMS           | WARRANTY_CLAIM                  | AUTHORISED                 | VENDOR                         | LIST                               | PHONE_NUMBER     |
      | CHROME  | Alza | Contact us      | What do you need help with? | Claims & Returns | I want to make a warranty claim | Find an authorised service | Acer                           | List of Authorised Service Centres | +421 911 792 034 |
      | CHROME  | Alza | Kontaktujte nás | What do you need help with? | Claims & Returns | I want to make a warranty claim | Find an authorised service | Acer                           | List of Authorised Service Centres | +421 911 792 034 |
      | CHROME  | Alza | Contact us      | What do you need help with? | Claims & Returns | I want to make a warranty claim | Find an authorised service | American Tourister SK (SK_AMT) | List of Authorised Service Centres | +421 2 3333 1027 |
      | CHROME  | Alza | Kontaktujte nás | What do you need help with? | Claims & Returns | I want to make a warranty claim | Find an authorised service | American Tourister SK (SK_AMT) | List of Authorised Service Centres | +421 2 3333 1027 |
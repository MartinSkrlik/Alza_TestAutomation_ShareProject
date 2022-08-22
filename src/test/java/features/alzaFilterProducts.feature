@test
Feature: Alza Filter Products

    @AlzaFilterProducts
    Scenario:
        Given Open browser "Chrome"
        When    Go to page "Alza"
        And     Search for "TABLET"
        And     Remember Item Count before filtering
        And     Set price range between "100" to "1000"
        Then    Verify product page content matches with previous page

@test
Feature: SAUCEDEMO - Login Action

  @Positive
  Scenario Outline: SAUCEDEMO - Login with valid credentials - positive - Demo
  #comment
    #Available Windows browsers: Chrome, Chrome-Headless, Firefox, Firefox-Headless, Edge, Edge-Headless, Opera, IE
    #Available Linux browsers:   Chrome, Chrome-Headless, Firefox, Firefox-Headless, Edge, Edge-Headless, Opera
    #Available macOS browsers:   Chrome, Chrome-Headless, Firefox, Firefox-Headless, Edge, Edge-Headless, Opera, Safari 
    #Given Open browser "<BROWSER>"
    Given Open browser "<BROWSER>" "<DRIVER_MODE>" "<DRIVER_VERSION>" 
  	#Given Open browserstack "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
  	#Given Open saucelabs "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
    When 	Go to page "<PAGE>"
    And 	Login user with username SECURE "<USERNAME>" and password SECURE "<PASSWORD>"
    Then 	Verify page title "<TITLE>" is visible

    Examples: 
       #Local browser run - Given Open browser "<BROWSER>"
       |     BROWSER     | DRIVER_MODE | DRIVER_VERSION|  PAGE    |		USERNAME 	  |   PASSWORD	 |  TITLE	   |
       | CHROME-HEADLESS |     -       |        -      |Saucedemo | standard_user  | secret_sauce | Products |
       
       #BrowserStack run - Given Open browserstack "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
       #| BROWSER |    PAGE   | BROWSERVERSION |    OS   | OSVERSION | DEVICE | APPIUMVERSION | RESOLUTION |	 USERNAME	   |   PASSWORD	  |  TITLE	 |
       #| Chrome  | Saucedemo |     latest     | Windows |    10     |   -    |      -        | 1920x1080  | standard_user | secret_sauce | Products |
       
       #SauceLabs run - Given Open saucelabs "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
       #| BROWSER |    PAGE   | BROWSERVERSION |   OS  | OSVERSION | DEVICE | APPIUMVERSION | RESOLUTION |    USERNAME	 |   PASSWORD	  |  TITLE	 |
       #| Firefox | Saucedemo |			  89       | macOS |   11.00   |   -    |      -        |  1920x1440 | standard_user | secret_sauce | Products |
      
  @Negative
  Scenario Outline: SAUCEDEMO - Login with lock-outed user - negative
    Given Open browser "<BROWSER>"
  	#Given Open browserstack "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
  	#Given Open saucelabs "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
    When 	Go to page "<PAGE>"
    And 	Login user with username SECURE "<USERNAME>" and password SECURE "<PASSWORD>"
    Then 	Verify page title is not visible
    And 	Verify error message is visible

    Examples: 
       #Local browser run - Given Open browser "<BROWSER>"
       |      BROWSER       |    PAGE   | BROWSERVERSION | OS | OSVERSION | DEVICE | APPIUMVERSION | RESOLUTION |		USERNAME	    |   PASSWORD	  |
     	 |   CHROME-HEADLESS  | Saucedemo |        -       | -  |     -     |    -   |      -        |     -      | locked_out_user  | secret_sauce |
      	
       #BrowserStack run - Given Open browserstack "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
       #| BROWSER |    PAGE   | BROWSERVERSION |    OS   | OSVERSION | DEVICE | APPIUMVERSION | RESOLUTION |	 USERNAME	     |   PASSWORD	  |
       #| Chrome  | Saucedemo |     latest     | Windows |    10     |   -    |      -        | 1920x1080  | locked_out_user | secret_sauce |
       
       #SauceLabs run - Given Open saucelabs "<BROWSER>" "<BROWSERVERSION>" "<OS>" "<OSVERSION>" "<DEVICE>" "<APPIUMVERSION>" "<RESOLUTION>"
       #| BROWSER |    PAGE   | BROWSERVERSION |   OS  | OSVERSION | DEVICE | APPIUMVERSION | RESOLUTION |    USERNAME		 |   PASSWORD	  |
       #| Firefox | Saucedemo |			  89       | macOS |   11.00   |   -    |      -        |  1920x1440 | locked_out_user | secret_sauce |
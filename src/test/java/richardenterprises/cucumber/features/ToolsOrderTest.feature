@Tag
Feature: Tools Order Test E2E Flow

  #this executes before each scenario - like before method.
  Background:

  Scenario: Add to cart first displayed single product test
   Given Goto products home page
    And Select the first displayed product
    And Land on product description page and add product to cart
    And Click cart icon
    And Check if items present in cart

  Scenario Outline: Add to cart single product by name test
    Given Goto products home page
    And Select displayed product by name <productName>
    And Land on product description page and add product to cart
    And Click cart icon
    And Check if items present in cart <productName>
    Examples:
    | productId                   | productName         |
    | 01KRDFS58A33NQ3QWW0WJCRNRC  | Combination Pliers  |

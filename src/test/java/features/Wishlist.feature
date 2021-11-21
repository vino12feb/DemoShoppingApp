#Author: Vinodh Vivekanandhan
#Date  : 20th November 2021

@WISHLIST
Feature: Testing a wishlist feature

	Background:
	Given I navigate to demo shopping application shop page

  Scenario Outline: Add multiple items in a wishlist and move lowest price item to cart
    Given I add <totalItems> different products to my Wishlist
    When I view my wishlist table
    Then I find total <totalItems> selected items in my Wishlist
    When I search for lowest price product
    And I am able to add the lowest price item to my cart
    Then I am able to verify the item in my cart
    Examples:
    |totalItems|
    |4|
    

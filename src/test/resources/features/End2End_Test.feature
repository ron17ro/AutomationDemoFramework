#@tag
#Feature: Login Action
#@tag1
#Scenario: Successful Login with Valid Credentials
#	Given user is on Home Page
#	When user Navigate to LogIn Page
##	And User enters "testuser_1" and "Test@123"
#  And user enters "<username>" and "<password>"
#	And user enters UserName and Password
#	Then message displayed Login Successfully
#
#@tag2
#Scenario: Successful LogOut
#	When user LogOut from the Application
#	Then message displayed LogOut Successfully
#
#Examples:
#    | username   | password |
#    | testuser_1 | Test@153 |
#    | testuser_2 | Test@153 |
@tag
Feature: Automated End2End Tests
	Description: test end 2 end integration

	@tag1
	Scenario: Customer place an order by purchasing an item from scratch
		Given user is on Home Page
		When he search for "dress"
#		And he select one item
		And choose to buy the first item
		And select product properties and add to cart
		And moves to checkout from mini cart
		And enter "<customer>" personal details on checkout page
		And place the order
		Then verify the order details
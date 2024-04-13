Feature: Entry controller

Scenario: A GET requests is received and accepted
  Given there are entries in the database
  When a request to get 10 entries is received
  Then 10 entries are returned
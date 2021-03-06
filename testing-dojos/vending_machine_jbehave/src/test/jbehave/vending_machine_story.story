Vending machine story
			 
Scenario: Idling vedning machine 
Given a vending machine
Then the display shows INSERT COINS

Scenario: Accept valid coins
Given a vending machine
When the customer inserts a Nickel
Then the display shows $0.05
When the customer inserts a Dime
Then the display shows $0.15
When the customer inserts a Quarter
Then the display shows $0.40

Scenario: Reject invalid coins
Given a vending machine
When the customer inserts a Penny
Then the display shows INSERT COINS
Then the return tray contains Penny
Then the display shows INSERT COINS
When the customer inserts a Invalid
Then the display shows INSERT COINS
Then the return tray contains Invalid
Then the display shows INSERT COINS

Scenario: Buy Cola
Given a vending machine
When the given inventory is
|productName|amount|
|Cola|1|
Then the return tray is empty
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer selects Cola
Then the machine dispends Cola
Then the display shows THANK YOU
Then the display shows INSERT COINS

Scenario: Add coins to cashbox after purchase
Given a vending machine
When the given inventory is
|productName|amount|
|Cola|1|
When the given cashbox is
|coinName|amount|
|Quarter|1|
|Dime|1|
|Nickel|1|
Then the chashbox value is 40
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer selects Cola
Then the machine dispends Cola
Then the chashbox value is 140
Then the display shows THANK YOU
Then the display shows INSERT COINS

Scenario: Product is sold out
Given a vending machine
When the given inventory is
|productName|amount|
|Cola|1| 
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer selects CHIPS
Then nothing should be dispensed
Then the display shows SOLD OUT

Scenario: Buy product with change
Given a vending machine
When the given inventory is 
|productName|amount|
|Candy|1|
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer inserts a Quarter
When the customer selects Candy
Then the machine should return 10 cents


Scenario: Switch from price info to credit
Given a vending machine
When the customer inserts a Quarter
When the customer selects Candy
Then the display shows PRICE: $0.65
Then the display shows $0.25

Scenario: Return coins
Given a vending machine
When the customer inserts a Quarter
When the customer inserts a Dime
When the customer inserts a Nickel
When the customer inserts a Dime
When the customer press return coins
Then the return tray contains Nickel, Dime, Dime, Quarter

Scenario: Exact change only
Given a vending machine
When the given cashbox is
|coinName|amount|
|Quarter|1|
Then the display shows EXACT CHANGE ONLY

Scenario: Exact change only after dispending
Given a vending machine
When the given cashbox is 
|coinName|amount|
|Nickel|1|
|Dime|1|
When the customer inserts a Dime
When the customer inserts a Dime
When the customer inserts a Dime
When the customer inserts a Dime
When the customer inserts a Dime
When the customer inserts a Dime
When the customer inserts a Dime
Then the display shows $0.70
When the customer selects Candy
Then the return tray contains Nickel
Then the display shows THANK YOU
Then the display shows EXACT CHANGE ONLY

Scenario: Insufficient funds 
Given a vending machine
When the given inventory is
|productName|amount|
|Candy|1|
Then the return tray is empty
When the customer inserts a Nickel
When the customer selects Candy
Then the display shows PRICE: $0.65
Then the return tray is empty
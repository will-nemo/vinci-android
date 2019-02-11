<img src="/app/src/main/res/drawable/vinci_playball.png" alt="My cool logo"/>

Android Mobile Application Stock Wallet

-----------------------------------------------------------

# IEX Trading Base URL

https://api.iextrading.com/1.0/

-----------------------------------------------------------
 
# IEX Reference Documentation

https://iextrading.com/developer/docs/

-----------------------------------------------------------

# Creating branch

Format for branch: Type/AVB-####-description

Types include:
  - Feature
  - Bugfix
  - Task
  
 AVB stands for Anua Vinci Branch
 
 Example: Feature/AVB-1234-This-is-an-example-branch
 
-----------------------------------------------------------

# Parsing stock symbols

When calling a GET request for stock symbols to display, the IEX Trading takes in the stock symbols with a comma delimiter (',').

Example: "aapl,aap,fb,tsla,crsp"

In the StockDisplayActivity.java, the string is parsed and mapped to display the appropriate stocks in the RecyclerView

-----------------------------------------------------------

# TODO:

1. ~~Ability to click on stocks and display more information.~~
2. ~~Ability to add stocks to default list to be displayed.~~
3. ~~Ability to delete stocks from default list to be displayed.~~
4. ~~Query name of Company and stock symbol~~
5. ~~Splash Screen~~
6. Navigation Drawer
7. Give user ability to log out
8. Give user ability to create bug ticket
9. ~~Filter query for only tech companies~~
10. Error screens 
11. About us pages

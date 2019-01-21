# Vinci
Android Mobile Application to view technology stocks.

-----------------------------------------------------------

# IEX Trading Base URL

https://api.iextrading.com/1.0/

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

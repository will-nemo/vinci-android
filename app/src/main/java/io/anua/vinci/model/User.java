package io.anua.vinci.model;

public class User {

    /**************************
     * Constants
     *************************/

    public static String DEFAULT_STOCKS = "aapl,aap,fb,tsla,crsp";

    /**************************
     * Private Members
     *************************/

    private String userName;
    private String userEmail;
    private String userID;
    private Long createdTimeOfUser;
    private String userStocks;

    /**************************
     * Constructor
     *************************/

    public User(String userName, String userEmail, String userID, long createdTimeOfUser) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
        this.createdTimeOfUser = createdTimeOfUser;
        this.userStocks = DEFAULT_STOCKS;
    }

    /**************************
     * Default Constructor
     *************************/

    public User(){

    }

    /**************************
     * Public Methods
     *************************/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserStocks() {
        return userStocks;
    }

    public void setUserStocks(String userStocks) {
        this.userStocks = userStocks;
    }

    public String getUserID() {
        return userID;
    }

    public Long getCreatedTimeOfUser() {
        return createdTimeOfUser;
    }
}

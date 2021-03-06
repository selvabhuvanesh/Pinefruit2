package com.qladder.pinefruit;

public class SessionInfo {

    String sessionID;
    String providerID;
    String providerOrg;
    String providerName;
    String mfromtime;
    String mtotime;
    String mdate;
    String sessionStatus;
    String sessionName;
    String sessionSearchText;
    String providerLatitude;
    String providerLongitude;
    String userEmail;
    String userID;
    String userName;


    public SessionInfo(String sessionID,
                       String mfromtime,
                       String mtotime,
                       String mdate,
                       String sessionStatus,
                       String sessionName,
                       String providerID,
                       String providerOrg,
                       String providerName,
                       String sessionSearchText,
                       String providerLatitude,
                       String providerLongitude,
                       String userEmail,
                       String userID,
                       String userName)
    {
        this.sessionID = sessionID;
        this.mfromtime=mfromtime;
        this.mtotime = mtotime;
        this.mdate = mdate;
        this.sessionStatus = sessionStatus;
        this.sessionName = sessionName;
        this.providerID = providerID;
        this.providerOrg = providerOrg;
        this.providerName = providerName;
        this.sessionSearchText = sessionSearchText;
        this.providerLatitude = providerLatitude;
        this.providerLongitude = providerLongitude;
        this.userEmail = userEmail;
        this.userID = userID;
        this.userName = userName;

    }

    public String getSessionID() {
        return sessionID;
    }

   // public String getProviderID() {
   //     return providerID;
   // }

    public String getMfromtime() {
        return mfromtime;
    }

    public String getMtotime() {
        return mtotime;
    }

    public String getMdate() {
        return mdate;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getProviderID() {
        return providerID;
    }

    public String getProviderOrg() {
        return providerOrg;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getSessionSearchText() {
        return sessionSearchText;
    }

    public String getProviderLatitude() {
        return providerLatitude;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getProviderLongitude() {
        return providerLongitude;
    }
}

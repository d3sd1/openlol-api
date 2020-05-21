package com.openlol.api.model;

import com.openlol.api.database.RethinkDbEntity;

@RethinkDbEntity("")
public class RiotUser {
    private long realId;
    private String realPuuid;
    private long realAccountId;
    private Region currentRegion;
    private String loginName;
    private String displayName;

    public long getRealId() {
        return realId;
    }

    public void setRealId(long realId) {
        this.realId = realId;
    }

    public String getRealPuuid() {
        return realPuuid;
    }

    public void setRealPuuid(String realPuuid) {
        this.realPuuid = realPuuid;
    }

    public long getRealAccountId() {
        return realAccountId;
    }

    public void setRealAccountId(long realAccountId) {
        this.realAccountId = realAccountId;
    }

    public Region getCurrentRegion() {
        return currentRegion;
    }

    @RethinkDbEntity("")
    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "RiotUser{" +
                "realId=" + realId +
                ", realPuuid='" + realPuuid + '\'' +
                ", realAccountId=" + realAccountId +
                ", currentRegion=" + currentRegion +
                ", loginName='" + loginName + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}

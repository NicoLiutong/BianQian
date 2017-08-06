package com.example.bianqian.bmobbasic;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 刘通 on 2017/8/6.
 */

public class MyAppVersion extends BmobObject {

    private Integer versionCode;

    private String version;

    private String updateLog;

    private BmobFile path;

    private boolean isForce;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public BmobFile getPath() {
        return path;
    }

    public void setPath(BmobFile path) {
        this.path = path;
    }

}

package com.ququ.imserver.user.pojo;

import java.io.Serializable;

public class ImUser implements Serializable {
    private static final long serialVersionUID = 5457507957150215600L;
    private Integer id;
    private String userid;
    private String username;
    private String mobile;
    private Integer sex;
    private String imuid;
    private String icon;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getImuid() {
        return imuid;
    }

    public void setImuid(String imuid) {
        this.imuid = imuid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ImUser{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", imuid='" + imuid + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}

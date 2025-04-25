package com.hcl.carservicing.carservice.dto;

import java.time.LocalDate;
import java.util.Date;

public class UserLoginDTO {

    private String jwt;
    private Date expirationTime;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String jwt, Date expirationTime) {
        this.jwt = jwt;
        this.expirationTime = expirationTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

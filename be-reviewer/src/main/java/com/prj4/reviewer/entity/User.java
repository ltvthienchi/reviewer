package com.prj4.reviewer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_REVIEWER")
public class User {
    @Id
    @Column(name = "ID_ACCOUNT")
    private String idAccount;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "PASS_ACCOUNT")
    private String passAccount;

    @Column(name = "TYPE_ACCOUNT")
    private int typeAccount;

    @Column(name = "ISACTIVE")
    private boolean isActive;

    public User() {}

    public User(String idAccount, String userName, String passAccount, int typeAccount, boolean isActive) {
        this.idAccount = idAccount;
        this.userName = userName;
        this.passAccount = passAccount;
        this.typeAccount = typeAccount;
        this.isActive = isActive;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassAccount() {
        return passAccount;
    }

    public int getTypeAccount() {
        return typeAccount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassAccount(String passAccount) {
        this.passAccount = passAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}

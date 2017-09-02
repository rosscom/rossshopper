/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author ulfrossang
 * ulf@rosscom.org vik012
 */
@Entity
@NamedQuery(name = Account.findAll, query = " SELECT t from Account t")
public class Account implements Serializable {
            
    static final String PREFIX = "account.entity.Account.";
    public static final String findAll = PREFIX + "findALl";
    
    @Id
    @Column(name="user_id")
    private String userId;
    private String password;
    private String mail;

    @Column(name="logged_in")
    private Boolean loggedIn;
    @Column(name="choosed_home")
    private String choosedHome;

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.loggedIn = false;
        this.choosedHome = null;
    }

    public Account() {
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }
    
    public String getChoosedHome() {
        return choosedHome;
    }

    public void setChoosedHome(String choosedHome) {
        this.choosedHome = choosedHome;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}

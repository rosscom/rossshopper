/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ulfrossang
 * ulf@rosscom.org vik012
 */
@Entity
@NamedQuery(name = Account.findAll, query = " SELECT t from Account t")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Account implements Serializable {
            
    static final String PREFIX = "account.entity.Account.";
    public static final String findAll = PREFIX + "findALl";
    
    @Id
    private String user;
    private String password;
    private Boolean loggedIn;

    public Account(String user, String password) {
        this.user = user;
        this.password = password;
        this.loggedIn = false;
    }

    public Account() {
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
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
    
    
}

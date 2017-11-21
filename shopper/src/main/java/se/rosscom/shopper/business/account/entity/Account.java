/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.entity;

import se.rosscom.shopper.business.authentication.entity.Token;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ulfrossang
 * ulf@rosscom.org vik012
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Account.findAll, 
                query = " SELECT t from Account t"),
    @NamedQuery(name = Account.findByLoggedIn,
                query="SELECT c FROM Account c WHERE c.loggedIn = :loggedIn"),
}) 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Account implements Serializable {
            
    static final String PREFIX = "account.entity.Account.";
    public static final String findAll = PREFIX + "findALl";
    public static final String findByLoggedIn = PREFIX + "findByLoggedIn";

    @Id
    private String userId;
    private String password;
    private String mail;

    private Boolean loggedIn;
    private String choosedHome;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Token> tokens;

    public Account(String userId, String password, String choosedHome, String mail) {
        this.userId = userId;
        this.password = password;
        this.loggedIn = false;
        this.choosedHome = choosedHome;
        this.mail = mail;
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

    public List<Token> getTokens() {
        return tokens;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.home.entity.Home;

/**
 *
 * @author ulfrossang
 */
@Embeddable
public class AccountHomepk implements Serializable {
    
    @ManyToOne
    private Home home;
    
    @ManyToOne
    private Account account;

    public AccountHomepk() {
    }

    public AccountHomepk(Account account, Home home) {
        this.account = account;
        this.home = home;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AccountHomepk){
            AccountHomepk accountHomepk = (AccountHomepk) obj;
            if(!accountHomepk.getAccount().equals(account)){
                return false;
            }
            if(!accountHomepk.getHome().equals(home)){
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return account.hashCode() + home.hashCode();
    }
    
    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
}

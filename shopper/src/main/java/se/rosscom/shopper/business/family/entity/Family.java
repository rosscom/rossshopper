package se.rosscom.shopper.business.family.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.home.entity.Home;

/**
 *
 * @author ulfrossang
 * 1, dagg, ulf@rosscom.org
 */
@Entity
@NamedQuery(name = Family.findAll, query = " SELECT t from Family t")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Family implements Serializable {

    static final String PREFIX = "family.entity.Family.";
    public static final String findAll = PREFIX + "findALl";
    
    @Id
    @GeneratedValue
    private long familyId;
    
    @ManyToOne
    private Home home;
    
    @ManyToOne
    private Account account;
    

    public Family(Home home, Account account) {
        this.home = home;
        this.account = account;
    }

    public Family() {
    }

    public long getFamilyId() {
        return familyId;
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

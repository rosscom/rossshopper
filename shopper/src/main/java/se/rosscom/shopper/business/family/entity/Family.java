package se.rosscom.shopper.business.family.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
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
@NamedQueries({
    @NamedQuery(name = Family.findAll, 
                query = " SELECT t from Family t"),
    @NamedQuery(name = Family.findByUser,
                query="SELECT c FROM Family c WHERE c.id.account.user = :user"),
}) 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Family implements Serializable {

    static final String PREFIX = "family.entity.Family.";
    public static final String findAll = PREFIX + "findALl";
    public static final String findByUser = PREFIX + "findByUser";
    
    @EmbeddedId
    private AccountHomepk id = new AccountHomepk();

    
    public Family(AccountHomepk id) {
        this.id = id;

    }
    public Family(Account acc, Home home) {
        this.id.setAccount(acc);
        this.id.setHome(home);

    }

    public Family() {
    }

    public AccountHomepk getId() {
            return id;
    }

    public void setId(AccountHomepk id) {
        this.id = id;
    } 
}

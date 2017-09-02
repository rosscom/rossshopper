package se.rosscom.shopper.business.family.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
                query="SELECT c FROM Family c WHERE c.userId.userId = :userId"),
}) 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Family implements Serializable {

    static final String PREFIX = "family.entity.Family.";
    public static final String findAll = PREFIX + "findALl";
    public static final String findByUser = PREFIX + "findByUser";
    
    @Id
    private Integer id;
    
    @ManyToOne  
    @JoinColumn(name="name", insertable = false, updatable = false, nullable=false)
    private Home name;
    
    @ManyToOne  
    @JoinColumn(name="user_id", insertable = false, updatable = false, nullable=false)
    private Account userId;
     
    public Family(Home name, Account userId) {
        this.name = name;
        this.userId = userId;
    }


    public Family() {
    }

    public Home getName() {
        return name;
    }

    public void setName(Home name) {
        this.name = name;
    }

    public Account getUserId() {
        return userId;
    }

    public void setUserId(Account userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
}

package se.rosscom.shopper.business.family.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.home.entity.Home;
import se.rosscom.shopper.business.list.entity.ListDetail;

/**
 *
 * @author ulfrossang
 * 1, dagg, ulf@rosscom.org
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Family.findAll, 
                query = " SELECT t from Family t"),
//    @NamedQuery(name = Family.findByUser,
//                query="SELECT c FROM Family c WHERE c.id = :account"),

}) 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Family implements Serializable {

    static final String PREFIX = "family.entity.Family.";
    public static final String findAll = PREFIX + "findALl";
    public static final String findByUser = PREFIX + "findByUser";
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long familyId;

    @ManyToOne  
    @JoinColumn(name="account")
    private Account account;

    @ManyToOne  
    @JoinColumn(name="home")
    private Home home;

    @OneToMany(mappedBy = "family", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ListDetail> listDetails;

    public Family(Account acc, Home home) {
        this.setAccount(acc);
        this.setHome(home);
    }
      
    public Family() {
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public List<ListDetail> getListDetails() {
        return listDetails;
    }

    public void setListDetails(List<ListDetail> listDetails) {
        this.listDetails = listDetails;
    }
}

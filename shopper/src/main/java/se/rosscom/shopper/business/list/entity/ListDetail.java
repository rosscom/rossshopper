/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.entity;

import java.io.Serializable;
import javax.persistence.*;

import se.rosscom.shopper.business.family.entity.Family;

/**
 *
 * @author ulfrossang
 */
@Entity (name = "list_detail")
@NamedQueries({
    @NamedQuery(name = ListDetail.findAll, 
                query = " SELECT t from list_detail t"),
//    @NamedQuery(name = ListDetail.findByFamily,
//                query="SELECT c FROM list_detail c WHERE c.family= :familyId"),
})

@SequenceGenerator(name="list_detail_seq", initialValue=1, allocationSize=1)
public class ListDetail implements Serializable {

    static final String PREFIX = "listdetail.entity.ListDetail";
    public static final String findAll = PREFIX + "findALl";
    public static final String findByFamily = PREFIX + "findByFamily";


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "list_detail_seq")
    private Integer id;
        
    @ManyToOne  
    @JoinColumn(name="familyId")
    private Family family;

    private String item;

    public ListDetail(Family family, String item) {
        this.family = family;
        this.item = item;
    }

    public ListDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
    
    
    
}

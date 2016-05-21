/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import se.rosscom.shopper.business.family.entity.Family;

/**
 *
 * @author ulfrossang
 */
@Entity
@NamedQuery(name = ListDetail.findAll, query = " SELECT t from ListDetail t")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ListDetail {

    static final String PREFIX = "listdetail.entity.ListDetail.";
    public static final String findAll = PREFIX + "findALl";
    
    @Id
    @GeneratedValue
    private long id;
        
    @ManyToOne
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

    public void setId(long id) {
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

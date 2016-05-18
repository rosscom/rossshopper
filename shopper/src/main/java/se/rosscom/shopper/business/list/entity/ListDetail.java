/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
    private String item;

    public ListDetail(String item) {
        this.item = item;
    }

    public ListDetail() {
    }
    
}

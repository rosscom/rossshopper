/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.home.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ulfrossang
 * dagg
 */
@Entity
@NamedQuery(name = Home.findAll, query = " SELECT t from Home t")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Home {

    static final String PREFIX = "home.entity.Home.";
    public static final String findAll = PREFIX + "findALl";
    
    @Id
    private String name;
    private String adress;

    public Home(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public Home() {
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    
}

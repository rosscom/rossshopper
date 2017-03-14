/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business;
 
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
 
import junit.framework.TestCase;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.home.entity.Home;
 
 
/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
 
	private EntityManager entityManager;
 
	public void testApp() {
 
//		entityManager = Persistence.createEntityManagerFactory("entityManager")
//				.createEntityManager();
//                		
//                entityManager.getTransaction().begin();
//
//  
//		Account user = new Account("seconduser","pass");
//		entityManager.merge(user);
//                System.err.println("user: "+ user);
//
//                Home home = new Home("landet","timmerdalen");
//		entityManager.merge(home);
//
//                Family family = new Family(user,home);
//		entityManager.merge(family);
//                
//                entityManager.getTransaction().commit();
//
//
//		entityManager.close();
	}
}
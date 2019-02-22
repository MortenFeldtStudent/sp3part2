package dbfacades;

import dbfacades.DemoFacade;
import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * UNIT TEST example that mocks away the database with an in-memory database
 * See Persistence unit in persistence.xml in the test packages
 * 
 * Use this in your own project by:
 *   - Delete everything inside the setUp method, but first, observe how test data is created
 *   - Delete the single test method, and replace with your own tests
 * 
 */
public class FacadeTest {
  
EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);

DemoFacade facade = new DemoFacade(emf);
 
  /** Setup test data in the database to a known state BEFORE Each test */
  @Before
  public void setUp() {
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();
      //Delete all, since some future test cases might add/change data
      em.createQuery("DELETE FROM Car").executeUpdate();
      //Add our test data
      Car e1 = new Car("Volvo");
      Car e2 = new Car("WW");
      em.persist(e1);
      em.persist(e2);
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }
  
  @Test
  public void testGetAllCars(){
      List<Car> allCars = facade.getAllCars();
      Assert.assertEquals(2, allCars.size());
  }
  
  @Test
  public void testGetCarsByMake(){
      String make = "WW";
      List<Car> carsByMake = facade.getCarsByMake(make);
      Assert.assertEquals(1, carsByMake.size());
  }
  
//  @Test
//  public void testGetCarById(){
//      int id = 1;
//      Car carById = facade.getCarById(id);
//      Assert.assertEquals("Volvo", carById.getMake());
//  }
//  
//  @Test
//  public void testDeleteCarByID(){
//      int id = facade.getAllCars().size();
//      int expected = id - 1;
//      List<Car> carsById = facade.deleteCarByID(id);
//      Assert.assertEquals(expected, carsById.size());
//  }

//  // Test the single method in the Facade
//  @Test
//  public void countEntities() {
//    EntityManager em = emf.createEntityManager();
//    try {
//      long count = facade.countCars();
//      Assert.assertEquals(2,count);
//    } finally {
//      em.close();
//    }
//  }
  
}
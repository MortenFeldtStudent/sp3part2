package dbfacades;

import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/*
Simple Facade demo for this start-up project
 Use this in your own project by:
  - Rename this class to reflect your "business logic"
  - Delete the class entity.Car, and add your own entity classes
  - Delete the three public methods below, and replace with your own Facade Logic 
  - Delete all content in the main method

 */
public class DemoFacade {

    EntityManagerFactory emf;

    public DemoFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Car addCar(Car car) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return car;
        } finally {
            em.close();
        }
    }

    public List<Car> getAllCars() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Car> carList = em.createQuery("SELECT m FROM Car m").getResultList();
            em.getTransaction().commit();
            return carList;
        } finally {
            em.close();
        }
//    throw new UnsupportedOperationException("FIX ME");
    }

    public List<Car> getCarsByMake(String make) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT m FROM Car m WHERE m.make = :make");
            q.setParameter("make", make);
            List<Car> carsByMake = q.getResultList();
            em.getTransaction().commit();
            return carsByMake;
        } finally {
            em.close();
        }
    }

    public Car getCarById(int id) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT m FROM Car m WHERE m.id = :id");
        q.setParameter("id", id);
        Car car = (Car) q.getSingleResult();
        return car;
    }

    public List<Car> deleteCarByID(int id) {
        EntityManager em = emf.createEntityManager();
        Car car = em.find(Car.class, id);
        em.getTransaction().begin();
        em.remove(car);
        em.getTransaction().commit();
        List<Car> carList = getAllCars();
        return carList;
    }

//  /*
//  This will only work when your have added a persistence.xml file in the folder: 
//     src/main/resources/META-INF
//  You can use the file: persistence_TEMPLATE.xml (in this folder) as your template
//  */
//  public static void main(String[] args) {
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
//    DemoFacade df = new DemoFacade(emf);
//    df.addCar(new Car("Volvo"));
//    df.addCar(new Car("WW"));
//    df.addCar(new Car("Jaguar"));
//    long numbOfCars = df.countCars();
//    System.out.println("Number of cars: "+numbOfCars);
//  }
}

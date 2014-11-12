package ejb;

import ejbInterface.AddBeanRemote;
import entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class AddBean
 */

@Stateless
public class AddBean implements AddBeanRemote {
    @PersistenceContext(name="jpaUnit")
    EntityManager em;


    public AddBean() { }

    public int add(int a, int b) {
        UserEntity user = new UserEntity();
        user
            .setUsername("jaimelive")
            .setPassword("passevite")
            .setName("Jaime Live")
            .setMail("jaime@live.com.pt")
            .setAdmin(true);
        em.persist(user);
        return a + b;
    }
}
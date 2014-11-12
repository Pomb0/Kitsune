package ejb;

import bean.User;
import ejbInterface.UserBeanRemote;
import entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 * User: Jaime
 * Date: 12/11/2014 - 10:30
 */
@Stateless
public class UserBean implements UserBeanRemote {
	@PersistenceContext(name="jpaUnit")
	EntityManager entityMan;

	@Override
	public boolean register(String username, String password, String mail, String name) {
		EntityTransaction eTrans = entityMan.getTransaction();
		eTrans.begin();
		UserEntity newUser = new UserEntity();
		newUser.setUsername(username)
				.setPassword(password)
				.setMail(mail)
				.setName(name);
		entityMan.persist(newUser);
		eTrans.commit();
		return true;
	}

	@Override
	public User auth(String username, String password) {
		return null;
	}

	@Override
	public User getUser(String username) {
		return null;
	}

	@Override
	public User getUser(int id) {
		return null;
	}
}
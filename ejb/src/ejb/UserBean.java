package ejb;

import bean.User;
import ejbInterface.UserBeanRemote;
import entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
	public Response register(String username, String password, String mail, String name) {
		try {
			UserEntity newUser = new UserEntity();
			newUser.setUsername(username)
					.setPassword(password)
					.setMail(mail)
					.setName(name);
			entityMan.persist(newUser);
		}catch (Exception e){
			return Response.DUPLICATE_USER;
		}
		return Response.OK;
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
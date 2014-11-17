package ejb;

import bean.User;
import ejbInterface.UserBeanRemote;
import entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
			while(e.getCause()!=null) e = (Exception) e.getCause();
			boolean buser = e.getMessage().contains("'" + username + "'");
			boolean bmail = e.getMessage().contains("'" + mail + "'");
			if(buser && !bmail) return Response.DUPLICATE_USER;
			if(bmail && !buser) return Response.DUPLICATE_MAIL;
			return Response.UNKNOWN;
		}
		return Response.OK;
	}


	@Override
	public User auth(String username, String password) {
		User user = getUser(username);
		if(user == null || !user.getPassword().equals(password)) return null;
		return user;
	}

	@Override
	public User getUser(String username) {
		Query query = entityMan.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username");
		query.setParameter("username", username);
		List<UserEntity> user = query.getResultList();
		if(user == null || user.isEmpty()) return null;
		return userEntity2Bean(user.iterator().next());
	}

	@Override
	public User getUser(int id) {

		UserEntity user = entityMan.find(UserEntity.class, id);
		if(user==null) return null;
		return userEntity2Bean(user);
	}

	@Override
	public Response updateUser(User user) {
		try {
			UserEntity userEntity = entityMan.find(UserEntity.class, user.getId());
			userEntity.setUsername(user.getUsername())
					.setMail(user.getMail())
					.setName(user.getName())
					.setAdmin(user.isAdmin());
			//Optional password update.
			if(user.getPassword() != null) userEntity.setPassword(user.getPassword());
		}catch (Exception e){
			while(e.getCause()!=null) e = (Exception) e.getCause();
			boolean buser = e.getMessage().contains("'" + user.getUsername() + "'");
			boolean bmail = e.getMessage().contains("'" + user.getMail() + "'");
			if(buser && !bmail) return Response.DUPLICATE_USER;
			if(bmail && !buser) return Response.DUPLICATE_MAIL;
			return Response.UNKNOWN;
		}
		return Response.OK;
	}

	private User userEntity2Bean(UserEntity userEntity){
		if(userEntity==null) return null;
		return new User()
				.setId(userEntity.getId())
				.setUsername(userEntity.getUsername())
				.setPassword(userEntity.getPassword())
				.setMail(userEntity.getMail())
				.setName(userEntity.getName())
				.setAdmin(userEntity.isAdmin());
	}

}
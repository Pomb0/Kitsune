package ejbInterface;

import bean.User;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: Jaime
 * Date: 12/11/2014 - 10:27
 */

@Remote
public interface UserBeanRemote {
	public Response register(String username, String password, String mail, String name);
	public User auth(String username, String password);
	public User getUser(String username);
	public User getUser(int id);
	public Response updateUser(User user);
	public List<User> getUserList();

	public enum Response{
		OK(1), DUPLICATE_USER(2), DUPLICATE_MAIL(3), UNKNOWN(4);
		private int value;
		private Response(int value) {
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
}


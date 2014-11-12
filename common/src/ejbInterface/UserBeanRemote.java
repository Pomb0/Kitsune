package ejbInterface;

import bean.User;

import javax.ejb.Remote;

/**
 * User: Jaime
 * Date: 12/11/2014 - 10:27
 */

@Remote
public interface UserBeanRemote {
	public boolean register(String username, String password, String mail, String name);
	public User auth(String username, String password);
	public User getUser(String username);
	public User getUser(int id);
}

package ejbInterface;

import javax.ejb.Asynchronous;

/**
 * User: Jaime
 * Date: 14/11/2014 - 08:04
 */
public interface MailBeanRemote {
	@Asynchronous
	public void sendMail(String from, String to, String subject, String body);
}

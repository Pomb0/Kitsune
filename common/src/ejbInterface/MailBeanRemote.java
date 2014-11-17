package ejbInterface;

/**
 * User: Jaime
 * Date: 14/11/2014 - 08:04
 */
public interface MailBeanRemote {
	public void sendMail(String from, String to, String subject, String body);
}

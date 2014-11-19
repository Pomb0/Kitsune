package ejb;

import ejbInterface.MailBeanRemote;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * User: Jaime
 * Date: 14/11/2014 - 08:04
 */
@Stateless
public class MailBean implements MailBeanRemote {
	@Resource(mappedName = "java:jboss/mail/postfix")
	private Session mailSession;

	@Override
	@Asynchronous
	public void sendMail(String from, String to, String subject, String body) {
		try {
			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(body, "text/html; charset=\"UTF-8\"");
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

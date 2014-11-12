package ejb;

import ejbInterface.AddBeanRemote;

import javax.ejb.Stateless;


/**
 * Session Bean implementation class AddBean
 */

@Stateless
public class AddBean implements AddBeanRemote {
    //@Resource(mappedName = "java:jboss/mail/postfix")
    //private Session mailSession;
    public AddBean() { }

    public int add(int a, int b) {

       /* try {

            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("reply@slimecraft.pt"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("jaime@live.com.pt"));
            message.setSubject("Fak off");
            message.setText("No lighter for you");

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }    */


        return a + b;
    }


}
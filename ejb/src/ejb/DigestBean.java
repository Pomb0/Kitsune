package ejb;

import ejbInterface.MailBeanRemote;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 * User: Jaime
 * Date: 14/11/2014 - 08:02
 */

@Stateless
public class DigestBean {
	@EJB
	MailBeanRemote mailBean;

	@Schedule(second="*/60", minute="*",hour="*", persistent=false)
	public void run(){
		//mailBean.sendMail("reply@slimecraft.pt","pdomingos.costa@gmail.com", "Fak off", "WHere's my lighter?");
		System.out.println("I AM TRYING TO WORK HERE!");
	}
}


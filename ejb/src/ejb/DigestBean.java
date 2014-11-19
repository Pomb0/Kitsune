package ejb;

import bean.Article;
import bean.User;
import ejbInterface.MailBeanRemote;
import ejbInterface.NewsBeanRemote;
import ejbInterface.UserBeanRemote;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: Jaime
 * Date: 14/11/2014 - 08:02
 */

@Stateless
public class DigestBean {
	@EJB
	MailBeanRemote mailBean;
	@EJB
	NewsBeanRemote newsBean;
	@EJB
	UserBeanRemote userBean;

	@Schedule(hour="*/10", persistent=false)
	public void run(){
		String digest = getDigest(12);
		List<User> users = userBean.getUserList();

		System.out.println(">>Initiating digest delivery.");
		for(User u : users){
			mailBean.sendMail("reply@slimecraft.pt", u.getMail(), "Kitsune Digest", digest);
		}
		System.out.println(">>Digest delivery finished.");
	}

	public String getDigest(int articleCount){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		StringBuilder digest = new StringBuilder();
	   	digest.append("<img src=\"http://root.slimecraft.pt/fox.png\" >");
		digest.append("<h1>Digest: ").append(dateFormat.format(new Date())).append("</h1>");
		digest.append("<ul>");

		NewsBeanRemote.PaginatedList page = newsBean.getArticlesPage(-1, 1, articleCount);
		List<Article> list = (List<Article>)page.getPage();

		for(Article a : list){
			digest
					.append("<li><a href=\"http://is.powertrip.pt/kitsune/article?id=")
					.append(a.getId())
					.append("\">")
					.append(a.getTitle())
					.append("</a></li>");
		}

		digest.append("<ul>");
		return digest.toString();
	}

}


package ejbInterface;

import bean.Article;
import bean.Topic;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: Jaime
 * Date: 13/11/2014 - 15:27
 */
@Remote
public interface NewsBeanRemote {
	public Article getArticle(int id);
	public List<Topic> getTopics();
	public void addArticle(Article article);
}

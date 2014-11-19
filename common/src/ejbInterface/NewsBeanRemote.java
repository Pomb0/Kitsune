package ejbInterface;

import bean.Article;
import bean.Author;
import bean.Topic;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.Date;
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
	public PaginatedList getArticlesPage(int topicId, int page, int perPage);

	public List<Author> getAuthors();

	public PaginatedList getSearchPage(Integer topicId, Integer authorId, Date dateLimit, String textSearch, int page, int perPage);

	class PaginatedList implements Serializable{
		private long total;
		private List page;

		public PaginatedList() {
		}

		public PaginatedList(int total, List<Object> page) {
			this.total = total;
			this.page = page;
		}

		public List getPage() {
			return page;
		}

		public long getTotal() {
			return total;
		}

		public PaginatedList setTotal(final long total) {
			this.total = total;
			return this;
		}

		public PaginatedList setPage(final List page) {
			this.page = page;
			return this;
		}


	}
}

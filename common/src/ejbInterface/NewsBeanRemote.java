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

	public List<Author> getAuthors();

	public PaginatedList getSearchPage(Integer topicId, Integer authorId, Date dateLimit, String textSearch, int pageNumber, int pageSize);

	class PaginatedList implements Serializable{
		private long total;
		private List page;
		private QueryType type;

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

		public QueryType getType() {
			return type;
		}

		public PaginatedList setTotal(final long total) {
			this.total = total;
			return this;
		}

		public PaginatedList setPage(final List page) {
			this.page = page;
			return this;
		}

		public PaginatedList setType(final QueryType type) {
			this.type = type;
			return this;
		}

		public enum QueryType{
			NORMAL(1), TOPIC(2), AUTHOR(3), DATE(4), TEXT_SEARCH(5);
			private int value;
			private QueryType(int value) {
				this.value = value;
			}
			public int getValue(){
				return value;
			}
		}

	}
}

package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * User: Jaime
 * Date: 12/11/2014 - 06:47
 */
@Entity
public class TopicEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "topic")
	private Set<ArticleEntity> articles;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<ArticleEntity> getArticles() {
		return articles;
	}

	public TopicEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public TopicEntity setName(final String name) {
		this.name = name;
		return this;
	}

	public TopicEntity setArticles(final Set<ArticleEntity> articles) {
		this.articles = articles;
		return this;
	}


}

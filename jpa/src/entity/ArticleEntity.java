package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * User: Jaime
 * Date: 12/11/2014 - 06:42
 */
@Entity
public class ArticleEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@Column(unique = true, nullable = false)
	private String url;
	@ManyToOne
	private TopicEntity topic;

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("id ASC")
	private Set<AuthorEntity> authors;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
	@OrderBy("id ASC")
	private Set<HighlightEntity> highlights;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
	@OrderBy("id ASC")
	private Set<MediaEntity> media;

	@Column(columnDefinition = "text")
	private String body;

	public ArticleEntity() {
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public TopicEntity getTopic() {
		return topic;
	}

	public Set<AuthorEntity> getAuthors() {
		return authors;
	}

	public Date getDate() {
		return date;
	}

	public String getBody() {
		return body;
	}

	public Set<HighlightEntity> getHighlights() {
		return highlights;
	}

	public Set<MediaEntity> getMedia() {
		return media;
	}

	public ArticleEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public ArticleEntity setTitle(final String title) {
		this.title = title;
		return this;
	}

	public ArticleEntity setUrl(final String url) {
		this.url = url;
		return this;
	}

	public ArticleEntity setTopic(final TopicEntity topic) {
		this.topic = topic;
		return this;
	}

	public ArticleEntity setAuthors(final Set<AuthorEntity> authors) {
		this.authors = authors;
		return this;
	}

	public ArticleEntity setDate(final Date date) {
		this.date = date;
		return this;
	}

	public ArticleEntity setBody(final String body) {
		this.body = body;
		return this;
	}

	public ArticleEntity setMedia(final Set<MediaEntity> media) {
		this.media = media;
		return this;
	}

	public ArticleEntity setHighlights(final Set<HighlightEntity> highlights) {
		this.highlights = highlights;
		return this;
	}


}

package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Jaime
 * Date: 12/11/2014 - 06:47
 */
@Entity
public class MediaEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private boolean image = true;

	@Column(nullable = false)
	private String url;

	@ManyToOne
	private ArticleEntity article;

	public int getId() {
		return id;
	}

	public boolean isImage() {
		return image;
	}

	public String getUrl() {
		return url;
	}

	public ArticleEntity getArticle() {
		return article;
	}

	public MediaEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public MediaEntity setImage(final boolean image) {
		this.image = image;
		return this;
	}

	public MediaEntity setUrl(final String url) {
		this.url = url;
		return this;
	}

	public MediaEntity setArticle(final ArticleEntity article) {
		this.article = article;
		return this;
	}


}

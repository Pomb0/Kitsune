package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Jaime
 * Date: 12/11/2014 - 06:47
 */
@Entity
public class HighlightEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private ArticleEntity article;

	@Column(columnDefinition = "text", nullable = false)
	private String text;

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public ArticleEntity getArticle() {
		return article;
	}

	public HighlightEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public HighlightEntity setArticle(final ArticleEntity article) {
		this.article = article;
		return this;
	}

	public HighlightEntity setText(final String text) {
		this.text = text;
		return this;
	}


}

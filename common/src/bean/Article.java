package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: Jaime
 * Date: 12/11/2014 - 09:23
 */
public class Article implements Serializable{
	private int id;
	private String url;
	private String title;
	private Date date;
	private String body;
	private String topic;
	private List<String> highlights;
	private List<Author> authors;
	private List<String> images;
	private List<String> videos;


	public Article() {
	}

	@Override
	public String toString() {
		StringBuilder view = new StringBuilder();
		view.append(">>Article: ").append(id).append("\n")
				.append("    Title: ").append(title).append("\n")
				.append("    Url: ").append(url).append("\n")
				.append("    Topic: ").append(topic).append("\n")
				.append("    Date: ").append(date).append("\n")
				.append("    Body: ").append(body).append("\n");
		view.append("    >Authors\n");
		for(Author item : authors) view.append("        ").append(item.getName()).append("\n");
		view.append("    >HighLights\n");
		for(String item : highlights) view.append("        ").append(item).append("\n");
		view.append("    >Images\n");
		for(String item : images) view.append("        ").append(item).append("\n");
		view.append("    >Videos\n");
		for(String item : videos) view.append("        ").append(item).append("\n");

		return view.toString();
	}

	public int getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	public String getBody() {
		return body;
	}

	public String getTopic() {
		return topic;
	}

	public List<String> getHighlights() {
		return highlights;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<String> getVideos() {
		return videos;
	}

	public List<String> getImages() {
		return images;
	}

	public Article setId(final int id) {
		this.id = id;
		return this;
	}

	public Article setUrl(final String url) {
		this.url = url;
		return this;
	}

	public Article setTitle(final String title) {
		this.title = title;
		return this;
	}

	public Article setDate(final Date date) {
		this.date = date;
		return this;
	}

	public Article setBody(final String body) {
		this.body = body;
		return this;
	}

	public Article setTopic(final String topic) {
		this.topic = topic;
		return this;
	}

	public Article setHighlights(final List<String> highlights) {
		this.highlights = highlights;
		return this;
	}

	public Article setAuthors(final List<Author> authors) {
		this.authors = authors;
		return this;
	}

	public Article setImages(final List<String> images) {
		this.images = images;
		return this;
	}

	public Article setVideos(final List<String> videos) {
		this.videos = videos;
		return this;
	}


 }

package bean;

import java.io.Serializable;

/**
 * User: Jaime
 * Date: 14/11/2014 - 05:48
 */
public class Author implements Serializable{
	private int id;
	private String name;

	public Author(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Author() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Author setId(final int id) {
		this.id = id;
		return this;
	}

	public Author setName(final String name) {
		this.name = name;
		return this;
	}
}

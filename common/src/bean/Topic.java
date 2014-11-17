package bean;

import java.io.Serializable;

/**
 * User: Jaime
 * Date: 14/11/2014 - 09:44
 */
public class Topic implements Serializable{
	private int id;
	private String name;

	public Topic() {
	}

	public Topic(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Topic setId(final int id) {
		this.id = id;
		return this;
	}

	public Topic setName(final String name) {
		this.name = name;
		return this;
	}


}

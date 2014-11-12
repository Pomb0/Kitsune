package bean;

import java.io.Serializable;

/**
 * User: Jaime
 * Date: 12/11/2014 - 09:23
 */
public class User implements Serializable{
	private int id;
	private boolean admin;
	private String mail;
	private String username;
	private String name;

	public User() {
	}

	public boolean isAdmin() {
		return admin;
	}

	public String getMail() {
		return mail;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public User setId(final int id) {
		this.id = id;
		return this;
	}

	public User setAdmin(final boolean admin) {
		this.admin = admin;
		return this;
	}

	public User setMail(final String mail) {
		this.mail = mail;
		return this;
	}

	public User setUsername(final String username) {
		this.username = username;
		return this;
	}

	public User setName(final String name) {
		this.name = name;
		return this;
	}


}

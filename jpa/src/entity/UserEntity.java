package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Jaime
 * Date: 12/11/2014 - 05:56
 */

@Entity
public class UserEntity implements Serializable{
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String username;
	private String password;
	private String name;
	private String mail;
	private boolean admin = false;


	@Id
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public String getMail() {
		return mail;
	}

	public UserEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public UserEntity setUsername(final String username) {
		this.username = username;
		return this;
	}

	public UserEntity setPassword(final String password) {
		this.password = password;
		return this;
	}

	public UserEntity setName(final String name) {
		this.name = name;
		return this;
	}

	public UserEntity setMail(final String mail) {
		this.mail = mail;
		return this;
	}

	public UserEntity setAdmin(final boolean admin) {
		this.admin = admin;
		return this;
	}


}

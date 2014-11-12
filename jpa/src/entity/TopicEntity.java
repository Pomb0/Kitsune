package entity;

import javax.persistence.*;
import java.io.Serializable;

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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public TopicEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public TopicEntity setName(final String name) {
		this.name = name;
		return this;
	}


}

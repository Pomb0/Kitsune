package bean;

import java.io.Serializable;
import java.util.Date;

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

}

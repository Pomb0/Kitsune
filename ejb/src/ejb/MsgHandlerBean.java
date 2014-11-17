package ejb;

import bean.Article;
import ejbInterface.NewsBeanRemote;
import xml.XmlParser;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.StreamMessage;
import java.util.List;

/**
 * User: Jaime
 * Date: 11/11/2014 - 07:06
 */
@MessageDriven(name = "MsgHandlerBean", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/news3"),
		@ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=slimecraft.pt;port=8080"),
		@ActivationConfigProperty(propertyName = "user", propertyValue = "newsUser"),
		@ActivationConfigProperty(propertyName = "password", propertyValue = "newsPassword"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "durable"),
		@ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "beanSubscriber")
})


public class MsgHandlerBean implements MessageListener {
	XmlParser parser;
	@EJB
	NewsBeanRemote news;
	public MsgHandlerBean() {
	}

	@Override
	public void onMessage(Message message) {
		List<Article> articles;
		parser = new XmlParser();
		StreamMessage msg = (StreamMessage)message;
		try {
			String xml = msg.readString();
			articles = parser.parse(xml);

			for(Article pickl : articles){
				news.addArticle(pickl);
			}

		} catch (JMSException e) {
			System.out.println("#>Error reading JMS message.");
		}
	}
}

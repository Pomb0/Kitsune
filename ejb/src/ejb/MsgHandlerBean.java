package ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.StreamMessage;

/**
 * User: Jaime
 * Date: 11/11/2014 - 07:06
 */
@MessageDriven(name = "MsgHandlerEJB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/news3"),
		@ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=slimecraft.pt;port=8080"),
		@ActivationConfigProperty(propertyName = "user", propertyValue = "newsUser"),
		@ActivationConfigProperty(propertyName = "password", propertyValue = "newsPassword"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "durable"),
		@ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "beanSubscriber")
})


public class MsgHandlerBean implements MessageListener {
	public MsgHandlerBean() {
	}

	@Override
	public void onMessage(Message message) {
		StreamMessage msg = (StreamMessage)message;
		try {
			String xml = msg.readString();
		} catch (JMSException e) {
			System.out.println("#>Error reading JMS message.");
		}
	}
}

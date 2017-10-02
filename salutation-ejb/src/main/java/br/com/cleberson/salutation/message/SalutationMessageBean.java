package br.com.cleberson.salutation.message;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "SalutationQueue",
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/SalutationMDBQueue"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})
public class SalutationMessageBean implements MessageListener {

	public SalutationMessageBean() {
		
	}
	
	@Override
	public void onMessage(Message message) {
		
		try {
			String name = message.getStringProperty("name");
			Logger.getLogger("SalutationLog").log(Level.INFO, "Salutation processed " + name, name);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		
		
	}

}

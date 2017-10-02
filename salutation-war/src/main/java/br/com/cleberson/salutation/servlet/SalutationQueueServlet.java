package br.com.cleberson.salutation.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@JMSDestinationDefinitions(

		value = {
				@JMSDestinationDefinition(
						
						name = "java:/queue/SalutationMDBQueue",
						destinationName = "SalutationQueue",
						interfaceName = "javax.jms.Queue"
						
						)
		}
		
		)

@WebServlet(urlPatterns = "/SalutationQueueServlet")
public class SalutationQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 6131885971174495840L;
	
	@Inject
	private JMSContext context;
	@Resource(lookup = "java:/queue/SalutationMDBQueue")
	private Queue queue;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String message = "Salutation generated";
			
			String name = "Cleberson";
			
			JMSProducer jmsProducer = context.createProducer();
			
			Destination destination = queue;
			
			TextMessage textMessage = context.createTextMessage();
			
			textMessage.setText(message);
			textMessage.setStringProperty("name", name);
			
			jmsProducer.send(destination, textMessage);
			
			Logger.getLogger("SalutationLog").log(Level.WARNING, "Message sent successfully", "Message sent successfully2");
		} catch (JMSException e) {
			Logger.getLogger("SalutationLog").log(Level.WARNING, "JMSException in SalutationQueueServlet", "JSMException in SalutationQueueServlet");
		}
	}

}

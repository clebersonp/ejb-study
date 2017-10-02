package br.com.cleberson.salutation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cleberson.salutation.ejb.Salutation;
import br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient;

@WebServlet(urlPatterns = "/SalutationServlet")
public class SalutationServlet extends HttpServlet {

	private static final long serialVersionUID = 5813194053500787614L;
	
	@EJB
	private Salutation salutation;
	private SalutationWithJNDIOnClient salutationJNDI;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		
		Context context = null;
		try {
			StringBuilder lookup = new StringBuilder();
//			java:global/salutation-ear-0.0.1-SNAPSHOT/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient!br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient
//			java:app/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient!br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient
//			java:module/SalutationWithJNDIOnClient!br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient
//			java:global/salutation-ear-0.0.1-SNAPSHOT/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient
//			java:app/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient
//			java:module/SalutationWithJNDIOnClient
			
			
//			lookup.append("java:global/salutation-ear-0.0.1-SNAPSHOT/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient!br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient");
//			lookup.append("java:app/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient!br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient");
			lookup.append("java:module/SalutationWithJNDIOnClient!br.com.cleberson.salutation.ejb.SalutationWithJNDIOnClient");
//			lookup.append("java:global/salutation-ear-0.0.1-SNAPSHOT/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient");
//			lookup.append("java:app/salutation-war-0.0.1-SNAPSHOT/SalutationWithJNDIOnClient");
//			lookup.append("java:module/SalutationWithJNDIOnClient");
			context = new InitialContext();
			salutationJNDI = (SalutationWithJNDIOnClient) context.lookup(lookup.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		try {
			resp.setContentType("text/html;charset=UTF-8");
			
			StringBuilder sb = new StringBuilder();
			sb.append("<html>").append("<head>")
			.append("<title>")
			.append("Ejb Example")
			.append("</title>")
			.append("</head>")
			.append("<body>");
			
			sb.append("<h2>").append(salutation.getInformalSalutation("Cleberson Pauluci - with @EJB")).append("</h2>");
			sb.append("<h2>").append(salutationJNDI.getInformalSalutation("Cleberson Pauluci - with JNDI")).append("</h2>");
			
			sb.append("</body>").append("</html>");
			
			writer.write(sb.toString());
		} finally {
			writer.flush();
			writer.close();
		}
	}

}

package com.co.javeriana.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.co.javeriana.integration.layer.IntegrationQueueBDImpl;

@Component
public class RestProducerImpl {

	private static final Logger log = LoggerFactory.getLogger(RestProducerImpl.class);

	@Autowired
	private Environment env;

	/* origenes de los mensajes */
	private final static String BILL = "bill.txt";
	private final static String INVOICE = "invoice.txt";
	private final static String RECEIPTS = "receipts.txt";

	public void publishMessage(String origen, String message) {
		String metodo = "/accounting/";
		try {
			/* conecta con el servicio REST encargado de persistir los datos en la rdbms */
			if(BILL.equals(origen)) {
				
			}else if(INVOICE.equals(origen)) {		
				metodo += "invoice/";
			}else if(RECEIPTS.equals(origen)) {
				
			}
			RestInvoker.invokeService(env.getProperty("rest.service.host")+":"+env.getProperty("rest.service.port")+metodo, 
					"POST", env.getProperty("rest.service.accept"), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

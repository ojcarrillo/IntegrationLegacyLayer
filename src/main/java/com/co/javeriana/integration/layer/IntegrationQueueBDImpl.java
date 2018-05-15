package com.co.javeriana.integration.layer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.javeriana.queue.producer.QueueProducerImpl;
import com.co.javeriana.rest.client.RestInvoker;
import com.co.javeriana.rest.client.RestProducerImpl;
import com.co.javeriana.utils.MessageUtils;

@Service
public class IntegrationQueueBDImpl {
			
	private static final Logger log = LoggerFactory.getLogger(IntegrationQueueBDImpl.class);
	
	@Autowired
	private QueueProducerImpl queueService;
	
	@Autowired
	private RestProducerImpl restDBService;
	
	@Autowired
	private MessageUtils msgUtils;
	
	public void procesar(String fileName) {			
		File archivo = new File(fileName);
		if(!archivo.exists()) {
			log.error("No existe el archivo ["+fileName+"]");
			return;
		}
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    for(String linea; (linea = br.readLine()) != null; ) {
		    	dataRouter(archivo.getName(), linea);
		    }
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public void dataRouter(String origen, String dato) {
		/* ultimo caracter de la trama fija */
		String estado = dato.substring(dato.length()-1, dato.length());
		/* si esta anulada (A) remite hacia la base de datos */
		if("A".equals(estado)) {
			restDBService.publishMessage(origen, msgUtils.getXJSONfromString(origen, dato));
		}else{
			/* consume la cola */
			queueService.publishMessage(msgUtils.getXMLfromString(origen, dato));
		}
	}
}

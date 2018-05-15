package com.co.javeriana.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
	
	/* origenes de los mensajes */
	private final static String BILL = "bill.txt";
	private final static String INVOICE = "invoice.txt";
	private final static String RECEIPTS= "receipts.txt";
		
	public String getXMLfromString(String origen, String message) {
		/* segun el archivo de origen , obtiene el mensaje convertido a XML */
		if(BILL.equals(origen)) {
			return "<bill>" + getXMLByConf(message, getConfiguracionBill()) + "</bill>";  
		}else if(INVOICE.equals(origen)) {			
			return "<invoice>" + getXMLByConf(message, getConfiguracionInvoice()) + "</invoice>";
		}else if(RECEIPTS.equals(origen)) {
			return "<receipt>" + getXMLByConf(message, getConfiguracionReceipt()) + "</recepit>"; 
		}
		return "No hay mensaje para procesar";
	}
	
	public String getXJSONfromString(String origen, String message) {
		/* segun el archivo de origen , obtiene el mensaje convertido a J */
		if(BILL.equals(origen)) {
			return "{" + getJSONByConf(message, getConfiguracionBill()) + "}";  
		}else if(INVOICE.equals(origen)) {			
			return "{" + getJSONByConf(message, getConfiguracionInvoiceDB()) + "}";
		}else if(RECEIPTS.equals(origen)) {
			return "{" + getJSONByConf(message, getConfiguracionReceipt()) + "}"; 
		}
		return "No hay mensaje para procesar";
	}

	public String getXMLByConf(String linea, Map conf) {	
		/* iteramos la linea del archivo sobre la definicion */
		Iterator<Map.Entry<String, Integer[]>> it = conf.entrySet().iterator();
		/* para la salida en xml */
		StringBuilder salidaXML = new StringBuilder();		
		while (it.hasNext()) {
			/* crea la etiqueta para el xml de cada dato*/
		    Map.Entry<String, Integer[]> pair = it.next();
		    salidaXML.append("<"+pair.getKey().trim()+">");System.out.println(pair.getKey()+"->"+linea.substring(pair.getValue()[0],pair.getValue()[1])+"<-");
		    salidaXML.append(linea.substring(pair.getValue()[0],pair.getValue()[1]));
		    salidaXML.append("</"+pair.getKey().trim()+">");		    
		}		
		System.out.println("XML::"+salidaXML.toString());
		return salidaXML.toString();
	}
	
	public String getJSONByConf(String linea, Map conf) {	
		/* iteramos la linea del archivo sobre la definicion */
		Iterator<Map.Entry<String, Integer[]>> it = conf.entrySet().iterator();
		/* para la salida en xml */
		StringBuilder salidaXML = new StringBuilder();		
		while (it.hasNext()) {
			/* crea la etiqueta para el xml de cada dato*/
		    Map.Entry<String, Integer[]> pair = it.next();
		    salidaXML.append("\""+pair.getKey().trim()+"\":\"");System.out.println(pair.getKey()+"->"+linea.substring(pair.getValue()[0],pair.getValue()[1])+"<-");
		    salidaXML.append(linea.substring(pair.getValue()[0],pair.getValue()[1]));
		    salidaXML.append("\",");		    
		}
		System.out.println("JSON::"+salidaXML.toString());
		return salidaXML.toString().substring(0, salidaXML.toString().length()-1);
	}
	
	public Map<String, Integer[]> getConfiguracionBill() {
		/* definimos la estructura de las posiciones del mensaje */
		Map<String, Integer[]> objs = new HashMap<>();
		objs.put("numerofactura", new Integer[]{0,11});
		objs.put("fechafatura", new Integer[]{12,19});
		objs.put("numdoccliente", new Integer[]{20,34});
		objs.put("nombrecliente", new Integer[]{35,104});
		objs.put("tipocliente", new Integer[]{105,106});
		objs.put("porcdescuento", new Integer[]{106,110});
		objs.put("valdescuento", new Integer[]{111,127});
		objs.put("porcimpuestos", new Integer[]{128,132});
		objs.put("valimpuestos", new Integer[]{133,149});
		objs.put("valfactura", new Integer[]{150,167});
		objs.put("fechavencimiento", new Integer[]{168,174});
		objs.put("estadofactura", new Integer[]{175,175});
		return objs;
	}
	
	public Map<String, Integer[]> getConfiguracionInvoice() {
		/* definimos la estructura de las posiciones del mensaje */
		Map<String, Integer[]> objs = new HashMap<>();
		objs.put("numerofactura", new Integer[]{0,11});
		objs.put("fechafatura", new Integer[]{11,19});
		objs.put("numdoccliente", new Integer[]{19,34});
		objs.put("tipocliente", new Integer[]{34,35});
		objs.put("nombrecliente", new Integer[]{35,105});		
		objs.put("porcdescuento", new Integer[]{105,110});
		objs.put("valdescuento", new Integer[]{110,127});
		objs.put("porcimpuestos", new Integer[]{127,132});
		objs.put("valimpuestos", new Integer[]{132,149});
		objs.put("valfactura", new Integer[]{149,166});
		objs.put("fechavencimiento", new Integer[]{166,174});
		objs.put("estadofactura", new Integer[]{174,175});
		return objs;
	}

	
	public Map<String, Integer[]> getConfiguracionReceipt() {
		/* definimos la estructura de las posiciones del mensaje */
		Map<String, Integer[]> objs = new HashMap<>();
		objs.put("numerofactura", new Integer[]{0,11});
		objs.put("fechafatura", new Integer[]{12,19});
		objs.put("numdoccliente", new Integer[]{20,34});
		objs.put("nombrecliente", new Integer[]{35,104});
		objs.put("tipocliente", new Integer[]{105,106});
		objs.put("porcdescuento", new Integer[]{106,110});
		objs.put("valdescuento", new Integer[]{111,127});
		objs.put("porcimpuestos", new Integer[]{128,132});
		objs.put("valimpuestos", new Integer[]{133,149});
		objs.put("valpagado", new Integer[]{150,167});
		objs.put("estadofactura", new Integer[]{168,168});
		return objs;
	}
	
	
	public Map<String, Integer[]> getConfiguracionInvoiceDB() {
		/* definimos la estructura de las posiciones del mensaje */
		Map<String, Integer[]> objs = new HashMap<>();
		objs.put("numerofactura", new Integer[]{0,11});
		objs.put("fechaExpedicion", new Integer[]{11,19});
		objs.put("numDocumentoCliente", new Integer[]{19,34});
		objs.put("tipoCliente", new Integer[]{34,35});
		objs.put("nombreCliente", new Integer[]{35,105});		
		objs.put("porcentajeDescuento", new Integer[]{105,110});
		objs.put("descuentoProntoPago", new Integer[]{110,127});
		objs.put("porcentajeImpuestos", new Integer[]{127,132});
		objs.put("valorImpuestos", new Integer[]{132,149});
		objs.put("valor", new Integer[]{149,166});
		objs.put("fechaVencimiento", new Integer[]{166,174});
		objs.put("estadoFactura", new Integer[]{174,175});
		return objs;
	}
}

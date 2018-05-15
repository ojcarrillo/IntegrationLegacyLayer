package com.co.javeriana.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestInvoker {
	
	/*
	 * public static void main(String[] args) { String method = "GET"; String
	 * headers =
	 * "Accept:application/json,Content-Type:application/json; charset=utf8"; String
	 * paramsMapping = "idFactura:numeroFactura"; Pago pago = new Pago();
	 * pago.setNumeroConvenio(35423L); pago.setNumeroFactura(1234L);
	 * pago.setNumeroIdCliente("fsdfsdfds"); pago.setTipoIdCliente("CC");
	 * pago.setTotalPago(3434344.4344); String payloadMapping = null; String
	 * responseData = "valorFactura"; String serviceURL =
	 * "http://192.168.0.4:9090/servicios/pagos/v1/payments/{idFactura}"; String
	 * accept="application/json"; System.out .println(invokeService(serviceURL,
	 * method, paramsMapping, headers,accept, payloadMapping, responseData, pago));
	 * }
	 */

	public static String invokeService(String serviceURL, String method, String accept, String payload) {

		return invoke(serviceURL, method, accept, payload);
	}

	private static String invoke(String serviceURL, String method, String accept, String payload) {

		try {

			URL url = new URL(serviceURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", accept);
			conn.setRequestProperty("Accept", accept);
			if (!"GET".equals(method)) {
				OutputStream os = conn.getOutputStream();
				os.write(payload.getBytes());
				os.flush();
			}
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED
					&& conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			String response = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				response += output;
			}

			return response;

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return null;
	}

}

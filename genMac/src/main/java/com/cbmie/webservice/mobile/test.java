package com.cbmie.webservice.mobile;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class test {

	public static void main(String[] args) throws Exception {
		try {
			JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
			Client client = clientFactory.createClient("http://localhost:8080/genMac/webservice/mobileDetail?wsdl");
			Object[] result = client.invoke("agentImport", 6l);
			System.out.println(result[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.example.springwsbugdemo.customer;

import com.example.springwsbugdemo.customer.xml.CustomerCountRequest;
import com.example.springwsbugdemo.customer.xml.CustomerCountResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CustomerClient extends WebServiceGatewaySupport {

    public int getCustomerCount() {
        CustomerCountRequest request = new CustomerCountRequest();
        request.setCustomerName("John Doe");

        CustomerCountResponse response =
                (CustomerCountResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response.getCustomerCount();
    }
}

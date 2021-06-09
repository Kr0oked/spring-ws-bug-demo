package com.example.springwsbugdemo;

import com.example.springwsbugdemo.customer.CustomerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.RequestMatchers.soapEnvelope;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;
import static org.springframework.ws.test.client.ResponseCreators.withSoapEnvelope;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SpringWsBugDemoApplicationTests {

    @Autowired
    private CustomerClient client;

    private MockWebServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockWebServiceServer.createServer(client);
    }

    @Test
    void customerClientSoapEnvelope() {
        Source requestPayload = new StringSource(
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "    <SOAP-ENV:Header/>\n" +
                        "    <SOAP-ENV:Body>\n" +
                        "        <customerCountRequest>\n" +
                        "            <customerName>John Doe</customerName>\n" +
                        "        </customerCountRequest>\n" +
                        "    </SOAP-ENV:Body>\n" +
                        "</SOAP-ENV:Envelope>");
        Source responsePayload = new StringSource(
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "    <SOAP-ENV:Header/>\n" +
                        "    <SOAP-ENV:Body>\n" +
                        "        <customerCountResponse>\n" +
                        "            <customerCount>10</customerCount>\n" +
                        "        </customerCountResponse>\n" +
                        "    </SOAP-ENV:Body>\n" +
                        "</SOAP-ENV:Envelope>");

        mockServer
                .expect(soapEnvelope(requestPayload))
                .andRespond(withSoapEnvelope(responsePayload));

        int result = client.getCustomerCount();
        assertEquals(10, result);

        mockServer.verify();
    }

    @Test
    void customerClientPayload() {
        Source requestPayload = new StringSource(
                "<customerCountRequest>\n" +
                        "    <customerName>John Doe</customerName>\n" +
                        "</customerCountRequest>");
        Source responsePayload = new StringSource(
                "<customerCountResponse>\n" +
                        "    <customerCount>10</customerCount>\n" +
                        "</customerCountResponse>");

        mockServer
                .expect(payload(requestPayload))
                .andRespond(withPayload(responsePayload));

        int result = client.getCustomerCount();
        assertEquals(10, result);

        mockServer.verify();
    }
}

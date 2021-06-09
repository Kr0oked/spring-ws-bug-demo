# Spring WS Bug Demo

This demo shows a bug in Spring WS Test module. When using
`org.springframework.ws.test.client.RequestMatchers::soapEnvelope` in tests the verification fails:

```
Cannot find SOAP wrapper for element [SOAP-ENV:Envelope: null]
java.lang.IllegalArgumentException: Cannot find SOAP wrapper for element [SOAP-ENV:Envelope: null]
at com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl.find(SOAPDocumentImpl.java:590)
at com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl.find(SOAPDocumentImpl.java:578)
at com.sun.xml.messaging.saaj.soap.SOAPPartImpl.lookForEnvelope(SOAPPartImpl.java:155)
at com.sun.xml.messaging.saaj.soap.SOAPPartImpl.getEnvelope(SOAPPartImpl.java:135)
at com.sun.xml.messaging.saaj.soap.SOAPPartImpl.getDocumentElement(SOAPPartImpl.java:430)
at org.custommonkey.xmlunit.DifferenceEngine.compareNode(DifferenceEngine.java:191)
at org.custommonkey.xmlunit.DifferenceEngine.compare(DifferenceEngine.java:130)
at org.custommonkey.xmlunit.Diff.compare(Diff.java:241)
at org.custommonkey.xmlunit.Diff.appendMessage(Diff.java:364)
at org.custommonkey.xmlunit.Diff.toString(Diff.java:378)
at org.springframework.ws.test.support.matcher.SoapEnvelopeDiffMatcher.match(SoapEnvelopeDiffMatcher.java:61)
at org.springframework.ws.test.support.matcher.AbstractSoapMessageMatcher.match(AbstractSoapMessageMatcher.java:40)
at org.springframework.ws.test.client.WebServiceMessageMatcherAdapter.match(WebServiceMessageMatcherAdapter.java:43)
at org.springframework.ws.test.client.MockSenderConnection.send(MockSenderConnection.java:76)
at org.springframework.ws.client.core.WebServiceTemplate.sendRequest(WebServiceTemplate.java:658)
at org.springframework.ws.client.core.WebServiceTemplate.doSendAndReceive(WebServiceTemplate.java:606)
at org.springframework.ws.client.core.WebServiceTemplate.sendAndReceive(WebServiceTemplate.java:555)
at org.springframework.ws.client.core.WebServiceTemplate.marshalSendAndReceive(WebServiceTemplate.java:390)
at org.springframework.ws.client.core.WebServiceTemplate.marshalSendAndReceive(WebServiceTemplate.java:383)
at org.springframework.ws.client.core.WebServiceTemplate.marshalSendAndReceive(WebServiceTemplate.java:373)
at com.example.springwsbugdemo.customer.CustomerClient.getCustomerCount(CustomerClient.java:14)
at com.example.springwsbugdemo.SpringWsBugDemoApplicationTests.customerClientSoapEnvelope(SpringWsBugDemoApplicationTests.java:57)
...
```

This method worked in Spring Boot 2.4.4 but since Spring Boot 2.4.5 the described behaviour can be observed. Probably the upgrade of SAAJ at
https://github.com/spring-projects/spring-boot/issues/26104 causes this issue.

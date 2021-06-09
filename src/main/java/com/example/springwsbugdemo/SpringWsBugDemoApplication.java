package com.example.springwsbugdemo;

import com.example.springwsbugdemo.customer.CustomerClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@SpringBootApplication
public class SpringWsBugDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWsBugDemoApplication.class, args);
    }

    @Bean
    public CustomerClient customerClient(WebServiceTemplateBuilder webServiceTemplateBuilder) {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.example.springwsbugdemo.customer.xml");

        var webServiceTemplate = webServiceTemplateBuilder
                .setDefaultUri("https://example.com/customerService")
                .setMarshaller(marshaller)
                .setUnmarshaller(marshaller)
                .build();

        var customerClient = new CustomerClient();
        customerClient.setWebServiceTemplate(webServiceTemplate);
        return customerClient;
    }
}

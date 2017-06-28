package microservices;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import javax.jms.ConnectionFactory;

/**
 * Created by ellioe03 on 27/06/2017.
 */
@Configuration
//@ConditionalOnClass({ JmsTemplate.class, ConnectionFactory.class })
//@EnableConfigurationProperties(JmsTemplateProperties.class)
public class ApplicationConfig {
    @Named
    static class JerseyConfig extends ResourceConfig {
        public JerseyConfig() {
            this.packages("microservices");
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
//
//    @EnableConfigurationProperties(ActiveMQConnectionFactoryProperties.class)
//    @ConfigurationProperties()
//    public static class ActiveMQConnectionFactoryProperties {
//
//        private String brokerURL = "tcp://localhost:61616";
//
//        private boolean inMemory = true;
//
//        private boolean pooled = false;
//
//        // Will override brokerURL if inMemory is set to true
//        public String getBrokerURL() {
//            if (this.inMemory) {
//                return "vm://localhost";
//            }
//            else {
//                return this.brokerURL;
//            }
//        }
}

package io.mosip.registration.processor.print.config;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PrintStageConfig {

    @Value("${config.server.file.storage.uri}")
    private String configServerFileStorageURL;

    @Value("${mosip.registration.processor.print.mvel.file}")
    private String mvelFile;

    @Autowired
    private RestTemplate restTemplate;

    @Bean("varresolver")
    public VariableResolverFactory getVariableResolverFactory() {
        String mvelExpression = restTemplate.getForObject(configServerFileStorageURL + mvelFile, String.class);
        VariableResolverFactory functionFactory = new MapVariableResolverFactory();
        MVEL.eval(mvelExpression, functionFactory);
        return functionFactory;
    }
}

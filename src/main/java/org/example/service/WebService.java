package org.example.service;

import org.example.config.WebServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@EnableConfigurationProperties(WebServiceProperties.class)
@Service("org.example.service.WebService")
public class WebService {

    private final WebServiceRepo webServiceRepo;

    private WebServiceProperties webServiceProperties;

    public WebService(WebServiceRepo webServiceRepo) {
        this.webServiceRepo = webServiceRepo;
    }

    public void setWebServiceProperties(WebServiceProperties webServiceProperties) {
        this.webServiceProperties = webServiceProperties;
    }

    public String getMessage() {
        return webServiceProperties.getController();
    }

    public String getRepoMessage() {
        return webServiceRepo.getMessage("염정현");
    }
}

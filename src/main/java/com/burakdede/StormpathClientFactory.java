package com.burakdede;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * Created by burakdede on 03/03/16.
 */
public class StormpathClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(StormpathClientFactory.class);

    private static final String API_KEY_ID     = "apiKey.id";
    private static final String API_KEY_SECRET = "apiKey.secret";

    @NotNull
    @JsonProperty
    private String apiKeyId;

    @NotNull
    @JsonProperty
    private String apiKeySecret;

    @NotNull
    @JsonProperty
    private String apiRestUrl;

    private Client client;

    private Application application;

    public String getApiKeyId() {
        return apiKeyId;
    }

    public void setApiKeyId(String apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public String getApiKeySecret() {
        return apiKeySecret;
    }

    public void setApiKeySecret(String apiKeySecret) {
        this.apiKeySecret = apiKeySecret;
    }

    public String getApiRestUrl() {
        return apiRestUrl;
    }

    public void setApiRestUrl(String apiRestUrl) {
        this.apiRestUrl = apiRestUrl;
    }

    public Client getClient() {
        return client;
    }

    public Application getApplication() {
        return application;
    }

    public Application buildStormpathApplication(Environment env) {

        final Properties apiKeyProperties = new Properties();
        apiKeyProperties.setProperty(API_KEY_ID, getApiKeyId());
        apiKeyProperties.setProperty(API_KEY_SECRET, getApiKeySecret());

        // setup stormapth client with apikey properties
        ApiKey apiKey = ApiKeys.builder().setProperties(apiKeyProperties).build();
        Client client = Clients.builder().setApiKey(apiKey).build();
        this.client = client;

        // setup stormpath application
        Application application = client.getResource(getApiRestUrl(), Application.class);
        this.application = application;

        return application;
    }

}

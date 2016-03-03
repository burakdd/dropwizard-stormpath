package com.burakdede;

import com.google.common.base.Optional;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by burakdede on 03/03/16.
 */
public abstract class StormpathBundle<T extends Configuration>
                                    implements ConfiguredBundle<T>, IClientFactory {

    private final static Logger LOGGER = LoggerFactory.getLogger(StormpathBundle.class);

    @Override
    public void initialize(Bootstrap bootstrap) {
        // do nothing
    }

    @Override
    public void run(T conf, Environment environment) throws Exception {
        Optional<StormpathClientFactory> optionalFactory = getStormpathClientFactory(conf);
        if (optionalFactory.isPresent()) {
            StormpathClientFactory stormpathClientFactory = optionalFactory.get();
            stormpathClientFactory.buildStormpathApplication(environment);
        } else {
            LOGGER.error("Stormpath configuration not found on yaml");
        }
    }
}

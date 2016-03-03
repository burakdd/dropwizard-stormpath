/*
 * Copyright (C) Burak Dede.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

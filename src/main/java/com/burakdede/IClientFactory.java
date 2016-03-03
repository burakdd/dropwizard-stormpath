package com.burakdede;

import com.google.common.base.Optional;
import io.dropwizard.Configuration;

/**
 * Created by burakdede on 03/03/16.
 */
public interface IClientFactory<T extends Configuration> {

    Optional<StormpathClientFactory> getStormpathClientFactory(T conf);
}

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

import com.burakdede.stormpath.StormpathApi;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.security.Principal;

/**
 * Created by burakdede on 03/03/16.
 */
public class BasicAuthenticator<T extends Principal> implements Authenticator<BasicCredentials, T> {

    @Override
    public Optional<T> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Optional<T> accountOptional = Optional.absent();
        final T account = (T) StormpathApi.authenticate(basicCredentials.getUsername(),
                                                        basicCredentials.getPassword());
        accountOptional = Optional.fromNullable(account);

        return accountOptional;
    }
}

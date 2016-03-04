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
package com.burakdede.stormpath;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by burakdede on 03/03/16.
 */
public abstract class StormpathApi {

    private final static Logger LOGGER = LoggerFactory.getLogger(StormpathApi.class);

    private static Application stormpathApplicaiton;

    private static Client stormpathClient;


    public static Application getStormpathApplicaiton() {
        return stormpathApplicaiton;
    }

    public static Client getStormpathClient() {
        return stormpathClient;
    }

    /**
     * This is where we inject stormpath stormpathApplicaiton instance
     *
     * @param application Application instance from stormpath
     */
    public static void initStormpathApi(Application application, Client client) {
        StormpathApi.stormpathClient = client;
        StormpathApi.stormpathApplicaiton = application;
    }

    /**
     * Authenticate user with given username and password
     *
     * @param username username string
     * @param password password string
     * @return Account instance or null
     */
    public static Account authenticate(String username, String password) {
        AuthenticationRequest request = UsernamePasswordRequest.builder()
                .setUsernameOrEmail(username)
                .setPassword(password)
                .build();

        try {
            AuthenticationResult result = stormpathApplicaiton.authenticateAccount(request);
            Account account = result.getAccount();
            LOGGER.info("Authenticating user with username: {} and password: {}",
                    new Object[]{account.getUsername(), "******"});
            return account;
        } catch (ResourceException e) {
            e.printStackTrace();
            LOGGER.error("Can not authenticate user with username: {} and password: {}",
                    new Object[] { username, "******"});
            LOGGER.debug("\"{}\", status={}, code={}, moreInfo=\"{}\"",
                    new Object[] {e.getDeveloperMessage(), e.getStatus(), e.getCode(), e.getMoreInfo()});
        }

        return null;
    }

}

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
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
    public static Account authenticate(String username, String password) throws ResourceException {

        AuthenticationRequest request = new UsernamePasswordRequest(username, password);

        AuthenticationResult result = getStormpathApplicaiton().authenticateAccount(request);
        Account authenticated = result.getAccount();
        LOGGER.info("Authenticating user with username: {} and password: {}", new Object[]{authenticated.getUsername(), "******"});

        return authenticated;
    }


    /**
     * Search for given email in account list
     *
     * @param email Email or regex pattern to search for e.g *.@stormpath
     * @return AccountList of users matching
     */
    public static AccountList searchUser(String email) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("email", email);
        AccountList accounts = getStormpathApplicaiton().getAccounts(queryParams);

        return accounts;
    }


    /**
     * Stormpath specific link account link
     *
     * @param href
     * @return
     */
    public static Account getAccount(String href) {
        return getStormpathClient().getResource(href, Account.class);
    }


    /**
     * Create new user account with given username and password
     *
     * @param givenName given name of user
     * @param surname last name of user
     * @param email email of user
     * @param password password of user
     * @return Account instance of new user
     * @throws ResourceException
     */
    public static Account createUser(String givenName, String surname, String email, String password) throws ResourceException {

        Account account = getStormpathClient().instantiate(Account.class);
        account.setGivenName(givenName);
        account.setSurname(surname);
        account.setEmail(email);
        account.setPassword(password);

        Account created = getStormpathApplicaiton().createAccount(account);

        return created;
    }

}

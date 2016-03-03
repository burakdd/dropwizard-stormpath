package com.burakdede;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by burakdede on 03/03/16.
 */
public class StormpathApi {

    private final static Logger LOGGER = LoggerFactory.getLogger(StormpathApi.class);

    private static Application application;

    public static void initStormpathApi(Application application) {
        StormpathApi.application = application;
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
            AuthenticationResult result = application.authenticateAccount(request);
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

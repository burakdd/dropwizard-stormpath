package com.burakdede;

import com.google.common.base.Optional;
import com.stormpath.sdk.account.Account;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by burakdede on 03/03/16.
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, Account> {

    @Override
    public Optional<Account> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Optional<Account> accountOptional = Optional.absent();
        final Account account = StormpathApi.authenticate(basicCredentials.getUsername(),
                                                            basicCredentials.getPassword());
        accountOptional = Optional.fromNullable(account);

        return accountOptional;
    }
}

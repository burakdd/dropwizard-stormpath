# Dropwizard Stormpath Bundle

**dropwizard-stormpath** is easy to use **bundle** around stormpath api for dropwizard applications.

# Documentation

## Setting up

### Config.yaml

    stormpath:
      apiKeyId: "your api key id"
      apiKeySecret: "your api key secret"
      apiRestUrl: "your stormpath rest api url"

### We need to add stormpath configuration factory to our main configuration factory

You need stormpath client factory in your main configuration factory

    @NotNull
    @Valid
    private StormpathClientFactory stormpathClientFactory;

    public ServiceConfiguration(@JsonProperty("stormpath") StormpathClientFactory stormpathClientFactory) {
        this.stormpathClientFactory = stormpathClientFactory;
    }

    @JsonProperty("stormpath")
    public StormpathClientFactory getStormpathClientFactory() {
        return stormpathClientFactory;
    }

### Create stormpath bundle

On dropwizard App class create your stormpath bundle

    private StormpathBundle<ServiceConfiguration> stormpathBundle =
        new StormpathBundle<ServiceConfiguration>() {
            @Override
            public Optional<StormpathClientFactory> getStormpathClientFactory(Configuration configuration) {
                return Optional.fromNullable(((ServiceConfiguration) configuration).getStormpathClientFactory());
            }
        };


### Register bundle

Add bundle to boostrap

    bootstrap.addBundle(stormpathBundle);

### Use api or extend

Use or extend StormpathApi with static methods

    Stormpathapi.authenticate("username", "pass");

### Basic Auth

Have builtin basic authenticator you can use with stormpath Account class

    BasicAuthFactory basicAuthFactory = new BasicAuthFactory(new BasicAuthenticator(), "HTTP AUTH", Account.class);
    env.jersey().register(AuthFactory.binder(basicAuthFactory));


# Download

Maven dependency

    <dependency>
        <groupId>com.burakdede</groupId>
        <artifactId>dropwizard-stormpath</artifactId>
        <version>0.0.2</version>
    </dependency>


If above dep. fails also include bintray

    <repositories>
        <repository>
            <id>bintray</id>
            <url>https://dl.bintray.com/burakdd/maven/</url>
        </repository>
    </repositories>

# License
 	Copyright (C) Burak Dede.
 
 	Licensed under the Apache License, Version 2.0 (the "License");
 	you may not use this file except in compliance with the License.
 	You may obtain a copy of the License at
 
    	   http://www.apache.org/licenses/LICENSE-2.0
 	
 	Unless required by applicable law or agreed to in writing, software
 	distributed under the License is distributed on an "AS IS" BASIS,
 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 	See the License for the specific language governing permissions and
 	limitations under the License.


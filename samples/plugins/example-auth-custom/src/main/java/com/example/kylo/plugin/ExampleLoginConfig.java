/**
 * 
 */
package com.example.kylo.plugin;

/*-
 * #%L
 * example-auth-custom
 * %%
 * Copyright (C) 2017 ThinkBig Analytics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thinkbiganalytics.auth.jaas.LoginConfiguration;
import com.thinkbiganalytics.auth.jaas.LoginConfigurationBuilder;
import com.thinkbiganalytics.auth.jaas.config.JaasAuthConfig;

/**
 * This is an example configuration that sets up a custom JAAS LoginModule as part of 
 * a Kylo plugin.  Its purpose is to define a bean representing a login module configuration
 * using the builder provided by the Kylo authentication framework.
 * 
 * @author Sean Felten
 */
@Configuration
public class ExampleLoginConfig {

    @Bean(name = "exampleLoginConfiguration")
    public LoginConfiguration servicesLdapLoginConfiguration(LoginConfigurationBuilder builder) {
        // This uses the same LoginModule and settings in the authentication process of both the Kylo UI and Kylo services.
        return builder
                .loginModule(JaasAuthConfig.JAAS_UI)
                    .moduleClass(ExampleLoginModule.class)
                    .controlFlag(LoginModuleControlFlag.REQUIRED)
                    .option(ExampleLoginModule.USERNAME, "dladmin")
                    .option(ExampleLoginModule.PASSWORD, "admin".toCharArray())
                    .add()
                .loginModule(JaasAuthConfig.JAAS_SERVICES)
                    .moduleClass(ExampleLoginModule.class)
                    .controlFlag(LoginModuleControlFlag.REQUIRED)
                    .option(ExampleLoginModule.USERNAME, "dladmin")
                    .option(ExampleLoginModule.PASSWORD, "admin".toCharArray())
                    .add()
                .build();
    }
}

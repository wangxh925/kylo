/**
 * 
 */
package com.thinkbiganalytics.auth.jaas.config;

/*-
 * #%L
 * thinkbig-security-auth
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.spi.LoginModule;

import com.thinkbiganalytics.auth.jaas.LoginConfiguration;
import com.thinkbiganalytics.auth.jaas.LoginConfigurationBuilder;

/**
 * Default implementation of LoginConfigurationBuilder.
 * @author Sean Felten
 */
public class DefaultLoginConfigurationBuilder implements LoginConfigurationBuilder {

    private DefaultLoginConfiguration configuration = new DefaultLoginConfiguration();
    
    public DefaultLoginConfigurationBuilder() {
    }

    /* (non-Javadoc)
     * @see com.thinkbiganalytics.auth.jaas.LoginConfigurationBuilder#loginModule(java.lang.String)
     */
    @Override
    public DefaultModuleBuilder loginModule(String appName) {
        return new DefaultModuleBuilder(this, appName);
    }

    /* (non-Javadoc)
     * @see com.thinkbiganalytics.auth.jaas.LoginConfigurationBuilder#build()
     */
    @Override
    public LoginConfiguration build() {
        return this.configuration;
    }

    protected void addEntry(String appName, AppConfigurationEntry configEntries) {
        this.configuration.addEntry(appName, configEntries);
    }
    
    public class DefaultModuleBuilder implements ModuleBuilder {
        
        private String appName;
        private Class<? extends LoginModule> moduleClass;
        private LoginModuleControlFlag flag;
        private Map<String, Object> options = new HashMap<>();
        private DefaultLoginConfigurationBuilder confBuilder;

        public DefaultModuleBuilder(DefaultLoginConfigurationBuilder parent, String appName) {
            this.appName = appName;
            this.confBuilder = parent;
        }

        @Override
        public ModuleBuilder moduleClass(Class<? extends LoginModule> moduleClass) {
            this.moduleClass = moduleClass;
            return this;
        }

        @Override
        public ModuleBuilder controlFlag(String flag) {
            if ("required".equals(flag)) {
                return controlFlag(LoginModuleControlFlag.REQUIRED);
            } else if ("requisite".equals(flag)) {
                return controlFlag(LoginModuleControlFlag.REQUISITE);
            } else if ("sufficient".equals(flag)) {
                return controlFlag(LoginModuleControlFlag.SUFFICIENT);
            } else if ("optional".equals(flag)) {
                return controlFlag(LoginModuleControlFlag.OPTIONAL);
            } else {
                throw new IllegalArgumentException("Unknown login module control flag: " + flag);
            }
        }

        @Override
        public ModuleBuilder controlFlag(LoginModuleControlFlag flag) {
            this.flag = flag;
            return this;
        }

        @Override
        public ModuleBuilder option(String name, Object value) {
            this.options.put(name, value);
            return this;
        }
        
        @Override
        public ModuleBuilder options(Map<String, Object> options) {
            this.options.putAll(options);
            return this;
        }

        @Override
        public LoginConfigurationBuilder add() {
            AppConfigurationEntry entry = new AppConfigurationEntry(this.moduleClass.getName(), this.flag, this.options);
            confBuilder.addEntry(this.appName, entry);
            return confBuilder;
        }
    }
    
    public class DefaultLoginConfiguration implements LoginConfiguration {

        private Map<String, List<AppConfigurationEntry>> configEntries = new HashMap<>();

        @Override
        public AppConfigurationEntry[] getApplicationEntries(String appName) {
            List<AppConfigurationEntry> list = this.configEntries.get(appName);
            return list != null ? list.toArray(new AppConfigurationEntry[list.size()]) : new AppConfigurationEntry[0];
        }
        
        @Override
        public Map<String, AppConfigurationEntry[]> getAllApplicationEntries() {
            return this.configEntries.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey(), 
                                                      e -> e.getValue().toArray(new AppConfigurationEntry[e.getValue().size()])));
        }
        
        protected void addEntry(String appName, AppConfigurationEntry entry) {
            List<AppConfigurationEntry> list = this.configEntries.get(appName);
            
            if (list == null) {
                list = new ArrayList<>();
                this.configEntries.put(appName, list);
            }
            
            list.add(entry);
        }
    }
}

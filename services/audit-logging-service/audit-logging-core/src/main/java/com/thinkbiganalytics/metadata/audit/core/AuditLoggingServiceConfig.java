/**
 * 
 */
package com.thinkbiganalytics.metadata.audit.core;

/*-
 * #%L
 * thinkbig-audit-logging-core
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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thinkbiganalytics.metadata.api.event.MetadataEventService;

/**
 *
 * @author Sean Felten
 */
@Configuration
public class AuditLoggingServiceConfig {

    @Bean
    public AuditLoggingService auditLoggingService(MetadataEventService eventService) {
        AuditLoggingService auditService = new AuditLoggingService();
        auditService.addListeners(eventService);
        return auditService;
    }
}

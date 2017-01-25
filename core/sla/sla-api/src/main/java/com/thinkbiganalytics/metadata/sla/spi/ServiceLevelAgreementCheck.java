package com.thinkbiganalytics.metadata.sla.spi;

/*-
 * #%L
 * thinkbig-sla-api
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

import com.thinkbiganalytics.metadata.sla.api.ServiceLevelAgreement;
import com.thinkbiganalytics.metadata.sla.api.ServiceLevelAgreementActionConfiguration;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Stores SLA Actions and their associated Cron Schedule Created by sr186054 on 7/21/16.
 */
public interface ServiceLevelAgreementCheck {

    interface ID extends Serializable {

    }

    /**
     * @return the unique ID of this SLACheck
     */
    ID getId();

    /**
     * @return the name of this SLACheck
     */
    String getName();

    /**
     * @return the time when this SLACheck was created
     */
    DateTime getCreatedTime();

    /**
     * @return a description of this SLACheck
     */
    String getDescription();


    ServiceLevelAgreement getServiceLevelAgreement();

    List<? extends ServiceLevelAgreementActionConfiguration> getActionConfigurations();

    String getCronSchedule();

}

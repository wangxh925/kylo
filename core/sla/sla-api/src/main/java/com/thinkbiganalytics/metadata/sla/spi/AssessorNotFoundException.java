/**
 * 
 */
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

import com.thinkbiganalytics.metadata.sla.api.Metric;

/**
 * Thrown when an assessor could not be found that can assess a particular kind of metric.
 * @author Sean Felten
 */
public class AssessorNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3406314751186303643L;
    
    private final Metric metric;
    
    public AssessorNotFoundException(Metric metric) {
        this.metric = metric;
    }

    public AssessorNotFoundException(String message, Metric metric) {
        super(message);
        this.metric = metric;
    }

    public Metric getMetric() {
        return metric;
    }
}

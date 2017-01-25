package com.thinkbiganalytics.datalake.authorization.model;

/*-
 * #%L
 * thinkbig-sentry-client
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

import java.util.List;

/**
 * Created by Jeremy Merrifield on 9/12/16.
 */
public class SentryGroups {

    private int totalCount;
    private List<SentryGroup> vXGroups;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<SentryGroup> getvXGroups() {
        return vXGroups;
    }

    public void setvXGroups(List<SentryGroup> vXGroups) {
        this.vXGroups = vXGroups;
    }
}

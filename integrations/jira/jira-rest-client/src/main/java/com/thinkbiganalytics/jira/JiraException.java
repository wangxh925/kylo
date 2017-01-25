package com.thinkbiganalytics.jira;

/*-
 * #%L
 * thinkbig-jira-rest-client
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

/**
 * Created by sr186054 on 10/16/15.
 */
public class JiraException extends Exception {

    public JiraException() {
    }

    public JiraException(String message) {
        super(message);
    }

    public JiraException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiraException(Throwable cause) {
        super(cause);
    }

    public JiraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

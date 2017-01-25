
package com.thinkbiganalytics.jira.domain;

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

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "startAt",
    "maxResults",
    "total",
    "worklogs"
})
public class Worklog {

    @JsonProperty("startAt")
    private Integer startAt;
    @JsonProperty("maxResults")
    private Integer maxResults;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("worklogs")
    private List<Object> worklogs = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The startAt
     */
    @JsonProperty("startAt")
    public Integer getStartAt() {
        return startAt;
    }

    /**
     * 
     * @param startAt
     *     The startAt
     */
    @JsonProperty("startAt")
    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    /**
     * 
     * @return
     *     The maxResults
     */
    @JsonProperty("maxResults")
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * 
     * @param maxResults
     *     The maxResults
     */
    @JsonProperty("maxResults")
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * 
     * @return
     *     The total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The worklogs
     */
    @JsonProperty("worklogs")
    public List<Object> getWorklogs() {
        return worklogs;
    }

    /**
     * 
     * @param worklogs
     *     The worklogs
     */
    @JsonProperty("worklogs")
    public void setWorklogs(List<Object> worklogs) {
        this.worklogs = worklogs;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

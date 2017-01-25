package com.thinkbiganalytics.jobrepo.query.model;

/*-
 * #%L
 * thinkbig-job-repository-core
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by sr186054 on 8/17/15.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultImpl implements SearchResult {

  private List<? extends Object> data;
  private Long recordsTotal;
  private Long recordsFiltered;
  private String error;

  @Override
  public List<? extends Object> getData() {
    return data;
  }

  @Override
  public void setData(List<? extends Object> data) {
    this.data = data;
  }

  @Override
  public Long getRecordsTotal() {
    return recordsTotal;
  }

  @Override
  public void setRecordsTotal(Long recordsTotal) {
    this.recordsTotal = recordsTotal;
  }

  @Override
  public Long getRecordsFiltered() {
    return recordsFiltered;
  }

  @Override
  public void setRecordsFiltered(Long recordsFiltered) {
    this.recordsFiltered = recordsFiltered;
  }

  @Override
  public String getError() {
    return error;
  }

  @Override
  public void setError(String error) {
    this.error = error;
  }
}

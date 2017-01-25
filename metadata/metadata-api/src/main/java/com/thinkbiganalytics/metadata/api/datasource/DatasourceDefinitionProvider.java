package com.thinkbiganalytics.metadata.api.datasource;

/*-
 * #%L
 * thinkbig-metadata-api
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

import com.thinkbiganalytics.metadata.api.BaseProvider;

import java.util.Set;

/**
 * Created by sr186054 on 11/15/16.
 */
public interface DatasourceDefinitionProvider extends BaseProvider<DatasourceDefinition, DatasourceDefinition.ID> {

    Set<DatasourceDefinition> getDatasourceDefinitions();

    DatasourceDefinition ensureDatasourceDefinition(String processorType);

    DatasourceDefinition findByProcessorType(String processorType);

    void removeAll();


}

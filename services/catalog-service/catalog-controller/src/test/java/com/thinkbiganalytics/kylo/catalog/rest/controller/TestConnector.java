/**
 * 
 */
package com.thinkbiganalytics.kylo.catalog.rest.controller;

/*-
 * #%L
 * kylo-catalog-controller
 * %%
 * Copyright (C) 2017 - 2018 ThinkBig Analytics, a Teradata Company
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

import com.thinkbiganalytics.metadata.api.catalog.Connector;
import com.thinkbiganalytics.metadata.api.catalog.DataSetSparkParameters;
import com.thinkbiganalytics.metadata.api.catalog.DataSource;

import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

/**
 * Test class to be further mocked/spied.
 */
public abstract class TestConnector implements Connector {

    @Override
    public String getSystemName() {
        return "sql_source";
    }

    @Override
    public String getTitle() {
        return "SQL Source";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public DataSetSparkParameters getSparkParameters() {
        return Mockito.spy(TestSparkParameters.class);
    }

    @Override
    public DataSetSparkParameters getEffectiveSparkParameters() {
        return getSparkParameters();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getPluginId() {
        return "mysql";
    }

    @Override
    public String getIcon() {
        return "amazon";
    }

    @Override
    public String getIconColor() {
        return "orange-700";
    }

    @Override
    public List<? extends DataSource> getDataSources() {
        return Collections.emptyList();
    }

}

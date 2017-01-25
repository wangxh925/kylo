package com.thinkbiganalytics.nifi.v2.ingest;

/*-
 * #%L
 * thinkbig-nifi-core-processors
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

import com.thinkbiganalytics.nifi.core.api.metadata.MetadataProviderService;
import com.thinkbiganalytics.nifi.core.api.metadata.MetadataRecorder;
import com.thinkbiganalytics.nifi.processor.AbstractNiFiProcessor;

import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.logging.ComponentLog;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.thinkbiganalytics.nifi.v2.ingest.IngestProperties.FEED_CATEGORY;
import static com.thinkbiganalytics.nifi.v2.ingest.IngestProperties.FEED_NAME;
import static com.thinkbiganalytics.nifi.v2.ingest.IngestProperties.METADATA_SERVICE;
import static com.thinkbiganalytics.nifi.v2.ingest.IngestProperties.REL_SUCCESS;

@EventDriven
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"thinkbig", "registration", "put"})
@CapabilityDescription("Saves the outcome of registration.")

public class UpdateRegistration extends AbstractNiFiProcessor {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    // Relationships

    private final Set<Relationship> relationships;

    public static final PropertyDescriptor RESULT = new PropertyDescriptor.Builder()
        .name("Result")
        .description("Indicates what should happen when a file with the same name already exists in the output directory")
        .required(true)
        .defaultValue(SUCCESS)
        .allowableValues(SUCCESS, FAIL)
        .build();

    private final List<PropertyDescriptor> propDescriptors;

    public UpdateRegistration() {
        HashSet r = new HashSet();
        r.add(REL_SUCCESS);

        this.relationships = Collections.unmodifiableSet(r);
        ArrayList pds = new ArrayList();
        pds.add(METADATA_SERVICE);
        pds.add(FEED_CATEGORY);
        pds.add(FEED_NAME);
        pds.add(RESULT);
        this.propDescriptors = Collections.unmodifiableList(pds);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return propDescriptors;
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile flowFile = session.get();
        if (flowFile == null) {
            return;
        }
        ComponentLog logger = getLog();
        try {
            final MetadataProviderService metadataService = context.getProperty(METADATA_SERVICE).asControllerService(MetadataProviderService.class);
            final String categoryName = context.getProperty(FEED_CATEGORY).evaluateAttributeExpressions(flowFile).getValue();
            final String feedName = context.getProperty(FEED_NAME).evaluateAttributeExpressions(flowFile).getValue();
            //final String result = context.getProperty(RESULT).getValue();

            final MetadataRecorder client = metadataService.getRecorder();

            // TODO: Restore when working
            //client.recordFeedInitialization(session, incoming, true);

            //TODO: Remove workaround
            client.recordFeedInitialization(categoryName, feedName);

        } catch (final Exception e) {
            logger.warn("Failed to update registration due to {}. Will proceed anyway resulting in new files going through registration.", new Object[]{flowFile, e});
        }
        session.transfer(flowFile, REL_SUCCESS);
    }

}

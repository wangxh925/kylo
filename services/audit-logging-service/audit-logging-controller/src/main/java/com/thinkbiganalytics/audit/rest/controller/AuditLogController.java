package com.thinkbiganalytics.audit.rest.controller;

/*-
 * #%L
 * thinkbig-audit-logging-controller
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
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import com.thinkbiganalytics.audit.rest.model.AuditLogEntry;
import com.thinkbiganalytics.metadata.api.MetadataAccess;
import com.thinkbiganalytics.metadata.api.audit.AuditLogProvider;

import io.swagger.annotations.Api;

@Component
@Api(tags = "Internal: Audit Log", produces = "application/json")
@Path("/v1/auditlog")
public class AuditLogController {

    @Inject
    private MetadataAccess metadataAccess;

    @Inject
    private AuditLogProvider auditProvider;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<AuditLogEntry> getList(@QueryParam("limit") @DefaultValue("10") int limit) {
        return metadataAccess.read(() -> {
            return this.auditProvider.list(limit).stream()
                            .map(transformer)
                            .collect(Collectors.toList());
        });
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public AuditLogEntry findById(@PathParam("id") String idStr) {
        return metadataAccess.read(() -> {
            com.thinkbiganalytics.metadata.api.audit.AuditLogEntry.ID id = auditProvider.resolveId(idStr);
            return this.auditProvider.findById(id)
                            .map(transformer)
                            .orElseThrow(() -> new WebApplicationException("No audit log entery exists with ID: " + idStr, Status.NOT_FOUND));
        });
    }


    private static final Function<com.thinkbiganalytics.metadata.api.audit.AuditLogEntry, AuditLogEntry> transformer
        = (domain) -> {
            AuditLogEntry entry = new AuditLogEntry();
            entry.setCreatedTime(domain.getCreatedTime());
            entry.setId(domain.getId().toString());
            entry.setType(domain.getType());
            entry.setUser(domain.getUser().getName());
            entry.setDescription(domain.getDescription());
            entry.setEntityId(domain.getEntityId().toString());
            return entry;
        };
}

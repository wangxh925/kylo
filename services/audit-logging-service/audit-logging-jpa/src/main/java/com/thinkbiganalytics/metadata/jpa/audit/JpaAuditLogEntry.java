/**
 * 
 */
package com.thinkbiganalytics.metadata.jpa.audit;

/*-
 * #%L
 * thinkbig-audit-logging-jpa
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

import java.io.Serializable;
import java.security.Principal;
import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.thinkbiganalytics.jpa.BaseJpaId;
import com.thinkbiganalytics.metadata.api.audit.AuditLogEntry;
import com.thinkbiganalytics.security.UsernamePrincipal;

/**
 * An audit log entry describing a metadata change of operation attempt.
 * 
 * @author Sean Felten
 */
@Entity
@Table(name = "AUDIT_LOG")
public class JpaAuditLogEntry implements AuditLogEntry {

    @EmbeddedId
    private AuditLogId id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "CREATE_TIME", nullable = false)
    private DateTime createdTime = DateTime.now();

    @Convert(converter = UsernamePrincipalConverter.class)
    @Column(name = "USER", columnDefinition = "varchar(100)")
    private UsernamePrincipal user;

    @Column(name = "LOG_TYPE", length = 45, nullable = false)
    private String type;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Column(name = "ENTITY_ID", length = 45)
    private String entityId;

    public JpaAuditLogEntry() {
        super();
    }

    public JpaAuditLogEntry(Principal user, String type, String description, String entityId) {
        super();
        this.id = AuditLogId.create();
        this.user = user instanceof UsernamePrincipal ? (UsernamePrincipal) user : new UsernamePrincipal(user.getName());
        this.type = type;
        this.description = description;
        this.entityId = entityId;
    }


    @Override
    public ID getId() {
        return this.id;
    }

    @Override
    public DateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public Principal getUser() {
        return user;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    public void setId(AuditLogId id) {
        this.id = id;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setUser(UsernamePrincipal user) {
        this.user = user;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }



    @Embeddable
    public static class AuditLogId extends BaseJpaId implements Serializable, AuditLogEntry.ID {

        private static final long serialVersionUID = 1L;

        @Column(name = "id", columnDefinition = "binary(16)")
        private UUID uuid;

        public static AuditLogId create() {
            return new AuditLogId(UUID.randomUUID());
        }


        public AuditLogId() {}

        public AuditLogId(Serializable ser) {
            super(ser);
        }

        @Override
        public UUID getUuid() {
            return this.uuid;
        }

        @Override
        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }

    public static class UsernamePrincipalConverter implements AttributeConverter<Principal, String> {
        @Override
        public String convertToDatabaseColumn(Principal principal) {
            return principal.getName();
        }

        @Override
        public Principal convertToEntityAttribute(String dbData) {
            return new UsernamePrincipal(dbData);
        }
    }

}

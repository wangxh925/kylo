package com.thinkbiganalytics.metadata.jpa.sla;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by sr186054 on 9/14/16.
 */
public interface JpaServiceLevelAssessmentRepository extends JpaRepository<JpaServiceLevelAssessment, JpaServiceLevelAssessment.ID> {

    @Query(" select assessment from JpaServiceLevelAssessment assessment where assessment.slaId = :id "
           + "and assessment.createdTime = (select max(assessment2.createdTime) "
           + "                              from JpaServiceLevelAssessment as assessment2 "
           + "                              where assessment2.slaId = :id)")
    JpaServiceLevelAssessment findLatestAssessment(@Param("id") String id);

}

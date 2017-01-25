package com.thinkbiganalytics.jobrepo.query.model.transform;

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

import com.thinkbiganalytics.jobrepo.query.model.DefaultExecutedFeed;
import com.thinkbiganalytics.jobrepo.query.model.DefaultFeedHealth;
import com.thinkbiganalytics.jobrepo.query.model.DefaultFeedStatus;
import com.thinkbiganalytics.jobrepo.query.model.ExecutedFeed;
import com.thinkbiganalytics.jobrepo.query.model.ExecutionStatus;
import com.thinkbiganalytics.jobrepo.query.model.FeedHealth;
import com.thinkbiganalytics.jobrepo.query.model.FeedStatus;
import com.thinkbiganalytics.metadata.api.feed.LatestFeedJobExecution;
import com.thinkbiganalytics.metadata.api.feed.OpsManagerFeed;
import com.thinkbiganalytics.metadata.api.jobrepo.job.BatchJobExecution;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sr186054 on 11/28/16.
 */
public class FeedModelTransform {


    public static ExecutedFeed executedFeed(BatchJobExecution jobExecution, OpsManagerFeed feed){

      return executedFeed(jobExecution,feed.getName());

    }

    public static ExecutedFeed executedFeed(BatchJobExecution jobExecution, String feedName){

        DefaultExecutedFeed executedFeed = new DefaultExecutedFeed();
        executedFeed.setFeedExecutionId(jobExecution.getJobExecutionId());
        executedFeed.setStartTime(jobExecution.getStartTime());
        executedFeed.setEndTime(jobExecution.getEndTime());
        executedFeed.setExitCode(jobExecution.getExitCode().name());
        executedFeed.setExitStatus(jobExecution.getExitMessage());
        executedFeed.setStatus(ExecutionStatus.valueOf(jobExecution.getStatus().name()));
        executedFeed.setName(feedName);
        executedFeed.setRunTime(ModelUtils.runTime(jobExecution.getStartTime(), jobExecution.getEndTime()));
        executedFeed.setTimeSinceEndTime(ModelUtils.timeSince(jobExecution.getStartTime(), jobExecution.getEndTime()));
        executedFeed.setFeedInstanceId(jobExecution.getJobInstance().getJobInstanceId());
        return executedFeed;

    }


    public static ExecutedFeed executedFeed(LatestFeedJobExecution jobExecution){

        DefaultExecutedFeed executedFeed = new DefaultExecutedFeed();
        executedFeed.setFeedExecutionId(jobExecution.getJobExecutionId());
        executedFeed.setStartTime(jobExecution.getStartTime());
        executedFeed.setEndTime(jobExecution.getEndTime());
        executedFeed.setExitCode(jobExecution.getExitCode().name());
        executedFeed.setExitStatus(jobExecution.getExitMessage());
        executedFeed.setStatus(ExecutionStatus.valueOf(jobExecution.getStatus().name()));
        executedFeed.setName(jobExecution.getFeedName());
        executedFeed.setRunTime(ModelUtils.runTime(jobExecution.getStartTime(), jobExecution.getEndTime()));
        executedFeed.setTimeSinceEndTime(ModelUtils.timeSince(jobExecution.getStartTime(), jobExecution.getEndTime()));
        executedFeed.setFeedInstanceId(jobExecution.getJobInstanceId());
        return executedFeed;

    }

    public static ExecutedFeed executedFeed(com.thinkbiganalytics.metadata.api.feed.FeedHealth feedHealth){

        DefaultExecutedFeed executedFeed = new DefaultExecutedFeed();
        executedFeed.setFeedExecutionId(feedHealth.getJobExecutionId());
        executedFeed.setStartTime(feedHealth.getStartTime());
        executedFeed.setEndTime(feedHealth.getEndTime());
        executedFeed.setExitCode(feedHealth.getExitCode().name());
        executedFeed.setExitStatus(feedHealth.getExitMessage());
        executedFeed.setStatus(ExecutionStatus.valueOf(feedHealth.getStatus().name()));
        executedFeed.setName(feedHealth.getFeedName());
        executedFeed.setRunTime(ModelUtils.runTime(feedHealth.getStartTime(), feedHealth.getEndTime()));
        executedFeed.setTimeSinceEndTime(ModelUtils.timeSince(feedHealth.getStartTime(), feedHealth.getEndTime()));
        executedFeed.setFeedInstanceId(feedHealth.getJobInstanceId());
        return executedFeed;

    }

    /**
     * Transforms the FeedHealth domain object to the Rest model object
     * @param domain
     * @return
     */
    public static List<FeedHealth> feedHealth(List<? extends com.thinkbiganalytics.metadata.api.feed.FeedHealth> domain){
        if(domain != null && !domain.isEmpty()){
        return domain.stream().map(d -> feedHealth(d)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static FeedHealth feedHealth(com.thinkbiganalytics.metadata.api.feed.FeedHealth domain){
        FeedHealth feedHealth = new DefaultFeedHealth();
        feedHealth.setUnhealthyCount(domain.getFailedCount());
        feedHealth.setHealthyCount(domain.getCompletedCount());
        feedHealth.setFeed(domain.getFeedName());
        feedHealth.setLastOpFeed(executedFeed(domain));
        return feedHealth;
    }




    public static FeedStatus feedStatus(List<FeedHealth> feedHealth){

        DefaultFeedStatus status = new DefaultFeedStatus(feedHealth);
        return status;

    }
}

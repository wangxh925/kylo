package com.thinkbiganalytics.metadata.api.jobrepo.nifi;

/*-
 * #%L
 * thinkbig-operational-metadata-api
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


import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by sr186054 on 8/23/16.
 */
public interface NifiFeedProcessorStatisticsProvider {

    public static enum TimeFrame {

        FIVE_MIN(new Long(1000 * 60 * 5), "Last 5 Minutes"), TEN_MIN(new Long(1000 * 60 * 10), "Last 10 Minutes"), THIRTY_MIN(new Long(1000 * 60 * 30), "Last 30 Minutes"),
        HOUR(new Long(1000 * 60 * 60), "Last Hour"),
        THREE_HOUR(HOUR.millis * 3, "Last 3 Hours"), FIVE_HOUR(HOUR.millis * 5, "Last 5 Hours"), TEN_HOUR(HOUR.millis * 10, "Last 10 Hours"),
        DAY(HOUR.millis * 24, " Last Day"), WEEK(DAY.millis * 7, "Last Week"), MONTH(DAY.millis * 30, " Last Month"), YEAR(DAY.millis * 365, "Last Year");

        protected Long millis;
        private String displayName;

        private TimeFrame(long millis, String displayName) {
            this.millis = millis;
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public DateTime startTime() {
            return DateTime.now().minus(millis);
        }

        public DateTime startTimeRelativeTo(DateTime dt) {
            return dt.minus(millis);
        }
    }

    NifiFeedProcessorStats create(NifiFeedProcessorStats t);

    List<? extends NifiFeedProcessorStats> findWithinTimeWindow(DateTime start, DateTime end);

    List<? extends NifiFeedProcessorStats> findForFeed(String feedName, DateTime start, DateTime end);

    List<? extends NifiFeedProcessorStats> findForFeedProcessorStatistics(String feedName, DateTime start, DateTime end);

    List<? extends NifiFeedProcessorStats> findForFeedProcessorStatistics(String feedName, TimeFrame timeFrame);

    List<? extends NifiFeedProcessorStats> findForFeedStatisticsGroupedByTime(String feedName, DateTime start, DateTime end);

    List<? extends NifiFeedProcessorStats> findForFeedStatisticsGroupedByTime(String feedName, TimeFrame timeFrame);

    Long findMaxEventId();

    Long findMaxEventId(String clusterNodeId);


}

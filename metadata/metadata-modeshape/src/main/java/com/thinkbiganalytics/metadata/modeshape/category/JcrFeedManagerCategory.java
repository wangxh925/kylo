package com.thinkbiganalytics.metadata.modeshape.category;

/*-
 * #%L
 * thinkbig-metadata-modeshape
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

import javax.jcr.Node;

import com.thinkbiganalytics.metadata.api.feed.Feed;
import com.thinkbiganalytics.metadata.api.feedmgr.category.FeedManagerCategory;
import com.thinkbiganalytics.metadata.modeshape.feed.JcrFeedManagerFeed;
import com.thinkbiganalytics.metadata.modeshape.support.JcrUtil;

/**
 * Created by sr186054 on 6/8/16.
 */
public class JcrFeedManagerCategory extends JcrCategory implements FeedManagerCategory {

    public static String ICON = "tba:icon";
    public static String ICON_COLOR = "tba:iconColor";

    public JcrFeedManagerCategory(Node node) {
        super(node);
    }

    public JcrFeedManagerCategory(JcrCategory category) {
        super(category.getNode());
    }

    @Override
    public String getIcon() {
        return getProperty(ICON, String.class, true);
    }

    @Override
    public String getIconColor() {
        return getProperty(ICON_COLOR, String.class, true);
    }


    public void setIcon(String icon) {
        setProperty(ICON, icon);
    }

    public void setIconColor(String iconColor) {
        setProperty(ICON_COLOR, iconColor);
    }


    @Override
    public List<? extends Feed> getFeeds() {

        List<JcrFeedManagerFeed> feeds = JcrUtil.getChildrenMatchingNodeType(this.node, "tba:feed", JcrFeedManagerFeed.class);
        return feeds;
    }
}

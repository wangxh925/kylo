/*
 * Copyright (c) 2016. Teradata Inc.
 */

package com.thinkbiganalytics.policy.standardization;

/*-
 * #%L
 * thinkbig-field-policy-default
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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by matthutton on 5/7/16.
 */
public class MaskLeavingLastFourDigitStandardizerTest {

    @Test
    public void testConvertValue() throws Exception {

        MaskLeavingLastFourDigitStandardizer cc = MaskLeavingLastFourDigitStandardizer.instance();
        assertEquals("XXXXXXXXXXXX8790", cc.convertValue("5100145505218790"));
        assertEquals("XXXX-XXXX-XXXX-8790", cc.convertValue("5100-1455-0521-8790"));
        assertEquals("XXX-XX-2015", cc.convertValue("560-60-2015"));
        assertEquals("2015", cc.convertValue("2015"));
        assertEquals("20", cc.convertValue("20"));
        assertEquals("", cc.convertValue(""));
        assertEquals("XXXXXXXXXXX9966", cc.convertValue("373327123279966"));
    }
}

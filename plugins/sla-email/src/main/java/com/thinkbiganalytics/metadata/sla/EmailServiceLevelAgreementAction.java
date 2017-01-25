package com.thinkbiganalytics.metadata.sla;

/*-
 * #%L
 * thinkbig-sla-email
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

import com.thinkbiganalytics.alerts.api.Alert;
import com.thinkbiganalytics.classnameregistry.ClassNameChange;
import com.thinkbiganalytics.metadata.sla.alerts.ServiceLevelAssessmentAlertUtil;
import com.thinkbiganalytics.metadata.sla.api.ServiceLevelAgreementAction;
import com.thinkbiganalytics.metadata.sla.api.ServiceLevelAgreementActionValidation;
import com.thinkbiganalytics.metadata.sla.api.ServiceLevelAssessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Email the users specified in the incoming Configuration class about the SLA violation
 *
 */
@ClassNameChange(classNames = {"com.thinkbiganalytics.metadata.sla.alerts.EmailServiceLevelAgreementAction"})
public class EmailServiceLevelAgreementAction implements ServiceLevelAgreementAction<EmailServiceLevelAgreementActionConfiguration> {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceLevelAgreementAction.class);
    @Inject
    private SlaEmailService emailService;

    @Override
    public boolean respond(EmailServiceLevelAgreementActionConfiguration actionConfiguration, ServiceLevelAssessment assessment, Alert a) {
        log.info("Responding to SLA violation.");
        String desc = ServiceLevelAssessmentAlertUtil.getDescription(assessment);
        String slaName = assessment.getAgreement().getName();
        String email = actionConfiguration.getEmailAddresses();
        log.info("Responding to SLA violation.  About to send an email for SLA {} ",slaName);
        //mail it
        emailService.sendMail(email, "SLA Violated: " + slaName, desc);

        return true;
    }

    public ServiceLevelAgreementActionValidation validateConfiguration(){

        if(emailService.isConfigured()){
            return ServiceLevelAgreementActionValidation.VALID;
        }
        else {
            return new ServiceLevelAgreementActionValidation(false,"Email connection information is not setup.  Please contact an administrator to set this up.");
        }

        /*
        try {
            emailService.testConnection();
            return ServiceLevelAgreementActionValidation.VALID;
        }catch (MessagingException e){
            return new ServiceLevelAgreementActionValidation(false,e.getMessage());
        }
        */
    }

}

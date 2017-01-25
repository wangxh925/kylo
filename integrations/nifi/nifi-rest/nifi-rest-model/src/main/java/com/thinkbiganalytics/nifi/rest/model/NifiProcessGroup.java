package com.thinkbiganalytics.nifi.rest.model;

/*-
 * #%L
 * thinkbig-nifi-rest-model
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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.thinkbiganalytics.nifi.rest.support.NifiProcessorValidationUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.nifi.web.api.dto.ProcessGroupDTO;
import org.apache.nifi.web.api.dto.ProcessorDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sr186054 on 1/22/16.
 */
public class NifiProcessGroup {

    public static String CONTROLLER_SERVICE_CATEGORY = "Controller Service";
    private ProcessGroupDTO processGroupEntity;

    private List<ProcessorDTO> activeProcessors;

    private ProcessorDTO  inputProcessor;

    private List<ProcessorDTO>  downstreamProcessors;

    private boolean success;

    private List<NifiProcessorDTO> errors;

    private boolean rolledBack = false;

    public NifiProcessGroup() {

    }

    public NifiProcessGroup(ProcessGroupDTO processGroupEntity, ProcessorDTO inputProcessor, List<ProcessorDTO> downstreamProcessors) {
        this.processGroupEntity = processGroupEntity;
        this.inputProcessor = inputProcessor;
        this.downstreamProcessors = downstreamProcessors;
        populateErrors();
        this.success =  !this.hasFatalErrors();
    }

    public NifiProcessGroup(ProcessGroupDTO processGroupEntity) {
        this.processGroupEntity = processGroupEntity;
        populateErrors();

        this.success = !this.hasFatalErrors();
    }

    private void populateErrors(){
        this.errors = new ArrayList<NifiProcessorDTO>();
        if(this.inputProcessor != null && this.processGroupEntity != null)
        {
            NifiProcessorDTO error = NifiProcessorValidationUtil.getProcessorValidationErrors(this.inputProcessor, false);
            if(error != null && !error.getValidationErrors().isEmpty()) {
                errors.add(error);
            }
        }
        if(this.downstreamProcessors != null && this.processGroupEntity != null)
        {
            List<NifiProcessorDTO> processorErrors = NifiProcessorValidationUtil.getProcessorValidationErrors(this.downstreamProcessors, true);
            if(processorErrors != null) {
                errors.addAll(processorErrors);
            }
        }

    }

    public void addError(String processGroupId, String processorId, NifiError.SEVERITY severity,String error, String errorType){
        final NifiProcessorDTO tmpDto = new NifiProcessorDTO("",processorId,processGroupId);
        final List<NifiProcessorDTO> dtos = Lists.newArrayList(Iterables.filter(errors, new Predicate<NifiProcessorDTO>() {
                @Override
            public boolean apply(NifiProcessorDTO nifiProcessorDTO) {
                return nifiProcessorDTO.equals(tmpDto);
            }
        }));
        if(dtos != null && !dtos.isEmpty()) {
            dtos.get(0).addError(severity,error,errorType);
        }
        else {
            tmpDto.addError(severity,error,errorType);
            this.errors.add(tmpDto);
        }

    }


    public void addError(NifiError.SEVERITY severity,String error, String errorType){
       addError(processGroupEntity.getName(),"",severity,error,errorType);

    }

    public void addError(NifiError error){
        addError(processGroupEntity.getName(),"",error.getSeverity(),error.getMessage(),error.getCategory());

    }

    public ProcessGroupDTO getProcessGroupEntity() {
        return processGroupEntity;
    }

    public List<NifiProcessorDTO> getErrors() {
        if (errors == null) {
            errors = new ArrayList<NifiProcessorDTO>();
        }
        return errors;
    }

    public boolean hasErrors(){
        return this.errors != null && !this.errors.isEmpty();
    }

    public boolean hasFatalErrors() {
        List<NifiProcessorDTO> fatalErrors = new ArrayList<>();
        if(errors != null && !errors.isEmpty()) {
            for(NifiProcessorDTO processor : errors){
                List<NifiError> errors = processor.getFatalErrors();
                     if(errors != null && !errors.isEmpty()) {
                         return true;
                     }
            }
        }
        return false;
    }


    public boolean isRolledBack() {
        return rolledBack;
    }

    public void setRolledBack(boolean rolledBack) {
        this.rolledBack = rolledBack;
    }

    public List<NifiProcessorDTO> getControllerServiceErrors(){
        return getErrorsForCategory(CONTROLLER_SERVICE_CATEGORY);
    }

    public List<NifiProcessorDTO> getErrorsForCategory(final String category){
        if(StringUtils.isBlank(category)){
            return null;
        }
        return Lists.newArrayList(Iterables.filter(getErrors(), new Predicate<NifiProcessorDTO>() {
            @Override
            public boolean apply(NifiProcessorDTO nifiProcessorDTO) {
                NifiError error = Iterables.tryFind(nifiProcessorDTO.getValidationErrors(), new Predicate<NifiError>() {
                    @Override
                    public boolean apply(NifiError nifiError) {
                        return category.equalsIgnoreCase(nifiError.getCategory());
                    }
                }).orNull();
                return error != null;
            }
        }));
    }


    public List<NifiError> getAllErrors(){
        List<NifiError> errors = new ArrayList<>();
        for(NifiProcessorDTO item : getErrors()) {
            if(item.getValidationErrors() != null && !item.getValidationErrors().isEmpty()) {
                errors.addAll(item.getValidationErrors());
            }
        }
        return errors;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

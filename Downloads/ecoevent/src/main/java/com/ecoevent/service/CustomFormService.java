package com.ecoevent.service;

import com.ecoevent.entity.CustomForm;
import com.ecoevent.entity.FormField;
import com.ecoevent.entity.FormSubmission;
import com.ecoevent.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomFormService {

    private final CustomFormRepository formRepository;
    private final FormFieldRepository fieldRepository;
    private final FormSubmissionRepository submissionRepository;

    public CustomFormService(CustomFormRepository formRepository,
                             FormFieldRepository fieldRepository,
                             FormSubmissionRepository submissionRepository) {
        this.formRepository = formRepository;
        this.fieldRepository = fieldRepository;
        this.submissionRepository = submissionRepository;
    }

    public List<CustomForm> findAllForms() {
        return formRepository.findAll();
    }

    public List<CustomForm> findActiveForms() {
        return formRepository.findByActiveTrueOrderByIdDesc();
    }

    public CustomForm findFormById(Long id) {
        return formRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Form not found: " + id));
    }

    public CustomForm saveForm(CustomForm form) {
        return formRepository.save(form);
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }

    public FormField saveField(FormField field) {
        return fieldRepository.save(field);
    }

    public List<FormField> findFieldsByForm(Long formId) {
        return fieldRepository.findByCustomFormIdOrderByDisplayOrderAsc(formId);
    }

    public void deleteField(Long id) {
        fieldRepository.deleteById(id);
    }

    public FormSubmission submitForm(Long formId, String data, String ip) {
        CustomForm form = findFormById(formId);
        FormSubmission submission = FormSubmission.builder()
                .customForm(form)
                .submittedData(data)
                .submitterIp(ip)
                .build();
        return submissionRepository.save(submission);
    }

    public List<FormSubmission> findSubmissionsByForm(Long formId) {
        return submissionRepository.findByCustomFormIdOrderBySubmittedAtDesc(formId);
    }
}

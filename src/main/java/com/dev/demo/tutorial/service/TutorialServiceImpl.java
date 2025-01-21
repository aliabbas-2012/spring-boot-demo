package com.dev.demo.tutorial.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import com.dev.demo.auth.repository.RoleRepository;
import com.dev.demo.base.BaseService;
import com.dev.demo.tutorial.model.Tutorial;
import com.dev.demo.tutorial.repository.TutorialRepository;
import com.dev.demo.validation.custom.validation.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service("TutorialService")
public class TutorialServiceImpl extends BaseService implements TutorialService, FieldValueExists {

    @Autowired
    TutorialRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Long getTotalTutorials() {
        return repository.count();
    }

    @Override
    public Page<Tutorial> getAllEntities(Pageable pageable, String search) {
        return repository.fetchAllTutorials(pageable, search);
    }

    @Override
    public Tutorial getEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }
    

    @Override
    public void createEntity(Tutorial entity) {
        repository.save(entity);
    }

    @Override
    public void updateEntity(Long id, Tutorial payload) {
        if (repository.existsById(id)) {
            Tutorial existingEntity = repository.findById(id).orElse(null);
            payload.setId(id);
            updateNonNullFields(payload, existingEntity);
            repository.save(Objects.requireNonNull(existingEntity));
        }
    }

    @Override
    public void deleteEntity(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (value == null || fieldName == null || fieldName.isEmpty()) {
            return false; // Skip validation for null or empty fields
        }

        String methodName = "existsBy" + capitalize(fieldName)+"AndIdNot";
        Method method = repository.getClass().getMethod(methodName, String.class, Long.class);

        // Dynamically invoke the method with the provided value
        return !(boolean) method.invoke(repository, value.toString(), getIdParameterFromRequest("users"));
    }
}
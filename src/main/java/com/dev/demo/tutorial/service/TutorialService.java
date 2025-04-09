package com.dev.demo.tutorial.service;


import com.dev.demo.tutorial.model.Tutorial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TutorialService {
    Long getTotalTutorials();
    Page<Tutorial> getAllEntities(Pageable pageable, String search,String[] filters, String[] searchColumns);
    Tutorial getEntityById(Long id);
    public Tutorial createEntity(Tutorial entity);
    Tutorial updateEntity(Long id, Tutorial payload) ;
    void deleteEntity(Long id);
}

package com.dev.demo.tutorial.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dev.demo.tutorial.model.Tutorial;
import com.dev.demo.tutorial.validation.request.CreateUpdateRequest;

@Mapper
public interface TutorialMapper {
    TutorialMapper INSTANCE = Mappers.getMapper(TutorialMapper.class);

    Tutorial toTutorial(CreateUpdateRequest createUpdateRequest);
}

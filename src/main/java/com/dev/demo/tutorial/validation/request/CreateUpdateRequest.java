package com.dev.demo.tutorial.validation.request;


import com.dev.demo.validation.custom.validation.FieldAuthorization;
import com.dev.demo.validation.custom.validation.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateUpdateRequest {

    private Long id; // For update scenarios

    @NotBlank(message = "Name is required.")
    @Size(min = 10, max = 80, message = "The name must be from 10 to 80 characters.")
    @Unique(service = "TutorialService", fieldName = "title", message = "Title must be unique")
    protected String title;

    protected String description;

    @FieldAuthorization(service = "TutorialService", fieldName = "published", message = "You cannot unpublish/publish ")
    private Boolean published;

}

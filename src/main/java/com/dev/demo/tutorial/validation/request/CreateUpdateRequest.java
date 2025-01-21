package com.dev.demo.tutorial.validation.request;


import com.dev.demo.validation.custom.validation.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateUpdateRequest {

    private Long id; // For update scenarios

    @NotBlank(message = "Name is required.")
    @Size(min = 10, max = 50, message = "The name must be from 3 to 20 characters.")
    @Unique(service = "TutorialService", fieldName = "email", message = "Title must be unique")
    protected String title;

    protected String description;

    private Boolean published;

}

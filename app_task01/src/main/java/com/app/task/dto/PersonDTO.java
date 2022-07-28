package com.app.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Integer idPerson;

    @NotEmpty
    @NotNull
    @Size(min = 3, max=80, message = "{firstname.size}")
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 3, max=80, message = "{lastname.size}")
    private String lastName;
}

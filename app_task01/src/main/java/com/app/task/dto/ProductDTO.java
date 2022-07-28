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
public class ProductDTO {

    private Integer idProduct;

    @NotEmpty
    @NotNull
    @Size(min = 3, max=80, message = "{name.size}")
    private String name;

    @NotEmpty
    @Size(min = 2, message = "{brand.size}")
    @NotNull
    private String brand;

    private Double price;

    @NotNull
    private String photoUrl;
}

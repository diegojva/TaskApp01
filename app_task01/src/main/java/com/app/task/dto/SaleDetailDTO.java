package com.app.task.dto;

import com.app.task.model.Product;
import com.app.task.model.Sale;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailDTO {

    private Integer idSaleDetail;

    @JsonIgnore
    @NotNull
    private Sale sale;

    @NotNull
    @NotEmpty
    private Product product;

    @NotNull
    @NotEmpty
    private Integer quantity;
}

package com.app.task.dto;

import com.app.task.model.Person;
import com.app.task.model.SaleDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

    private Integer idSale;

    @NotNull
    private Person person;

    @NotNull
    private LocalDateTime dateTime;

    private Double total;

    @NotNull
    private List<SaleDetail> details;
}

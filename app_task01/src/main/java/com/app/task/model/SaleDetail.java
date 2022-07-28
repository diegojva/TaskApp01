package com.app.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSaleDetail;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_sale", nullable = false,
            foreignKey = @ForeignKey (name="FK_SALE_DETAIL"))
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false,
            foreignKey = @ForeignKey (name="FK_SALE_DETAIL_PRODUCT"))
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}

package com.app.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSale;

    @ManyToOne
    @JoinColumn(name = "id_person", nullable = false,
            foreignKey = @ForeignKey (name="FK_SALE_PERSON"))
    private Person person;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Double total;

    @OneToMany(mappedBy = "sale", cascade = {CascadeType.ALL},
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SaleDetail> details;

}

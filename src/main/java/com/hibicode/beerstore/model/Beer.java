package com.hibicode.beerstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "beer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Beer {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "name.mandatory")
    private String name;

    @NotNull(message = "type.mandatory")
    private BeerType type;

    @DecimalMin(message = "volume.minValue", value = "0")
    @NotNull(message = "volume.mandatory")
    private BigDecimal volume;

    public boolean isNew() {
        return getId() == null;
    }

    public boolean isUpdate() {
        return getId() != null;
    }
}

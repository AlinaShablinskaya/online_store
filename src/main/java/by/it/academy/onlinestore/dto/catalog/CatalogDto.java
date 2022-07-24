package by.it.academy.onlinestore.dto.catalog;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CatalogDto {
    private Integer id;
    @NotBlank
    private String groupName;
}

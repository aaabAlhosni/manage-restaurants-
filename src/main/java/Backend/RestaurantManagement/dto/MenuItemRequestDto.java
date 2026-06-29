package Backend.RestaurantManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    private String description;

    @NotNull
    private Boolean available;
}

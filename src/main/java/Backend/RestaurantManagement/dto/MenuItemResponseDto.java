package Backend.RestaurantManagement.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MenuItemResponseDto {
    private Long menuItemId;
    private String name;
    private BigDecimal price;
    private String description;
    private boolean available;
}

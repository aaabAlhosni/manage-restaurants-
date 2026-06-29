package Backend.RestaurantManagement.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuResponseDto {
    private Long menuId;
    private String title;
    private String category;
    private List<MenuItemResponseDto> items;
}

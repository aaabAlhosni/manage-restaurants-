package Backend.RestaurantManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MenuRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String category;
}

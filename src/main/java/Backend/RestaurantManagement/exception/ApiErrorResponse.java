package Backend.RestaurantManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String message;
}

package mukhtarovich.uz.inventory_service.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class ApiResponse<T> {
    private Boolean status;
    private HttpStatus code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(Boolean status, HttpStatus code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}

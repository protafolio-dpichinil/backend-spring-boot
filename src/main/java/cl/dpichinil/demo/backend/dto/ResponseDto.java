package cl.dpichinil.demo.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private Boolean status;
    private String message;
    private Object data;

    public ResponseDto(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    public ResponseDto(Boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

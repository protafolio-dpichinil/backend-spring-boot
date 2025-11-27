package cl.dpichinil.demo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthDto {
    private String accessToken;
    private String tokenType;
    
    public JWTAuthDto(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }
}

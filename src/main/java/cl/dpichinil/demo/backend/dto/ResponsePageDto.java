package cl.dpichinil.demo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageDto {
    private int size;
    private int number;
    private long totalElements;
    private int totalPages;
    private Object content;
}

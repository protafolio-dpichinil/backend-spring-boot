package cl.dpichinil.demo.backend.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResponsePageDtoTest {

    @Test
    void builder_and_all_args_constructor_work() {
        ResponsePageDto built = ResponsePageDto.builder()
                .size(5)
                .number(1)
                .totalElements(20)
                .totalPages(4)
                .content(List.of("a", "b"))
                .build();

        assertThat(built.getSize()).isEqualTo(5);
        assertThat(built.getNumber()).isEqualTo(1);
        assertThat(built.getTotalElements()).isEqualTo(20);
        assertThat(built.getTotalPages()).isEqualTo(4);
        assertThat(built.getContent()).isInstanceOf(java.util.List.class);
        assertThat(((java.util.List<?>) built.getContent())).hasSize(2);

        ResponsePageDto manual = new ResponsePageDto(2, 0, 2L, 1, List.of("x"));
        assertThat(manual.getSize()).isEqualTo(2);
        assertThat(manual.getNumber()).isEqualTo(0);
        assertThat(manual.getTotalElements()).isEqualTo(2L);
        assertThat(manual.getContent()).isInstanceOf(List.class);
    }
}

package cl.dpichinil.demo.backend.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseDtoTest {

    @Test
    void constructors_and_getters_work() {
        ResponseDto a = new ResponseDto(true, "ok");
        assertThat(a.getStatus()).isTrue();
        assertThat(a.getMessage()).isEqualTo("ok");

        ResponseDto b = new ResponseDto(false, "err", "payload");
        assertThat(b.getStatus()).isFalse();
        assertThat(b.getMessage()).isEqualTo("err");
        assertThat(b.getData()).isEqualTo("payload");
    }

    @Test
    void noArgs_and_setters_work() {
        ResponseDto r = new ResponseDto();
        r.setStatus(true);
        r.setMessage("msg");
        r.setData(123);

        assertThat(r.getStatus()).isTrue();
        assertThat(r.getMessage()).isEqualTo("msg");
        assertThat(r.getData()).isEqualTo(123);
    }
}

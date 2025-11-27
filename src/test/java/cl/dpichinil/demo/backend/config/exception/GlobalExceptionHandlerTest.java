package cl.dpichinil.demo.backend.config.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/test-exception")
    static class TestController {
        @GetMapping("/custom")
        public void custom() {
            throw new CustomException(HttpStatus.NOT_FOUND, "resource not found");
        }

        @GetMapping("/illegal")
        public void illegal() {
            throw new IllegalArgumentException("bad argument");
        }

        @GetMapping("/runtime")
        public void runtime() {
            throw new RuntimeException("boom");
        }
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenCustomException_expectNotFoundAndResponseDto() throws Exception {
        mockMvc.perform(get("/test-exception/custom"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("resource not found"));
    }

    @Test
    void whenCustomException_expectMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/test-exception/custom"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.instance").value("/test-exception/custom"))
                .andExpect(jsonPath("$.detail").value("Method 'POST' is not supported."))
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.title").value("Method Not Allowed"));
    }

    @Test
    void whenIllegalArgument_expectBadRequestAndResponseDto() throws Exception {
        mockMvc.perform(get("/test-exception/illegal"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("bad argument"));
    }

    @Test
    void whenUnhandledRuntime_expectInternalServerError() throws Exception {
        mockMvc.perform(get("/test-exception/runtime"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }
}

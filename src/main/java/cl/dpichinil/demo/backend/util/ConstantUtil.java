package cl.dpichinil.demo.backend.util;

public class ConstantUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    
    public static final String PATH_LOGIN = "/api/v1/auth/login";
    

    public static final String AUTORIZED_REQUEST_LOGIN = PATH_LOGIN + "**";

    public static final String AUTORIZED_REQUEST_API_DOCS_1 = "/v3/api-docs";
    public static final String AUTORIZED_REQUEST_API_DOCS_2 = "/v3/api-docs/**";
    public static final String AUTORIZED_REQUEST_SWAGGER_1 = "/swagger-ui/**";
    public static final String AUTORIZED_REQUEST_SWAGGER_2 = "/swagger-ui.html**";
}

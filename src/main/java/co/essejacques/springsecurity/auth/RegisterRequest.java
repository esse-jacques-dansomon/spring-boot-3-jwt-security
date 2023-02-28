package co.essejacques.springsecurity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author  : essejacques.co
 * Date    : 28/02/2023 00:52
 * Project : spring-security
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

}

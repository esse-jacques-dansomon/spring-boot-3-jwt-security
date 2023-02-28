package co.essejacques.springsecurity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author  : essejacques.co
 * Date    : 28/02/2023 00:51
 * Project : spring-security
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
    private String token;


}

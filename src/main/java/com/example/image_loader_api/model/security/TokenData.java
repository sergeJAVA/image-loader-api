package com.example.image_loader_api.model.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenData {
    private Long id;
    private String username;
    private String token;
    private List<? extends GrantedAuthority> authorities;
}

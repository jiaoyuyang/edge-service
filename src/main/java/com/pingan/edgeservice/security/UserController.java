package com.pingan.edgeservice.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class UserController {
    @GetMapping("user")
    public Mono<User> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        var user = new User(
                oidcUser.getPreferredUsername(),
                oidcUser.getGivenName(),
                oidcUser.getFamilyName(),
                List.of("employee","customer")
        );
        return Mono.just(user); //edgeService是一个反应式应用，将User对象包装岛反应式发布者中
    }


    /** 不用注入的方式
    @GetMapping("user")
    public Mono<User> getUser(){
        return ReactiveSecurityContextHolder.getContext()  //从ReactiveSecurityContextHolder 中获取当前认证用户的SecurityContext
                .map(SecurityContext::getAuthentication) //从SecurityContext中获取Authentication
                .map(authentication ->
                        (OidcUser)authentication.getPrincipal())//冲Authentication中获取principal
                .map(oidcUser ->
                        new User(
                           oidcUser.getPreferredUsername(),
                           oidcUser.getGivenName(),
                           oidcUser.getFamilyName(),
                           List.of("employee","customer")
                        )
                );
    }
     */
}

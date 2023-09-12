package org.donggle.backend.config;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.auth.JwtTokenProvider;
import org.donggle.backend.auth.presentation.AuthInterceptor;
import org.donggle.backend.auth.presentation.RefreshTokenAuthInterceptor;
import org.donggle.backend.auth.presentation.TokenArgumentResolver;
import org.donggle.backend.ui.common.MDCInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final MDCInterceptor mdcInterceptor;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.LOCATION)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(mdcInterceptor)
                .addPathPatterns("/**")
                .order(1);

        registry.addInterceptor(new AuthInterceptor(jwtTokenProvider))
                .addPathPatterns("/member/**", "/writings/**", "/categories/**", "/trash/**", "/connections/**", "/auth/**")
                .excludePathPatterns("/connections/**/redirect", "/auth/login/**")
                .order(2);

        registry.addInterceptor(new RefreshTokenAuthInterceptor(jwtTokenProvider, tokenRepository))
                .addPathPatterns("/token/refresh")
                .order(3);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenArgumentResolver(jwtTokenProvider));
    }
}


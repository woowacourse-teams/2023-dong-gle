package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.oauth.notion.NotionOAuthService;
import org.donggle.backend.application.service.oauth.tistory.TistoryOAuthService;
import org.donggle.backend.application.service.request.AddTokenRequest;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.auth.support.AuthenticationPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/connections")
public class ConnectionController {
    private final TistoryOAuthService tistoryOAuthService;
    private final NotionOAuthService notionOAuthService;

    @GetMapping("/tistory")
    public ResponseEntity<Void> connectionsRedirectTistory(
            @AuthenticationPrincipal final Long memberId,
            @RequestParam final String redirect_uri
    ) {
        final String redirectUri = tistoryOAuthService.createAuthorizeRedirectUri(memberId, redirect_uri);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUri)
                .build();
    }

    @PostMapping("/tistory")
    public ResponseEntity<Void> connectionsAddTistory(
            @AuthenticationPrincipal final Long memberId,
            @RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest
    ) {
        tistoryOAuthService.saveAccessToken(memberId, oAuthAccessTokenRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notion")
    public ResponseEntity<Void> connectionsRedirectNotion(
            @AuthenticationPrincipal final Long memberId,
            @RequestParam final String redirect_uri
    ) {
        final String redirectUri = notionOAuthService.createRedirectUri(memberId, redirect_uri);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUri)
                .build();
    }

    @PostMapping("/notion")
    public ResponseEntity<Void> connectionsAddNotion(
            @AuthenticationPrincipal final Long memberId,
            @RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest
    ) {
        notionOAuthService.saveAccessToken(memberId, oAuthAccessTokenRequest);
        return ResponseEntity.ok().build();
    }
}

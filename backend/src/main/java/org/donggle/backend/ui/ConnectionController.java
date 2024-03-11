package org.donggle.backend.ui;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.service.request.OAuthAccessTokenRequest;
import org.donggle.backend.application.service.request.TokenAddRequest;
import org.donggle.backend.infrastructure.client.medium.MediumConnectionClient;
import org.donggle.backend.infrastructure.client.notion.NotionConnectionClient;
import org.donggle.backend.ui.common.AuthenticationPrincipal;
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
//    private final TistoryConnectionClient tistoryConnectService;
    private final NotionConnectionClient notionConnectionService;
    private final MediumConnectionClient mediumConnectionClient;

//    @GetMapping("/tistory/redirect")
//    public ResponseEntity<Void> connectionsRedirectTistory(
//            @RequestParam final String redirect_uri
//    ) {
//        final String redirectUri = tistoryConnectService.createAuthorizeRedirectUri(redirect_uri);
//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .header(HttpHeaders.LOCATION, redirectUri)
//                .build();
//    }

//    @PostMapping("/tistory")
//    public ResponseEntity<Void> connectionsAddTistory(
//            @AuthenticationPrincipal final Long memberId,
//            @RequestBody final OAuthAccessTokenRequest oAuthAccessTokenRequest
//    ) {
//        tistoryConnectService.saveAccessToken(memberId, oAuthAccessTokenRequest);
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping("/tistory/disconnect")
//    public ResponseEntity<Void> connectionsDisconnectTistory(
//            @AuthenticationPrincipal final Long memberId
//    ) {
//        tistoryConnectService.deleteAccessToken(memberId);
//        return ResponseEntity.ok().build();
//    }
//
    @GetMapping("/notion/redirect")
    public ResponseEntity<Void> connectionsRedirectNotion(
            @RequestParam final String redirect_uri
    ) {
        final String redirectUri = notionConnectionService.createRedirectUri(redirect_uri);
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
        notionConnectionService.saveAccessToken(memberId, oAuthAccessTokenRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notion/disconnect")
    public ResponseEntity<Void> connectionsDisconnectNotion(
            @AuthenticationPrincipal final Long memberId
    ) {
        notionConnectionService.deleteAccessToken(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/medium")
    public ResponseEntity<Void> connectionAddMedium(
            @AuthenticationPrincipal final Long memberId,
            @RequestBody final TokenAddRequest addTokenRequest
    ) {
        mediumConnectionClient.saveAccessToken(memberId, addTokenRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/medium/disconnect")
    public ResponseEntity<Void> connectionsDisconnectMedium(
            @AuthenticationPrincipal final Long memberId
    ) {
        mediumConnectionClient.deleteAccessToken(memberId);
        return ResponseEntity.ok().build();
    }
}

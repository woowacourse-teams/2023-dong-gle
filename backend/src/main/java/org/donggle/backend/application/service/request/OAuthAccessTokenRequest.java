package org.donggle.backend.application.service.request;

public record OAuthAccessTokenRequest(String redirectUri, String code) {
}

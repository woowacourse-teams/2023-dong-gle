package org.donggle.backend.application.service.request;

public record OAuthAccessTokenRequest(String redirect_uri, String code) {
}

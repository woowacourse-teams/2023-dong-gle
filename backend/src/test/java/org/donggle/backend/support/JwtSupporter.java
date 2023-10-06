package org.donggle.backend.support;

import org.donggle.backend.domain.auth.JwtTokenProvider;

public final class JwtSupporter {

    private static final JwtTokenProvider jwtTokenProvider =
            new JwtTokenProvider("wjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmdwjdgustmd", 600000, 1200000);

    public static String generateToken(final Long id) {
        return jwtTokenProvider.createAccessToken(id);
    }
}
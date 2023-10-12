package org.donggle.backend.application.service.writing;

import org.donggle.backend.application.service.concurrent.ConcurrentAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Repository
public class LockRepository {
    private static final String GET_LOCK = "SELECT GET_LOCK(:memberId , 1)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(:memberId)";
    private static final String EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다.";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LockRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> T executeWithLock(final String memberId, final Supplier<T> supplier) {
        try {
            final int lockNumber = getLock(memberId);
            if (lockNumber == 1) {
                return supplier.get();
            }
            throw new ConcurrentAccessException();
        } finally {
            releaseLock(memberId);
        }
    }

    private int getLock(final String memberId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);

        return jdbcTemplate.queryForObject(GET_LOCK, params, Integer.class);
    }

    private void releaseLock(final String memberId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);

        final Integer result = jdbcTemplate.queryForObject(RELEASE_LOCK, params, Integer.class);

        checkResult(result);
    }

    private void checkResult(final Integer result) {
        if (result != 1) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
}

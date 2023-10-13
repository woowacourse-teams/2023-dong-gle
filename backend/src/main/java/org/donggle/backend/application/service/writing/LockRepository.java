package org.donggle.backend.application.service.writing;

import org.donggle.backend.application.service.concurrent.ConcurrentAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

@Repository
public class LockRepository {
    private static final String GET_LOCK = "SELECT GET_LOCK(?,1)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";
    private static final String EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다.";

    private final DataSource dataSource;

    public LockRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T executeWithLock(final String memberId, final Supplier<T> supplier) {
        try (final Connection connection = dataSource.getConnection()) {
            try {
                getLock(connection, memberId);
                System.out.println("slkdjflasfljasdf");
                return supplier.get();
            } finally {
                releaseLock(connection, memberId);
            }
        } catch (final SQLException | ConcurrentAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void getLock(final Connection connection, final String memberId) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
            preparedStatement.setString(1, memberId);

            checkResult(preparedStatement);
        }
    }

    public void releaseLock(final Connection connection, final String memberId) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
            preparedStatement.setString(1, memberId);

            checkResult(preparedStatement);
        }
    }

    private void checkResult(final PreparedStatement preparedStatement) throws SQLException {
        try (final ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
            final int result = resultSet.getInt(1);
            if (result != 1) {
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
        }
    }
}

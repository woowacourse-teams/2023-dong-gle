module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'jsdom',
  transform: {
    '^.+\\.(js|jsx|ts|tsx)$': ['babel-jest'],
  },
  moduleNameMapper: {
    '^apis/(.*)$': '<rootDir>/src/apis/$1',
    '^assets/(.*)$': '<rootDir>/src/assets/$1',
    '^components/(.*)$': '<rootDir>/src/components/$1',
    '^constants/(.*)$': '<rootDir>/src/constants/$1',
    '^hooks/(.*)$': '<rootDir>/src/hooks/$1',
    '^mocks/(.*)$': '<rootDir>/src/mocks/$1',
    '^pages/(.*)$': '<rootDir>/src/pages/$1',
    '^styles/(.*)$': '<rootDir>/src/styles/$1',
    '^types/(.*)$': '<rootDir>/src/types/$1',
    '^utils/(.*)$': '<rootDir>/src/utils/$1',
  },
};

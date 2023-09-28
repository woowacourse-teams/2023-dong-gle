import ky from 'ky';
import { getHttpErrorFromKyError } from 'utils/apis/HttpError';
import { regenerateAccessToken } from './login';
import { HttpErrorResponseBody } from 'types/apis/error';

export const http = ky.create({
  hooks: {
    beforeError: [
      (error) => {
        const { response, request, options } = error;

        return getHttpErrorFromKyError(response, request, options);
      },
    ],
    beforeRequest: [
      (request) => {
        // 헤더에 accessToken 추가
        request.headers.set('Authorization', `Bearer ${localStorage.getItem('accessToken') ?? ''}`);
      },
    ],
    afterResponse: [
      // accessToken 만료 시 재발급
      async (request, options, response) => {
        if (response.status === 401) {
          const { error }: HttpErrorResponseBody = await response.json();
          if (error.code === 4011) {
            // accessToken 만료
            const newAccessTokenResponse = await regenerateAccessToken();
            const newAccessToken = newAccessTokenResponse.accessToken;
            localStorage.setItem('accessToken', newAccessToken);
            request.headers.set('Authorization', `Bearer ${newAccessToken}`);
            return http(request);
          }
          // refreshToken 만료 -> ErrorBoundary
        }
      },
    ],
  },
});

import { HttpErrorResponseBody } from 'types/apis/error';
import { HTTPError } from 'ky';
import type { NormalizedOptions } from 'ky';

export class HttpError extends HTTPError {
  statusCode: number;
  message: string;

  constructor(
    statusCode: number,
    message: string = 'HttpError가 발생했습니다.',
    response: Response,
    request: Request,
    options: NormalizedOptions,
  ) {
    super(response, request, options);
    this.name = 'HttpError';
    this.statusCode = statusCode;
    this.message = message;
  }
}

export const getHttpErrorFromKyError = async (
  response: Response,
  request: Request,
  options: NormalizedOptions,
) => {
  const statusCode = response.status;
  const responseBody: HttpErrorResponseBody = await response.json();
  const errorMessage = responseBody.error.message ?? '';

  return new HttpError(statusCode, errorMessage, response, request, options);
};

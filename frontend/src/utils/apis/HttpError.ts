import { HttpStatus } from 'constants/apis/http';

export class HttpError extends Error {
  statusCode: number;
  message: string;

  constructor(statusCode: number, message: string = 'HttpError가 발생했습니다.') {
    super(message);
    this.name = 'HttpError';
    this.statusCode = statusCode;
    this.message = message;
  }
}

export const handleHttpError = async (response: Response) => {
  const statusCode = response.status;
  const responseBody: { message: string } = await response.json();
  const errorMessage = responseBody.message;

  if (statusCode >= HttpStatus.INTERNAL_SERVER_ERROR) {
    throw new HttpError(statusCode, errorMessage);
  }
  if (statusCode >= HttpStatus.BAD_REQUEST) {
    throw new HttpError(statusCode, errorMessage);
  }
  if (statusCode >= HttpStatus.MULTIPLE_CHOICES) {
    throw new HttpError(statusCode, errorMessage);
  }
};

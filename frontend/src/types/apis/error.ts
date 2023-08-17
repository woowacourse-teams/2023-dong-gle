// 400,500대 상태코드 일 때 받아오는 Response의 body타입
export type HttpErrorResponseBody = {
  error: { message: string | null; hint: string | null; code: number };
};

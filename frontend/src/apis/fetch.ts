import { HttpError, handleHttpError } from 'utils/apis/HttpError';

type Option = {
  json?: unknown;
} & RequestInit;

const parseOption = (option: Option): RequestInit => {
  return option.json
    ? {
        ...option,
        body: JSON.stringify(option.json),
        headers: {
          ...option.headers,
          'Content-Type': 'application/json',
        },
      }
    : { ...option };
};

const fetchAPI = async (endpoint: RequestInfo | URL, option: Option) => {
  try {
    const response = await fetch(endpoint, parseOption(option));

    if (!response.ok) handleHttpError(response);

    const contentType = response.headers.get('content-type');

    if (!contentType || !contentType.includes('application/json')) return response;

    return response.json();
  } catch (error) {
    // httpError 면 던져서 fetch API 사용하는 쪽에서 핸들링
    if (error instanceof HttpError) throw error;

    alert(error); // httpError가 아니면 여기서 바로 alert
  }
};

export const http = {
  get(endpoint: RequestInfo | URL, option?: Option) {
    return fetchAPI(endpoint, {
      method: 'GET',
      ...option,
    });
  },

  post(endpoint: RequestInfo | URL, option?: Option) {
    return fetchAPI(endpoint, {
      method: 'POST',
      ...option,
    });
  },

  patch(endpoint: RequestInfo | URL, option?: Option) {
    return fetchAPI(endpoint, {
      method: 'PATCH',
      ...option,
    });
  },

  delete(endpoint: RequestInfo | URL, option?: Option) {
    return fetchAPI(endpoint, {
      method: 'DELETE',
      ...option,
    });
  },
};

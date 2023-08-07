import { HttpError, handleHttpError } from 'types/apis/http';

type Option = {
  json?: unknown;
} & RequestInit;

const fetchAPI = async (endpoint: RequestInfo | URL, option: Option) => {
  try {
    const response = await fetch(endpoint, parseOption(option));

    if (!response.ok) {
      handleHttpError(response);
    }

    const contentType = response.headers.get('content-type');

    if (!contentType && response.ok) return response;

    const jsonData = await response.json();
    return jsonData;
  } catch (error) {
    // httpError 면 던져서 fetch API 사용하는 쪽에서 핸들링
    if (error instanceof HttpError) {
      throw error;
    }
    alert(error); // httpError가 아니면 여기서 바로 alert
  }
};

const parseOption = (option: Option): RequestInit => {
  let parsedOption: RequestInit = { ...option };

  if (option.json) {
    parsedOption = {
      ...parsedOption,
      body: JSON.stringify(option.json),
      headers: {
        ...parsedOption.headers,
        'Content-Type': 'application/json',
      },
    };
  }

  return parsedOption;
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

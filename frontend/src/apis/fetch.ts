type Option = {
  json?: unknown;
} & RequestInit;

const getOptions = (option: Option): RequestInit => {
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
    return fetch(
      endpoint,
      getOptions({
        method: 'GET',
        ...option,
      }),
    );
  },

  post(endpoint: RequestInfo | URL, option?: Option) {
    return fetch(
      endpoint,
      getOptions({
        method: 'POST',
        ...option,
      }),
    );
  },

  patch(endpoint: RequestInfo | URL, option?: Option) {
    return fetch(
      endpoint,
      getOptions({
        method: 'PATCH',
        ...option,
      }),
    );
  },

  delete(endpoint: RequestInfo | URL, option?: Option) {
    return fetch(
      endpoint,
      getOptions({
        method: 'DELETE',
        ...option,
      }),
    );
  },
};

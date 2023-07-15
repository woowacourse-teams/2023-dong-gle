const FETCH_DEFAULT_OPTION = {
  headers: {
    'Content-Type': 'application/json',
  },
} as const;

export const http = {
  get(endpoint: RequestInfo | URL, option: RequestInit = FETCH_DEFAULT_OPTION) {
    return fetch(endpoint, {
      method: 'GET',
      ...option,
    });
  },

  post(endpoint: RequestInfo | URL, option: RequestInit = FETCH_DEFAULT_OPTION) {
    return fetch(endpoint, {
      method: 'POST',
      ...option,
    });
  },

  patch(endpoint: RequestInfo | URL, option: RequestInit = FETCH_DEFAULT_OPTION) {
    return fetch(endpoint, {
      method: 'PATCH',
      ...option,
    });
  },

  delete(endpoint: RequestInfo | URL, option: RequestInit = FETCH_DEFAULT_OPTION) {
    return fetch(endpoint, {
      method: 'DELETE',
      ...option,
    });
  },
};

export const http = {
  get(endpoint: RequestInfo | URL, option?: RequestInit) {
    return fetch(endpoint, {
      method: 'GET',
      ...option,
    });
  },

  post(endpoint: RequestInfo | URL, option?: RequestInit) {
    return fetch(endpoint, {
      method: 'POST',
      ...option,
    });
  },

  patch(endpoint: RequestInfo | URL, option?: RequestInit) {
    return fetch(endpoint, {
      method: 'PATCH',
      ...option,
    });
  },

  delete(endpoint: RequestInfo | URL, option?: RequestInit) {
    return fetch(endpoint, {
      method: 'DELETE',
      ...option,
    });
  },
};

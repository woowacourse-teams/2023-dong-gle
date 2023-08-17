export const useAuthToken = () => {
  const authToken = localStorage.getItem('accessToken');

  return { authToken };
};

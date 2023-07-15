import { useCallback, useEffect, useState } from 'react';

type UseGetQueryArgs<ResponseData> = {
  fetcher: () => Promise<Response>;
  onSuccess?: (data: ResponseData) => void;
  onError?: (error?: Error) => void;
  onSettled?: () => void;
};

export const useGetQuery = <ResponseData>({
  fetcher,
  onSuccess,
  onError,
  onSettled,
}: UseGetQueryArgs<ResponseData>) => {
  const [data, setData] = useState<ResponseData | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const getData = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetcher();

      if (!response.ok) {
        const { status, statusText } = response;

        throw new Error(`${status}: ${statusText}`);
      }

      const responseData = await response.json();

      setData(responseData);
      onSuccess?.(responseData);
    } catch (error) {
      if (error instanceof Error) {
        setError(error);
        onError?.(error);
      } else {
        console.error(error);
      }
    } finally {
      onSettled?.();
      setLoading(false);
    }
  }, [fetcher]);

  useEffect(() => {
    getData();
  }, []);

  return {
    data,
    loading,
    error,
    getData,
  };
};

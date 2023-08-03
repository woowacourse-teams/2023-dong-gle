import { useCallback, useEffect, useState } from 'react';

type UseGetQueryArgs<ResponseData> = {
  fetcher: () => Promise<Response>;
  onSuccess?: (data: ResponseData) => void;
  onError?: (error?: Error) => void;
  onSettled?: () => void;
  enabled?: boolean;
};

export const useGetQuery = <ResponseData>({
  fetcher,
  onSuccess,
  onError,
  onSettled,
  enabled = true,
}: UseGetQueryArgs<ResponseData>) => {
  const [data, setData] = useState<ResponseData | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const getData = useCallback(async () => {
    setIsLoading(true);
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
      setIsLoading(false);
    }
  }, [fetcher]);

  useEffect(() => {
    if (!enabled) return;

    getData();
  }, []);

  return {
    data,
    isLoading,
    error,
    getData,
  };
};

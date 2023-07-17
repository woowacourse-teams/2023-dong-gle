import { useState } from 'react';

type useMutateQueryArgs<RequestData, ResponseData> = {
  fetcher: (body: RequestData) => Promise<Response>;
  onSuccess?: (data: { response: ResponseData; headers: Headers }) => void;
  onError?: (error?: Error) => void;
  onSettled?: () => void;
};

const useMutateQuery = <RequestData, ResponseData>({
  fetcher,
  onSuccess,
  onError,
  onSettled,
}: useMutateQueryArgs<RequestData, ResponseData>) => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const mutateQuery = async (body: RequestData) => {
    setIsLoading(true);
    setError(null);

    try {
      const response = await fetcher(body);

      if (!response.ok) {
        const { status, statusText } = response;

        throw new Error(`${status}: ${statusText}`);
      }

      const responseData = await response.text();
      const jsonData = responseData === '' ? {} : JSON.parse(responseData);

      onSuccess?.({ response: jsonData, headers: response.headers });
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
  };

  return { mutateQuery, isLoading, error };
};
export default useMutateQuery;

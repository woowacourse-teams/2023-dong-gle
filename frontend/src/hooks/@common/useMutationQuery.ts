import { useState } from 'react';

interface useMutateQueryArgs<ResponseData> {
  fetcher: () => Promise<Response>;
  onSuccess?: (data: { response: ResponseData; headers: Headers }) => void;
  onError?: (error?: Error) => void;
  onSettled?: () => void;
}

const useMutateQuery = <ResponseData>({
  fetcher,
  onSuccess,
  onError,
  onSettled,
}: useMutateQueryArgs<ResponseData>) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const mutateQuery = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetcher();

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
      setLoading(false);
    }
  };

  return { mutateQuery, loading, error };
};
export default useMutateQuery;

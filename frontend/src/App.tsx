import {
  useQuery,
  useMutation,
  useQueryClient,
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query';
import { ErrorBoundary } from 'components/ErrorBoundary/ErrorBoundary';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import Layout from 'pages/Layout/Layout';
type Props = {};

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      useErrorBoundary: true,
      // suspense: true, // 전체를 감싸니까 깜빡임 현상이 일어남
    },
  },
});

const App = ({}: Props) => {
  return (
    <QueryClientProvider client={queryClient}>
      <ErrorBoundary>
        <Layout />
      </ErrorBoundary>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
};

export default App;

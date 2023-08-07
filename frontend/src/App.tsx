import {
  useQuery,
  useMutation,
  useQueryClient,
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query';
import { ErrorBoundary } from 'components/ErrorBoundary/ErrorBoundary';
import Layout from 'pages/Layout/Layout';
type Props = {};

const App = ({}: Props) => {
  const queryClient = new QueryClient();

  return (
    <QueryClientProvider client={queryClient}>
      <ErrorBoundary>
        <Layout />
      </ErrorBoundary>
    </QueryClientProvider>
  );
};

export default App;

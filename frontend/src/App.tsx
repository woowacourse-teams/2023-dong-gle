import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ErrorBoundary } from 'components/ErrorBoundary/ErrorBoundary';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import ToastProvider from 'contexts/ToastProvider';
import ToastContainer from 'components/@common/Toast/ToastContainer';
import { Outlet } from 'react-router-dom';
import ErrorPage from 'pages/ErrorPage/ErrorPage';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      useErrorBoundary: true,
      // suspense: true, // 전체를 감싸니까 깜빡임 현상이 일어남
    },
  },
});

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <ToastProvider>
        <ErrorBoundary fallback={ErrorPage}>
          <Outlet />
        </ErrorBoundary>
        <ToastContainer />
      </ToastProvider>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
};

export default App;

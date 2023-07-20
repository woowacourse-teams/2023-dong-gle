import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Layout from 'pages/Layout/Layout';
import WritingPage from 'pages/WritingPage/WritingPage';

export const Router = () => {
  const browserRouter = createBrowserRouter([
    {
      path: '',
      element: <Layout />,
      children: [
        {
          path: '/writing/:writingId',
          element: <WritingPage />,
        },
      ],
    },
  ]);

  return <RouterProvider router={browserRouter} />;
};

import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import Layout from 'pages/Layout/Layout';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';

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
        {
          path: '/writings/:categoryId',
          element: <WritingTablePage />,
        },
      ],
    },
  ]);

  return <RouterProvider router={browserRouter} />;
};

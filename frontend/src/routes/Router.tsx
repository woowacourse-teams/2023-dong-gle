import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';
import App from '../App';

export const Router = () => {
  const browserRouter = createBrowserRouter([
    {
      path: '',
      element: <App />,
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

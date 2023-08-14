import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';
import App from '../App';
import OauthPage from 'pages/OauthPage/OauthPage';
import TrashCanPage from 'pages/TrashCanPage/TrashCanPage';

export const Router = () => {
  const browserRouter = createBrowserRouter([
    {
      path: '',
      element: <App />,
      children: [
        {
          path: '/writings/:categoryId/:writingId',
          element: <WritingPage />,
        },
        {
          path: '/writings/:categoryId',
          element: <WritingTablePage />,
        },
        {
          path: '/trash-can',
          element: <TrashCanPage />,
        },
        {
          path: '/oauth/login/*',
          children: [
            {
              path: 'kakao',
              element: <OauthPage />,
            },
          ],
        },
      ],
    },
  ]);

  return <RouterProvider router={browserRouter} />;
};

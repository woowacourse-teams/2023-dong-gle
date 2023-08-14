import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';
import App from '../App';
import OauthPage from 'pages/OauthPage/OauthPage';

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

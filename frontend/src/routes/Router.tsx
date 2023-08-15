import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';
import App from '../App';
import OauthPage from 'pages/OauthPage/OauthPage';
import IntroducePage from 'pages/IntroducePage/IntroducePage';
import Layout from 'pages/Layout/Layout';

export const Router = () => {
  const browserRouter = createBrowserRouter([
    {
      path: '',
      element: <App />,
      children: [
        {
          path: '',
          element: <IntroducePage />,
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
        {
          path: '/space',
          element: <Layout />,
          children: [
            {
              path: 'writings/:categoryId/:writingId',
              element: <WritingPage />,
            },
            {
              path: 'writings/:categoryId',
              element: <WritingTablePage />,
            },
          ],
        },
      ],
    },
  ]);

  return <RouterProvider router={browserRouter} />;
};

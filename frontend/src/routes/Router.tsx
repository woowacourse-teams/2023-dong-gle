import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';
import App from '../App';
import OauthPage from 'pages/OauthPage/OauthPage';
import IntroducePage from 'pages/IntroducePage/IntroducePage';
import Layout from 'pages/Layout/Layout';
import { PATH } from 'constants/path';
import TrashCanPage from 'pages/TrashCanPage/TrashCanPage';
import MyPage from 'pages/MyPage/MyPage';
import ConnectionPage from 'pages/ConnectionPage/ConnectionPage';

export const Router = () => {
  const browserRouter = createBrowserRouter([
    {
      path: PATH.app,
      element: <App />,
      children: [
        {
          path: PATH.introducePage,
          element: <IntroducePage />,
        },
        {
          path: PATH.oauthPage,
          element: <OauthPage />,
        },
        {
          path: PATH.myPage,
          element: <MyPage />,
          children: [
            {
              path: PATH.connections,
              element: <ConnectionPage />,
            },
          ],
        },
        {
          path: PATH.space,
          element: <Layout />,
          children: [
            {
              path: PATH.writingPage,
              element: <WritingPage />,
            },
            {
              path: PATH.writingTablePage,
              element: <WritingTablePage />,
            },
            {
              path: PATH.trashCanPage,
              element: <TrashCanPage />,
            },
          ],
        },
      ],
    },
  ]);

  return <RouterProvider router={browserRouter} />;
};

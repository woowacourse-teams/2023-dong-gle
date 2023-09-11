import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import { PATH } from 'constants/path';
import App from '../App';
import MyPage from 'pages/MyPage/MyPage';
import ConnectionPage from 'pages/ConnectionPage/ConnectionPage';
import WritingPage from 'pages/WritingPage/WritingPage';
import WritingTablePage from 'pages/WritingTablePage/WritingTablePage';
import TrashCanPage from 'pages/TrashCanPage/TrashCanPage';
import PrivateRouter from './PrivateRouter';
import { Suspense, lazy } from 'react';

const IntroducePage = lazy(() => import('pages/IntroducePage/IntroducePage'));
const OauthPage = lazy(() => import('pages/OauthPage/OauthPage'));
const Layout = lazy(() => import('pages/Layout/Layout'));
const ErrorPage = lazy(() => import('pages/ErrorPage/ErrorPage'));

export const Router = () => {
  const browserRouter = createBrowserRouter(
    createRoutesFromElements(
      <Route path={PATH.app} element={<App />}>
        <Route path={PATH.introducePage} element={<IntroducePage />} />

        <Route path={PATH.oauthPage} element={<OauthPage />} />

        <Route element={<PrivateRouter />}>
          <Route path={PATH.myPage} element={<MyPage />} />
          <Route path={PATH.connections} element={<ConnectionPage />} />

          <Route path={PATH.space} element={<Layout />}>
            <Route path={PATH.writingPage} element={<WritingPage />} />
            <Route path={PATH.writingTablePage} element={<WritingTablePage />} />
            <Route path={PATH.trashCanPage} element={<TrashCanPage />} />
          </Route>
        </Route>

        <Route path='*' element={<ErrorPage status={404} title='' message='' />} />
      </Route>,
    ),
  );

  return (
    <Suspense fallback={<div>Loading...</div>}>
      <RouterProvider router={browserRouter} />
    </Suspense>
  );
};

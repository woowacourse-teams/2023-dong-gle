import React from 'react';
import type { Preview } from '@storybook/react';
import { ThemeProvider } from 'styled-components';
import { withRouter } from 'storybook-addon-react-router-v6';
import { initialize, mswLoader } from 'msw-storybook-addon';
import { handlers } from '../src/mocks/handlers';

import GlobalStyle from '../src/styles/GlobalStyle';
import { theme } from '../src/styles/theme';
import ToastProvider from '../src/contexts/ToastProvider';
import ToastContainer from '../src/components/@common/Toast/ToastContainer';

// msw init
initialize();

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
    msw: {
      handlers,
    },
  },
  loaders: [mswLoader],
};

export default preview;

export const decorators = [
  (Story) => (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <ToastProvider>
        <Story />
        <ToastContainer />
      </ToastProvider>
    </ThemeProvider>
  ),
  withRouter,
];

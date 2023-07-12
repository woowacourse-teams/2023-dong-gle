import React from 'react';
import type { Preview } from '@storybook/react';
import { ThemeProvider } from 'styled-components';

import GlobalStyle from '../src/styles/GlobalStyle';
import { theme } from '../src/styles/theme';

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
  },
};

export default preview;

export const decorators = [
  (Story) => (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Story />
    </ThemeProvider>
  ),
];

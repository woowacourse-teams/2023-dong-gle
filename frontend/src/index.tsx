import React from 'react';
import { createRoot } from 'react-dom/client';

import GlobalStyle from './styles/GlobalStyle';
import App from './App';
import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';

const root = createRoot(document.getElementById('app') as HTMLElement);
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <App />
    </ThemeProvider>
  </React.StrictMode>,
);

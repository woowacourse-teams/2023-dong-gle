import { render } from '@testing-library/react';
import { ThemeProvider } from 'styled-components';
import { HomeIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { theme } from 'styles/theme';

describe('render 테스트', () => {
  it('matches snapshot', () => {
    render(
      <ThemeProvider theme={theme}>
        <Button icon={<HomeIcon />} />
      </ThemeProvider>,
    );
  });
});

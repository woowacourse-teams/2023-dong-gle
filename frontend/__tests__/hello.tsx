import { render } from '@testing-library/react';
import { CalendarIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';

describe('render 테스트', () => {
  it('matches snapshot', () => {
    render(<Button icon={<CalendarIcon />} />);
  });
});

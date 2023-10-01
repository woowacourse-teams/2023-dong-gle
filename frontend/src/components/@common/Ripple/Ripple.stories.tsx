import { Meta, StoryObj } from '@storybook/react';
import Ripple from './Ripple';

/**
 * 아무곳이나 클릭해보세요.
 *
 * 물결이 퍼지는 `Ripple` 효과를 볼 수 있어요.
 */
const meta = {
  title: 'common/Ripple',
  component: Ripple,
  args: {
    color: 'gray',
  },
  argTypes: {},
} satisfies Meta<typeof Ripple>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

/**
 * 버튼에 Ripple 을 적용한 예시입니다.
 */
export const Button: Story = {
  render: ({}) => {
    return (
      <button
        style={{
          width: '100px',
          height: '50px',
          color: 'white',
          backgroundColor: '#0064ff',
          position: 'relative',
          overflow: 'hidden',
        }}
      >
        버튼
        <Ripple color='black' />
      </button>
    );
  },
};

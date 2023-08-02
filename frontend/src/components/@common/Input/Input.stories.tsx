import type { Meta, StoryObj } from '@storybook/react';
import Input, { InputVariant } from './Input';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';
import { Size } from 'constants/components/common';

const meta: Meta<typeof Input> = {
  title: 'common/Input',
  component: Input,
  args: {
    variant: 'outline',
    size: 'medium',
    labelText: '라벨',
    supportingText: '안내 문구는 여기에 나타납니다',
    isError: false,
    required: true,
    placeholder: 'placeholder',
  },
  argTypes: {
    variant: {
      description: '미리 정의해놓은 인풋의 스타일입니다.',
      options: Object.values(InputVariant),
      control: { type: 'radio' },
    },
    size: {
      description: '크기에 따라 padding과 font-size가 바뀝니다.',
      options: Object.values(Size),
      control: { type: 'radio' },
    },
    labelText: {
      description: '라벨 텍스트입니다.',
      control: { type: 'text' },
    },
    supportingText: {
      description: '인풋 아래에 나타나는 안내 문구 텍스트입니다.',
      control: { type: 'text' },
    },
    placeholder: {
      description: '인풋 안에 나타나는 placeholder 텍스트입니다.',
      control: { type: 'text' },
    },
    isError: {
      description: 'Error 상태를 나타냅니다.',
      control: { type: 'boolean' },
    },
    required: {
      description: 'input의 필수 입력 여부를 나타냅니다.',
      control: { type: 'boolean' },
    },
  },
};

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

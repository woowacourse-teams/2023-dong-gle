import { Meta, StoryObj } from '@storybook/react';

import Carousel from './Carousel';

const meta = {
  title: 'common/Carousel',
  component: Carousel,
} satisfies Meta<typeof Carousel>;

export default meta;
type Story = StoryObj<typeof meta>;

type Media = {
  type:
    | 'image/jpeg'
    | 'image/png'
    | 'image/webp'
    | 'image/avif'
    | 'image/gif'
    | 'video/mp4'
    | 'video/webm';

  src: string;
};

const pngItems = [
  { type: 'image/png', src: '../../../assets/icons/donggle-example.png' },
  { type: 'image/png', src: '../../../assets/icons/donggle-example.png' },
  { type: 'image/png', src: '../../../assets/icons/donggle-example.png' },
  { type: 'image/png', src: '../../../assets/icons/donggle-example.png' },
  { type: 'image/png', src: '../../../assets/icons/donggle-example.png' },
] as Media[];

export const Primary: Story = {
  args: {
    items: pngItems,
  },
  argTypes: {
    items: {
      description: '캐러셀 내부에 보여질 미디어 컨텐츠 배열입니다.',
    },
  },
};

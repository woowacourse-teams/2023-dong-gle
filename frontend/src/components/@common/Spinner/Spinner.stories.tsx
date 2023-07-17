/* eslint-disable react/jsx-key */
import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';
import { theme } from 'styles/theme';
import Spinner from './Spinner';

const meta: Meta<typeof Spinner> = {
  title: 'common/Spinner',
  component: Spinner,
  args: {
    size: 30,
    thickness: 4,
    duration: 1,
    backgroundColor: theme.color.gray4,
    barColor: theme.color.primary,
  },
  argTypes: {
    size: {
      description: 'size에 따라 spinner 크기가 바뀝니다.',
    },
    thickness: {
      description: 'spinner의 두께에 해당합니다.',
    },
    duration: {
      description: 'spinner가 한 바퀴 도는데 걸리는 시간(second)입니다.',
    },
    backgroundColor: {
      description: 'spinner의 배경 색상입니다.',
    },
    barColor: {
      description: 'spinner의 돌아가는 bar의 색상입니다.',
    },
  },
};

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Sizes: Story = {
  render: () => {
    const size = [20, 30, 40];

    return (
      <StoryContainer>
        {Object.values(size).map((size) => (
          <StoryItemContainer>
            <StoryItemTitle>{size}</StoryItemTitle>
            <Spinner size={size} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const Thickness: Story = {
  render: () => {
    const thickness = [2, 4, 6];

    return (
      <StoryContainer>
        {Object.values(thickness).map((thickness) => (
          <StoryItemContainer>
            <StoryItemTitle>{thickness}</StoryItemTitle>
            <Spinner thickness={thickness} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const Duration: Story = {
  render: () => {
    const duration = [0.8, 1, 1.2];

    return (
      <StoryContainer>
        {Object.values(duration).map((duration) => (
          <StoryItemContainer>
            <StoryItemTitle>{duration}</StoryItemTitle>
            <Spinner duration={duration} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const BackgroundColor: Story = {
  render: () => {
    const backgroundColor = [theme.color.gray1, theme.color.gray3, theme.color.gray5];

    return (
      <StoryContainer>
        {Object.values(backgroundColor).map((backgroundColor) => (
          <StoryItemContainer>
            <StoryItemTitle>{backgroundColor}</StoryItemTitle>
            <Spinner backgroundColor={backgroundColor} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const BarColor: Story = {
  render: () => {
    const barColor = [theme.color.gray1, theme.color.gray6, theme.color.primary];

    return (
      <StoryContainer>
        {Object.values(barColor).map((barColor) => (
          <StoryItemContainer>
            <StoryItemTitle>{barColor}</StoryItemTitle>
            <Spinner barColor={barColor} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

import { Meta, StoryObj } from '@storybook/react';

import Accordion from './Accordion';
import { Size, TextAlign, Variant } from 'constants/components/common';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';

const meta = {
  title: 'common/Accordion',
  args: {
    variant: 'primary',
    size: 'medium',
    textAlign: 'start',
  },
  argTypes: {
    variant: {
      description: '정의된 스타일입니다.',
      options: Object.values(Variant),
      control: { type: 'radio' },
    },
    size: {
      description: '3가지 사이즈 입니다.',
      options: Object.values(Size),
      control: { type: 'radio' },
    },
    textAlign: {
      description: '글자 정렬 방식입니다. 제목은 정렬하지 않습니다.',
      options: Object.values(TextAlign),
      control: { type: 'radio' },
    },
  },
  component: Accordion,
} satisfies Meta<typeof Accordion>;

export default meta;
type Story = StoryObj<typeof meta>;

const AccordionValues = [
  { title: '제목1', panel: '내용' },
  { title: '제목2', panel: '내용' },
  { title: '제목3', panel: '내용' },
  { title: '제목4', panel: '내용' },
];

export const Playground: Story = {};

export const Variants: Story = {
  render: () => {
    return (
      <StoryContainer>
        {Object.values(Variant).map((variant) => (
          <StoryItemContainer key={variant}>
            <StoryItemTitle>{variant}</StoryItemTitle>
            <Accordion>
              {AccordionValues.map((value) => {
                return (
                  <Accordion.Item key={value.title}>
                    <Accordion.Title>{value.title}</Accordion.Title>
                    <Accordion.Panel>{value.panel}</Accordion.Panel>
                  </Accordion.Item>
                );
              })}
            </Accordion>
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const Sizes: Story = {
  render: () => {
    return (
      <StoryContainer>
        {Object.values(Size).map((size) => (
          <StoryItemContainer key={size}>
            <StoryItemTitle>{size}</StoryItemTitle>
            <Accordion>
              <Accordion.Item>
                {AccordionValues.map((value) => {
                  return (
                    <Accordion.Item key={value.title}>
                      <Accordion.Title>{value.title}</Accordion.Title>
                      <Accordion.Panel>{value.panel}</Accordion.Panel>
                    </Accordion.Item>
                  );
                })}
              </Accordion.Item>
            </Accordion>
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const TextAligns: Story = {
  render: () => {
    return (
      <StoryContainer>
        {Object.values(TextAlign).map((textAlign) => (
          <StoryItemContainer key={textAlign}>
            <StoryItemTitle>{textAlign}</StoryItemTitle>
            <Accordion>
              {AccordionValues.map((value) => {
                return (
                  <Accordion.Item key={value.title}>
                    <Accordion.Title>{value.title}</Accordion.Title>
                    <Accordion.Panel>{value.panel}</Accordion.Panel>
                  </Accordion.Item>
                );
              })}
            </Accordion>
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

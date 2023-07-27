import type { Meta, StoryObj } from '@storybook/react';
import Category from './Category';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';

const meta: Meta<typeof Category> = {
  title: 'Category',
  component: Category,
  args: {
    categoryName: '프로젝트 기록',
    numberOfWritings: 3,
  },
  argTypes: {
    categoryName: {
      description: '카테고리의 제목입니다.',
      control: { type: 'text' },
    },
    numberOfWritings: {
      description: '카테고리에 있는 글의 개수입니다.',
      control: { type: 'number' },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const LongCategoryName: Story = {
  render: () => {
    const names = [
      '가',
      '가나',
      '가나다',
      '가나다라',
      '가나다라마',
      '가나다라마바사아자차카타파하',
      '가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하',
    ];

    return (
      <StoryContainer>
        {names.map((name) => (
          <StoryItemContainer>
            <Category
              categoryName={name}
              numberOfWritings={0}
              renameCategory={() => {}}
              deleteCategory={() => {}}
            />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const LongNumberOfWritings: Story = {
  render: () => {
    const numbers = [1, 100, 10000, 1000000, 100000000, 10000000000, 100000000000000000000];

    return (
      <StoryContainer>
        {numbers.map((number) => (
          <StoryItemContainer>
            <Category
              categoryName='가나다라마바사아자차'
              numberOfWritings={number}
              renameCategory={() => {}}
              deleteCategory={() => {}}
            />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

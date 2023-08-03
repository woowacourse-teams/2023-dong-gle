import type { Meta, StoryObj } from '@storybook/react';
import Category from './Category';
import { StoryContainer, StoryItemContainer } from 'styles/storybook';

const meta: Meta<typeof Category> = {
  title: 'Category',
  component: Category,
  args: {
    categoryName: '프로젝트 기록',
  },
  argTypes: {
    categoryName: {
      description: '카테고리의 제목입니다.',
      control: { type: 'text' },
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
              id={1}
              categoryName={name}
              getCategories={() => Promise.resolve()}
              isDefaultCategory={false}
            />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

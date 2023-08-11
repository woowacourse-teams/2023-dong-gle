import Accordion from 'components/@common/Accordion/Accordion';
import { useState } from 'react';
import Category from '../Category/Category';
import WritingList from '../WritingList/WritingList';

type Props = {
  categoryId: number;
  categoryName: string;
  isDefaultCategory: boolean;
};

const Item = ({ categoryId, categoryName, isDefaultCategory }: Props) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <Accordion.Item key={categoryId}>
      <Accordion.Title
        onIconClick={() => setIsOpen((prev) => !prev)}
        aria-label={`${categoryName} 카테고리 왼쪽 사이드바에서 열기`}
      >
        <Category
          categoryId={categoryId}
          categoryName={categoryName}
          isDefaultCategory={isDefaultCategory}
        />
      </Accordion.Title>
      <Accordion.Panel>
        <WritingList categoryId={categoryId} isOpen={isOpen} />
      </Accordion.Panel>
    </Accordion.Item>
  );
};

export default Item;

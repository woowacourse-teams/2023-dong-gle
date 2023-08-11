import Accordion from 'components/@common/Accordion/Accordion';
import { useCategories } from './useCategories';
import Item from '../Item/Item';

const List = () => {
  const { categories } = useCategories();

  return (
    <>
      {categories ? (
        <Accordion>
          {categories.map((category, index) => {
            return (
              <Item
                key={category.id}
                categoryId={category.id}
                categoryName={category.categoryName}
                isDefaultCategory={Boolean(index === 0)}
              />
            );
          })}
        </Accordion>
      ) : null}
    </>
  );
};

export default List;

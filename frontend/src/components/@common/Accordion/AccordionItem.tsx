import {
  Children,
  ComponentPropsWithoutRef,
  ReactElement,
  cloneElement,
  isValidElement,
  useState,
} from 'react';
import { styled } from 'styled-components';
import AccordionTitle from './AccordionTitle';
import AccordionPanel from './AccordionPanel';

const AccordionItem = ({ children }: ComponentPropsWithoutRef<'li'>) => {
  const [isOpen, isSetOpen] = useState(false);

  const togglePanel = () => {
    isSetOpen(!isOpen);
  };

  return (
    <S.Item>
      {Children.map(children, (child) => {
        if (!child || !isValidElement(child)) return null;

        if (child.type === AccordionTitle) {
          return cloneElement(child as ReactElement, {
            isOpen,
            onToggleIconClick: togglePanel,
          });
        }

        if (child.type === AccordionPanel) {
          return cloneElement(child as ReactElement, { isOpen });
        }
      })}
    </S.Item>
  );
};

export default AccordionItem;

const S = {
  Item: styled.li`
    width: 100%;

    border-radius: 4px;
  `,
};

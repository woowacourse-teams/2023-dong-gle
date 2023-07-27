import { ComponentPropsWithoutRef, ReactNode, useState } from 'react';
import { styled } from 'styled-components';
import AccordionPanel from './AccordionPanel';
import AccordionTitle from './AccordionTitle';

type Props = {
  accordionTitle: ReactNode;
  panel: ReactNode;
  onTitleClick?: () => void;
  onPanelClick?: () => void;
} & ComponentPropsWithoutRef<'li'>;

const AccordionItem = ({ accordionTitle, panel, onTitleClick, onPanelClick }: Props) => {
  const [isOpen, isSetOpen] = useState(false);

  const handlePanelClick = () => {
    isSetOpen(!isOpen);

    if (onTitleClick) onTitleClick();
  };

  return (
    <S.Item>
      <AccordionTitle accordionTitle={accordionTitle} isOpen={isOpen} onClick={handlePanelClick} />
      <AccordionPanel panel={panel} isOpen={isOpen} onPanelClick={onPanelClick} />
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

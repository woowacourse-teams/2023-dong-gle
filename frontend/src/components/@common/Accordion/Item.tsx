import { ComponentPropsWithoutRef, ReactNode, useState } from 'react';
import { styled } from 'styled-components';
import Body from './Body';
import HeaderButton from './HeaderButton';

type Props = {
  header: ReactNode;
  body: ReactNode;
} & ComponentPropsWithoutRef<'li'>;

const Item = ({ header, body }: Props) => {
  const [isOpen, isSetOpen] = useState(false);

  const toggleBody = () => {
    isSetOpen(!isOpen);
  };

  return (
    <S.Item>
      <HeaderButton header={header} isOpen={isOpen} onClick={toggleBody} />
      <Body body={body} isOpen={isOpen} />
    </S.Item>
  );
};

export default Item;

const S = {
  Item: styled.li`
    width: 100%;

    border-radius: 4px;
  `,
};

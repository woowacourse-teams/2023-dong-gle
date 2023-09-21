import { Dispatch } from 'react';
import styled from 'styled-components';

type Props = {
  length: number;
  activeIndex: number;
  setActiveIndex: Dispatch<React.SetStateAction<number>>;
};

const Indicator = ({ length, activeIndex, setActiveIndex }: Props) => {
  const changeActiveIndexOnClick = (index: number) => setActiveIndex(index);

  return (
    <S.Indicator>
      {Array.from({ length }, (_, index) => (
        <li key={index}>
          <S.ControlButton onClick={() => changeActiveIndexOnClick(index)}>
            <S.Bar $isActive={index === activeIndex} />
          </S.ControlButton>
        </li>
      ))}
    </S.Indicator>
  );
};

export default Indicator;

const S = {
  Indicator: styled.ul`
    display: flex;
    gap: 8px;
    position: absolute;
    left: 50%;
    transform: translate(-50%, 0%);
    bottom: 16px;
  `,

  ControlButton: styled.button`
    width: 60px;
    height: 20px;
  `,

  Bar: styled.div<{ $isActive: boolean }>`
    width: 100%;
    height: 4px;
    border-radius: 2px;
    background-color: ${({ $isActive, theme }) =>
      $isActive ? theme.color.gray8 : theme.color.gray6};
  `,
};

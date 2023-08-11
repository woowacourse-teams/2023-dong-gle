import { styled } from 'styled-components';
import Header from '../Header/Header';
import List from '../List/List';

const Section = () => {
  return (
    <S.Section>
      <Header />
      <List />
    </S.Section>
  );
};

export default Section;

const S = {
  Section: styled.section`
    width: 26rem;
    overflow: auto;
  `,
};

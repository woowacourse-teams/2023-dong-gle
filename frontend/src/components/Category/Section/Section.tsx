import { styled } from 'styled-components';
import Header from '../Header/Header';
import List from '../List/List';
import { throttle } from 'utils/functionRegulator';
import { useScroll } from 'hooks/@common/useScroll';

const Section = () => {
  const { scrollRef, scrollToBottom, scrollInArea } = useScroll();

  return (
    <S.Section ref={scrollRef} onDrag={throttle(scrollInArea(100, 100), 100)}>
      <Header onCategoryAdded={scrollToBottom} />
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

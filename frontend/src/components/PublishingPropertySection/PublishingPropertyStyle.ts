import { css, styled } from 'styled-components';
import { slideToLeft } from 'styles/animation';
import { Blog } from 'types/domain';

const PublishingPropertyStyle = {
  PublishingPropertySection: styled.section<{ $blog: Blog }>`
    display: flex;
    flex-direction: column;
    gap: 2rem;
    animation: ${slideToLeft} 0.5s;

    ${({ theme, $blog }) => css`
      & > button {
        outline-color: ${theme.color[$blog.toLowerCase()]};
        background-color: ${theme.color[$blog.toLowerCase()]};

        &:hover {
          background-color: ${theme.color[$blog.toLowerCase()]};
        }
      }
    `};
  `,
  SectionHeader: styled.h1`
    display: flex;
    gap: 1.5rem;
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.5rem;
  `,
  Properties: styled.div`
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    padding: 0 0 1rem 0.9rem;
  `,
  PropertyRow: styled.div`
    display: flex;
    align-items: center;

    select,
    input {
      padding: 1px;
    }
  `,
  PropertyName: styled.div`
    display: flex;
    align-items: center;
    flex-shrink: 0;
    width: 9.5rem;
    height: 2.3rem;
    color: ${({ theme }) => theme.color.gray8};
    font-size: 1.3rem;
    font-weight: 600;
  `,
  LoadingWrapper: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 4rem;
    font-size: 1.3rem;
  `,
};

export default PublishingPropertyStyle;

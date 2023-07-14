import { styled } from 'styled-components';

export const StoryContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 300px;
  gap: 28px;
`;

export const StoryItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

export const StoryItemContainerRow = styled.div`
  display: flex;
  flex-direction: row;
  gap: 12px;
`;

export const StoryItemTitle = styled.h3`
  color: ${({ theme }) => theme.color.gray9};
  font-size: 12px;
  font-weight: 400;
  text-transform: uppercase;
`;

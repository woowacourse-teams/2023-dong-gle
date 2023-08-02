import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';
import { CloseIcon } from 'assets/icons';

type Props = {
  removable?: boolean;
} & ComponentPropsWithoutRef<'button'>;

const Tag = ({ removable = true, children, ...rest }: Props) => {
  return (
    <S.Tag {...rest}>
      #{children}
      {removable && <CloseIcon width={14} height={14} />}
    </S.Tag>
  );
};

export default Tag;

const S = {
  Tag: styled.button`
    display: inline-flex;
    align-items: center;
    padding: 0.6rem;
    background-color: ${({ theme }) => theme.color.gray4};
    border-radius: 8px;
    color: ${({ theme }) => theme.color.gray8};
    font-size: 1.2rem;
    font-weight: 600;
  `,
};

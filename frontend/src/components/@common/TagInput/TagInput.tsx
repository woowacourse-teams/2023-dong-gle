import { styled } from 'styled-components';
import { useTagInput } from './useTagInput';
import Tag from 'components/@common/Tag/Tag';
import { useEffect } from 'react';

type Props = {
  onChangeTags: (tags: string[]) => void;
};

const TagInput = ({ onChangeTags }: Props) => {
  const { inputValue, tags, addTag, removeLastTag, removeTag, onInputValueChange } = useTagInput();

  useEffect(() => {
    onChangeTags(tags);
  }, [tags]);

  const TagsList = () => {
    return tags.map((tag) => (
      <Tag key={tag} onClick={removeTag(tag)}>
        {tag}
      </Tag>
    ));
  };

  return (
    <S.TagInputContainer>
      <TagsList />
      <S.Input
        value={inputValue}
        placeholder='태그 추가'
        onKeyUp={addTag}
        onKeyDown={removeLastTag}
        onChange={onInputValueChange}
      />
    </S.TagInputContainer>
  );
};

export default TagInput;

const S = {
  TagInputContainer: styled.div`
    display: flex;
    flex-wrap: wrap;
    gap: 0.2rem;
  `,
  Input: styled.input`
    border: none;
    padding: 0.4rem;
    outline-color: ${({ theme }) => theme.color.gray1};
  `,
};

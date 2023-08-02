import { styled } from 'styled-components';
import { useTagInput } from './useTagInput';
import Tag from 'components/@common/Tag/Tag';

const TagInput = () => {
  const { inputValue, tags, addTag, removeLastTag, removeTag, onInputValueChange } = useTagInput();

  const TagsList = () => {
    return tags.map((tag) => <Tag onClick={removeTag(tag)}>{tag}</Tag>);
  };

  return (
    <S.TagInputContainer>
      <TagsList />
      <S.Input
        value={inputValue}
        placeholder='Add tag'
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

import Button from 'components/@common/Button/Button';
import FileUploader from 'components/@common/FileUploader/FileUploader';
import Modal from 'components/@common/Modal/Modal';
import Spinner from 'components/@common/Spinner/Spinner';
import { styled } from 'styled-components';
import { useFileUploadModal } from './useFileUploadModal';

type Props = {
  isOpen: boolean;
  closeModal: () => void;
};

const FileUploadModal = ({ isOpen, closeModal }: Props) => {
  const { isLoading, inputValue, uploadOnServer, setNotionPageLink, uploadNotionWriting } =
    useFileUploadModal({ closeModal });

  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <S.Container>
        <S.Title>글 가져오기</S.Title>
        {isLoading ? (
          <>
            글 가져오는 중...
            <Spinner />
          </>
        ) : (
          <S.Content>
            <S.Item>
              <S.ItemTitle>내 컴퓨터에서 가져오기</S.ItemTitle>
              <FileUploader accept='.md' height='15rem' onFileSelect={uploadOnServer} />
            </S.Item>
            <S.Item>
              <S.ItemTitle>노션에서 가져오기</S.ItemTitle>
              <S.InputContainer>
                <S.Input id='notion-link' value={inputValue} onChange={setNotionPageLink} />
                <S.Label htmlFor='notion-link'>페이지 링크</S.Label>
                <S.Underline />
              </S.InputContainer>
            </S.Item>
            <Button block={true} variant='secondary' onClick={uploadNotionWriting}>
              가져오기
            </Button>
          </S.Content>
        )}
      </S.Container>
    </Modal>
  );
};

export default FileUploadModal;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
    width: 50vw;
    max-width: 40rem;
  `,
  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;
  `,
  Content: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 7rem;
    height: 100%;
    margin: 2rem 0;
    font-size: 1.3rem;
  `,
  Item: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1.6rem;
    width: 100%;
  `,
  ItemTitle: styled.h2`
    font-size: 1.3rem;
    font-weight: 500;
  `,
  InputContainer: styled.div`
    position: relative;
    margin-top: 1rem;
  `,
  Input: styled.input`
    font-size: 1.3rem;
    width: 100%;
    border: none;
    border-bottom: 2px solid ${({ theme }) => theme.color.gray6};
    padding: 0.5rem;
    background-color: transparent;
    outline-color: ${({ theme }) => theme.color.gray1};

    &:focus ~ label {
      top: -1.3rem;
      font-size: 1.2rem;
      color: ${({ theme }) => theme.color.gray8};
    }

    &:focus ~ div {
      transform: scaleX(1);
    }
  `,
  Label: styled.label`
    position: absolute;
    top: 25%;
    left: 0.5rem;
    color: ${({ theme }) => theme.color.gray6};
    transition: all 0.3s ease;
    font-size: 1.2rem;
    pointer-events: none;
  `,
  Underline: styled.div`
    position: absolute;
    bottom: 0;
    left: 0;
    height: 2px;
    width: 100%;
    background-color: ${({ theme }) => theme.color.primary};
    transform: scaleX(0);
    transition: all 0.3s ease;
  `,
};

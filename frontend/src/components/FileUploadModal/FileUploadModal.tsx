import Button from 'components/@common/Button/Button';
import FileUploader from 'components/@common/FileUploader/FileUploader';
import Modal from 'components/@common/Modal/Modal';
import Spinner from 'components/@common/Spinner/Spinner';
import { styled } from 'styled-components';
import { useFileUploadModal } from './useFileUploadModal';
import Input from 'components/@common/Input/Input';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useMember } from 'hooks/queries/useMember';
import { useGlobalStateValue } from '@yogjin/react-global-state';
import { activeCategoryIdState } from 'globalState';

type Props = {
  isOpen: boolean;
  closeModal: () => void;
};

const FileUploadModal = ({ isOpen, closeModal }: Props) => {
  const activeCategoryId = useGlobalStateValue(activeCategoryIdState);
  const { isLoading, inputValue, uploadOnServer, setNotionPageLink, uploadNotionWriting } =
    useFileUploadModal({
      closeModal,
      categoryId: activeCategoryId,
    });
  const { goMyPage } = usePageNavigate();
  const { notion } = useMember();

  return (
    <Modal
      isOpen={isOpen}
      canBackdropClose={!isLoading}
      canEscKeyClose={!isLoading}
      hasCloseButton={!isLoading}
      closeModal={closeModal}
    >
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
            {notion && notion.isConnected ? (
              <>
                <S.Item>
                  <S.ItemTitle>노션에서 가져오기</S.ItemTitle>
                  <Input
                    labelText='페이지 링크'
                    variant='filled'
                    supportingText='페이지 공유 - 링크 복사 후 붙여넣기'
                    placeholder='https://www.notion.so/..'
                    value={inputValue}
                    onChange={setNotionPageLink}
                    onKeyUp={(e) => {
                      if (e.key === 'Enter') uploadNotionWriting();
                    }}
                  />
                </S.Item>
                <Button block={true} variant='secondary' onClick={uploadNotionWriting}>
                  가져오기
                </Button>
              </>
            ) : (
              <Button block={true} variant='secondary' onClick={goMyPage}>
                노션 연동하기
              </Button>
            )}
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
    gap: 1.5rem;
    width: 100%;
  `,
  ItemTitle: styled.h2`
    font-size: 1.4rem;
    font-weight: 500;
  `,
};

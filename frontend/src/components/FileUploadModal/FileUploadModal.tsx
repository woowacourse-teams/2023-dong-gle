import Button from 'components/@common/Button/Button';
import FileUploader from 'components/@common/FileUploader/FileUploader';
import Modal from 'components/@common/Modal/Modal';
import Spinner from 'components/@common/Spinner/Spinner';
import { css, styled } from 'styled-components';
import { useFileUploadModal } from './useFileUploadModal';
import Input from 'components/@common/Input/Input';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useMember } from 'hooks/queries/useMember';
import { useGlobalStateValue } from '@yogjin/react-global-state';
import { activeCategoryIdState } from 'globalState';
import { KeyboardEventHandler } from 'react';
import { MAX_WIDTH } from 'constants/style';

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

  const uploadNotionWritingWithEnter: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key === 'Enter') uploadNotionWriting();
  };

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
              <FileUploader
                accept='.md'
                width='100%'
                height='15rem'
                onFileSelect={uploadOnServer}
              />
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
                    onKeyUp={uploadNotionWritingWithEnter}
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

const generateResponsiveStyle = {
  container: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.tablet}) {
        width: 40vw;
      }

      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        width: 60vw;
      }

      @media (max-width: ${MAX_WIDTH.mobileMedium}) {
        width: 80vw;
      }
    `;
  },

  content: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        padding: 0 2rem;
      }
    `;
  },
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    width: 30vw;

    ${() => generateResponsiveStyle.container()}
  `,
  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;
  `,
  Content: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8rem;
    width: 100%;
    height: 100%;
    margin: 2rem 0;
    padding: 0 4rem;
    font-size: 1.2rem;

    ${() => generateResponsiveStyle.content()}
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

import Modal from 'components/@common/Modal/Modal';
import { MAX_WIDTH } from 'constants/style';
import { useMemberDelete } from 'hooks/queries/member/useMemberDelete';
import { css, styled } from 'styled-components';

type Props = {
  isOpen: boolean;
  closeModal: () => void;
};

const DeleteAccountModal = ({ isOpen, closeModal }: Props) => {
  const deleteMember = useMemberDelete();

  const deleteAccount = () => {
    deleteMember.mutate();
  };

  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <S.Container>
        <S.Title>회원 탈퇴</S.Title>
        <S.Content>
          <p>탈퇴 시, 회원 정보가 모두 삭제되고 재가입 시 복원되지 않습니다.</p>
          <p>정말 탈퇴하시겠습니까?</p>
        </S.Content>
        <S.ButtonContainer>
          <S.DeleteAccountButton onClick={deleteAccount}>탈퇴</S.DeleteAccountButton>
          <S.CancelButton onClick={closeModal}>취소</S.CancelButton>
        </S.ButtonContainer>
      </S.Container>
    </Modal>
  );
};

export default DeleteAccountModal;

const generateResponsiveStyle = {
  button: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        padding: 0.8rem 3.2rem;
      }

      @media (max-width: ${MAX_WIDTH.mobileMedium}) {
        padding: 0.6rem 1.6rem;
      }

      @media (max-width: ${MAX_WIDTH.mobileSmall}) {
        padding: 0.4rem 0.8rem;
      }
    `;
  },
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
    width: 50vw;
    max-width: 40rem;
    padding: 0 2rem;
  `,
  Title: styled.h1`
    font-size: 2rem;
    font-weight: 700;
  `,
  Content: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    width: 100%;
    height: 100%;
    font-size: 1.2rem;
    word-break: keep-all;
  `,
  ButtonContainer: styled.div`
    display: flex;
    justify-content: space-between;
    width: 100%;

    button {
      padding: 1rem 6rem;
      border-radius: 8px;

      ${() => generateResponsiveStyle.button()}
    }
  `,
  DeleteAccountButton: styled.button`
    border: 2px solid ${({ theme }) => theme.color.red5};
    background-color: ${({ theme }) => theme.color.red5};
    color: ${({ theme }) => theme.color.gray1};

    &:hover {
      border: 2px solid ${({ theme }) => theme.color.red6};
      background-color: ${({ theme }) => theme.color.red6};
    }
  `,
  CancelButton: styled.button`
    border: 2px solid ${({ theme }) => theme.color.gray4};
    background-color: ${({ theme }) => theme.color.gray4};

    &:hover {
      border: 2px solid ${({ theme }) => theme.color.gray5};
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
};

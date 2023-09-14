import Modal from 'components/@common/Modal/Modal';
import { useMemberDelete } from 'hooks/queries/member/useMemberDelete';
import { styled } from 'styled-components';

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
    justify-content: center;
    align-items: center;
    gap: 1rem;
    height: 100%;
    margin: 2rem 0;
    font-size: 1.3rem;
  `,
  ButtonContainer: styled.div`
    display: flex;
    gap: 1rem;
    button {
      padding: 1rem 6rem;
      border-radius: 8px;
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

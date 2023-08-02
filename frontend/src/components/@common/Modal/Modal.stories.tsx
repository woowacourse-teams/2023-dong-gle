import { Meta, StoryObj } from '@storybook/react';
import Modal from './Modal';
import Button from 'components/@common/Button/Button';
import { useModal } from 'hooks/@common/useModal';
import { styled } from 'styled-components';

const meta = {
  title: 'common/Modal',
  component: Modal,
  argTypes: {
    children: {
      control: false,
    },
    isOpen: {
      control: false,
    },
  },
  args: {
    isOpen: false,
  },
} satisfies Meta<typeof Modal>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: ({ ...rest }) => {
    const { isOpen, openModal, closeModal } = useModal();
    return (
      <>
        <Button variant='secondary' onClick={openModal}>
          Open Modal
        </Button>
        <Modal {...rest} isOpen={isOpen} closeModal={closeModal}>
          <ModalContent>
            <h1>모달</h1>
            <p>내용을 마음껏 써주세요.</p>
          </ModalContent>
        </Modal>
      </>
    );
  },
};

const ModalContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 30rem;
  p {
    margin-top: 13rem;
    font-size: 1.3rem;
  }
`;

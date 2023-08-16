/* eslint-disable react/jsx-key */
import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';
import Toast, { Props, ToastTheme, ToastType } from './Toast';
import Button from '../Button/Button';
import { useToast } from '../../../hooks/@common/useToast';

const meta: Meta<typeof Toast> = {
  title: 'common/Toast',
  component: Toast,
  args: {
    theme: 'light',
    type: 'plain',
    duration: 3000,
    hasCloseButton: false,
    hasProgressBar: true,
    message: '휴지통으로 이동됨',
  },
  argTypes: {
    theme: {
      description: 'Toast의 전체 테마입니다.',
      options: Object.values(ToastTheme),
      control: { type: 'radio' },
    },
    type: {
      description: 'Toast의 정보 type입니다.',
      options: Object.values(ToastType),
      control: { type: 'radio' },
    },
    duration: {
      description: 'Toast가 띄어지는 시간(second)입니다.',
      control: { type: 'number' },
    },
    hasCloseButton: {
      description: 'Toast의 닫기 버튼 유무입니다.',
      control: { type: 'boolean' },
    },
    hasProgressBar: {
      description: 'Toast의 진척도를 나타내는 ProgressBar 입니다.',
      control: { type: 'boolean' },
    },
  },
};

export default meta;

type Story = StoryObj<typeof meta>;

const ShowToastButton = ({ args }: { args: Props }) => {
  const toast = useToast();

  const addToast = () => {
    toast.show({ message: '안녕!', ...args });
  };

  return (
    <Button variant={'secondary'} onClick={addToast}>
      Show Toast
    </Button>
  );
};

export const Playground: Story = {
  render: (args) => {
    return <ShowToastButton args={args} />;
  },
};

const StoryColumnContainer = ({ children }: { children: React.ReactNode }) => {
  return <div style={{ display: 'flex' }}>{children}</div>;
};

export const Themes: Story = {
  render: () => {
    return (
      <StoryColumnContainer>
        <StoryContainer>
          {Object.values(ToastTheme).map((theme, index) => (
            <StoryItemContainer key={index}>
              <StoryItemTitle style={{ fontSize: '1.5rem', fontWeight: 600 }}>
                {theme}
              </StoryItemTitle>
              {Object.values(ToastType).map((type, index) => (
                <StoryItemContainer key={index}>
                  <StoryItemTitle>{type}</StoryItemTitle>
                  <Toast
                    toastId={index}
                    theme={theme}
                    type={type}
                    duration={100000}
                    message={'소셜 로그인에 실패하였습니다'}
                    onClose={() => {}}
                  />
                </StoryItemContainer>
              ))}
            </StoryItemContainer>
          ))}
        </StoryContainer>
        <StoryContainer>
          {Object.values(ToastTheme).map((theme, index) => (
            <StoryItemContainer key={index}>
              <StoryItemTitle style={{ fontSize: '1.5rem', fontWeight: 600 }}>
                {theme} With Progress Bar
              </StoryItemTitle>
              {Object.values(ToastType).map((type, index) => (
                <StoryItemContainer key={index}>
                  <StoryItemTitle>{type}</StoryItemTitle>
                  <Toast
                    toastId={index}
                    theme={theme}
                    type={type}
                    hasProgressBar={true}
                    duration={100000}
                    message={'소셜 로그인에 실패하였습니다'}
                    onClose={() => {}}
                  />
                </StoryItemContainer>
              ))}
            </StoryItemContainer>
          ))}
        </StoryContainer>
      </StoryColumnContainer>
    );
  },
};

export const Closable: Story = {
  render: () => {
    const closable = [true, false];

    return (
      <StoryContainer>
        {Object.values(closable).map((closable, index) => (
          <StoryItemContainer key={index}>
            <StoryItemTitle style={{ fontSize: '1.5rem', fontWeight: 600 }}>
              {closable}
            </StoryItemTitle>
            <Toast
              toastId={index}
              hasCloseButton={closable}
              duration={100000}
              message={'소셜 로그인에 실패하였습니다'}
              onClose={() => {}}
            />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

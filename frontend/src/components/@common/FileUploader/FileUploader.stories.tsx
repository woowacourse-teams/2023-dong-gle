import type { Meta, StoryObj } from '@storybook/react';
import FileUploader from './FileUploader';

const meta: Meta<typeof FileUploader> = {
  title: 'common/FileUploader',
  component: FileUploader,
};

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: () => {
    const onFileSelect = (file: FormData | null) => {
      if (file) alert('파일이 선택되었습니다!');
    };
    return <FileUploader onFileSelect={onFileSelect} />;
  },
};

import { useMutation } from '@tanstack/react-query';
import { publishWriting as PublishWritingRequest } from 'apis/writings';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import { useState } from 'react';
import { PublishWritingArgs } from 'types/apis/writings';
import { Blog, PublishingPropertyData } from 'types/domain';

type Args = {
  selectCurrentTab: (tabKey: TabKeys) => void;
};

type PublishWritingToBlogArgs = {
  writingId: number;
  publishTo: Blog;
};

export const usePublishingPropertySection = ({ selectCurrentTab }: Args) => {
  const [propertyFormInfo, setPropertyFormInfo] = useState<PublishingPropertyData>({ tags: [] });
  const { mutate: publishWriting, isLoading } = useMutation(PublishWritingRequest, {
    onSuccess: () => {
      selectCurrentTab(TabKeys.WritingProperty);
      alert('글 발행에 성공했습니다.');
    },
    onError: () => alert('글 발행에 실패했습니다.'),
  });

  const publishWritingToBlog = ({ writingId, publishTo }: PublishWritingToBlogArgs) => {
    const body = {
      publishTo,
      tags: propertyFormInfo.tags,
    };

    publishWriting({ writingId, body });
  };

  const setTags = (tags: string[]) => {
    setPropertyFormInfo((prev) => ({ ...prev, tags }));
  };

  return { isLoading, setTags, publishWritingToBlog };
};

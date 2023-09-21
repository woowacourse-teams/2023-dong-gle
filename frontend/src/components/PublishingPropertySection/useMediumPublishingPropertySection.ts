import { useMutation } from '@tanstack/react-query';
import { publishWritingToMedium as publishWritingToMediumRequest } from 'apis/writings';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import { useToast } from 'hooks/@common/useToast';
import { useState } from 'react';
import type { PublishWritingToMediumRequest } from 'types/apis/writings';
import { HttpError } from 'utils/apis/HttpError';

type Args = {
  selectCurrentTab: (tabKey: TabKeys) => void;
};

export const useMediumPublishingPropertySection = ({ selectCurrentTab }: Args) => {
  const [propertyFormInfo, setPropertyFormInfo] = useState<PublishWritingToMediumRequest>({
    tags: [],
    publishStatus: 'PUBLIC',
  });
  const toast = useToast();
  const { mutate: publishWritingToMedium, isLoading } = useMutation(
    (writingId: number) => publishWritingToMediumRequest({ writingId, body: propertyFormInfo }),
    {
      onSuccess: () => {
        selectCurrentTab(TabKeys.WritingProperty);
        toast.show({ type: 'success', message: '글 발행에 성공했습니다.' });
      },
      onError: (error) => {
        if (error instanceof HttpError) toast.show({ type: 'error', message: error.message });
      },
    },
  );

  const setTags = (tags: PublishWritingToMediumRequest['tags']) => {
    setPropertyFormInfo((prev) => ({ ...prev, tags }));
  };

  const setPublishStatus = (publishStatus: PublishWritingToMediumRequest['publishStatus']) => {
    setPropertyFormInfo((prev) => ({ ...prev, publishStatus }));
  };

  return { isLoading, setTags, setPublishStatus, publishWritingToMedium };
};

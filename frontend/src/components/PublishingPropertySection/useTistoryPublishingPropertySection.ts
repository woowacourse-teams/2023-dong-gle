import { useMutation } from '@tanstack/react-query';
import { publishWritingToTistory as publishWritingToTistoryRequest } from 'apis/writings';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import { useToast } from 'hooks/@common/useToast';
import { useRef, useState } from 'react';
import type { PublishWritingToTistoryRequest } from 'types/apis/writings';
import { HttpError } from 'utils/apis/HttpError';

type Args = {
  selectCurrentTab: (tabKey: TabKeys) => void;
};

export const useTistoryPublishingPropertySection = ({ selectCurrentTab }: Args) => {
  const passwordRef = useRef<HTMLInputElement>(null);
  const dateRef = useRef<HTMLInputElement>(null);
  const timeRef = useRef<HTMLInputElement>(null);

  const [propertyFormInfo, setPropertyFormInfo] = useState<PublishWritingToTistoryRequest>({
    tags: [],
    publishStatus: 'PUBLIC',
    password: '',
    categoryId: '0', // TODO: 카테고리 선택 기능 추가
    publishTime: '',
  });
  const toast = useToast();
  const { mutate: publishWritingToTistory, isLoading } = useMutation(
    (writingId: number) => {
      const publishTime =
        dateRef.current && timeRef.current
          ? `${dateRef.current.value} ${timeRef.current.value}:59.999`
          : '';
      return publishWritingToTistoryRequest({
        writingId,
        body: {
          ...propertyFormInfo,
          password: passwordRef.current?.value ?? '',
          publishTime,
        },
      });
    },
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

  const setTags = (tags: PublishWritingToTistoryRequest['tags']) => {
    setPropertyFormInfo((prev) => ({ ...prev, tags }));
  };

  const setPublishStatus = (publishStatus: PublishWritingToTistoryRequest['publishStatus']) => {
    setPropertyFormInfo((prev) => ({ ...prev, publishStatus }));
  };

  return {
    isLoading,
    propertyFormInfo,
    setTags,
    setPublishStatus,
    passwordRef,
    dateRef,
    timeRef,
    publishWritingToTistory,
  };
};

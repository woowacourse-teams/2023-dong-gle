import { publishWriting } from 'apis/writings';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import useMutation from 'hooks/@common/useMutation';
import { usePageNavigate } from 'hooks/usePageNavigate';
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
  const { mutateQuery, isLoading } = useMutation<PublishWritingArgs, null>({
    fetcher: publishWriting,
    onSuccess: () => selectCurrentTab(TabKeys.WritingProperty),
  });

  const publishWritingToBlog = async ({ writingId, publishTo }: PublishWritingToBlogArgs) => {
    const body = {
      publishTo,
      tags: propertyFormInfo.tags,
    };

    await mutateQuery({ writingId, body });
  };

  const setTags = (tags: string[]) => {
    setPropertyFormInfo((prev) => ({ ...prev, tags }));
  };

  return { isLoading, setTags, publishWritingToBlog };
};

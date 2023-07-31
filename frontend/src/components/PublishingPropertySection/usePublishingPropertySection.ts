import { publishWriting } from 'apis/writings';
import useMutation from 'hooks/@common/useMutation';
import { useState } from 'react';
import { PublishWritingArgs } from 'types/apis/writings';
import { Blog, PublishingPropertyData } from 'types/domain';

type PublishWritingToBlogArgs = {
  writingId: number;
  publishTo: Blog;
};

export const usePublishingPropertySection = () => {
  const [propertyFormInfo, setPropertyFormInfo] = useState<PublishingPropertyData>({ tags: [] });
  const { mutateQuery } = useMutation<PublishWritingArgs, null>({
    fetcher: publishWriting,
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

  return { setTags, publishWritingToBlog };
};

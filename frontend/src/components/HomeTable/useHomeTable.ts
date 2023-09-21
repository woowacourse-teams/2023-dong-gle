import { useEffect, useRef, useState } from 'react';
import { useQuery } from '@tanstack/react-query';

import { getHomeWritings } from 'apis/writings';
import { GetHomeWritingsResponse } from 'types/apis/writings';

export const useHomeTable = (initialPageIndex: number) => {
  const [activePage, setActivePage] = useState(initialPageIndex);
  const [fetchOption, setFetchOption] = useState(`?page=${activePage}&sort=createAt,desc`);
  const { data } = useQuery<GetHomeWritingsResponse>(['homeWritings', fetchOption], () =>
    getHomeWritings(fetchOption),
  );
  const rowRef = useRef<HTMLTableRowElement>(null);

  const changeActivePage = (pageIndex: number) => {
    setFetchOption(`?page=${pageIndex}&sort=createAt,desc`);
    setActivePage(pageIndex);
  };

  useEffect(() => {
    rowRef.current?.focus();
  }, []);

  return {
    content: data?.content,
    pageable: data?.pageable,
    totalPages: data?.totalPages,
    totalElements: data?.totalElements,
    last: data?.last,
    size: data?.size,
    number: data?.number,
    sort: data?.sort,
    numberOfElements: data?.numberOfElements,
    first: data?.first,
    empty: data?.empty,
    rowRef,
    activePage,
    changeActivePage,
  };
};

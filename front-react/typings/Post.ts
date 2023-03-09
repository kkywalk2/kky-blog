export interface IPost {
  id: number;
  title: string;
  category: string;
  views: number;
  createdAt: string;
  updatedAt: string;
}

interface Sort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}

interface Pageable {
  sort: Sort;
  offset: number;
  pageSize: number;
  pageNumber: number;
  paged: boolean;
  unpaged: boolean;
}

interface Sort2 {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}

export interface PostResponse {
  content: IPost[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: Sort2;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

interface Comment {
  id: number;
  postId: number;
  accountName: string;
  content: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface PostDetailResponse {
  id: number;
  title: string;
  category: string;
  views: number;
  createdAt: Date;
  updatedAt: Date;
  content: string;
  comments: Comment[];
}

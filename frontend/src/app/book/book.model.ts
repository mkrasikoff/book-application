export interface Book {
  id: number;
  title: string;
  author: Author;
  description: string;
  rating: number;
  imageUrl: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface Author {
  id: number;
  name: string;
  surname: string;
}

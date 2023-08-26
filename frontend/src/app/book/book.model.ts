export interface Book {
  id: number;
  title: string;
  authorId: string;
  description: string;
  rating: number;
  imageUrl: string;
  createdAt: Date;
  updatedAt: Date;
}

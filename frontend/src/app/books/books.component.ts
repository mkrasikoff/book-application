import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';
import { Book } from '../book.model';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html'
})
export class BooksComponent implements OnInit {

  books: Book[] = [];
  selectedBook: Book | null = null;
  formBook: Book = this.emptyBook();

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
  }

  emptyBook(): Book {
    return {
      id: 0,
      title: '',
      authorId: '',
      description: '',
      rating: 0,
      imageUrl: '',
      createdAt: new Date(0),
      updatedAt: new Date(0),
    };
  }

  getBook(id: number): void {
    this.bookService.getBook(id).subscribe(book => {
      this.selectedBook = book;
    });
  }

  editBook(book: Book): void {
    this.formBook = {...book};
  }

  createOrUpdateBook(): void {
    if (this.formBook.id) {
      this.updateBook(this.formBook.id, this.formBook);
    } else {
      this.createBook(this.formBook);
    }
    this.formBook = this.emptyBook();
  }

  createBook(book: Book): void {
    this.bookService.createBook(book).subscribe(newBook => {
      this.books.push(newBook);
    });
  }

  updateBook(id: number, book: Book): void {
    this.bookService.updateBook(id, book).subscribe(updatedBook => {
      const index = this.books.findIndex(b => b.id === id);
      if (index > -1) {
        this.books[index] = updatedBook;
      }
    });
  }

  deleteBook(id: number): void {
    this.bookService.deleteBook(id).subscribe(() => {
      const index = this.books.findIndex(b => b.id === id);
      if (index > -1) {
        this.books.splice(index, 1);
      }
    });
  }
}

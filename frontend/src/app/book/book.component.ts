import { Component, OnInit } from '@angular/core';
import { BookService } from './book.service';
import { Book } from './book.model';

@Component({
  selector: 'app-books',
  templateUrl: './book.component.html'
})
export class BookComponent implements OnInit {

  books: Book[] = [];
  selectedBook: Book | null = null;
  formBook: Book = this.emptyBook();
  errorMessage: string | null = null;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getAllUsers();
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

  handleError(error: any): void {
    this.errorMessage = error?.error?.message || 'An error occurred';
  }

  getBook(id: number): void {
    this.bookService.getBook(id).subscribe(book => this.selectedBook = book);
  }

  editBook(book: Book): void {
    this.formBook = {...book};
  }

  createOrUpdateBook(): void {
    this.formBook.id ? this.updateBook(this.formBook.id, this.formBook) : this.createBook(this.formBook);
  }

  createBook(book: Book): void {
    this.bookService.createBook(book).subscribe(
      newBook => {
        this.books.push(newBook);
        this.formBook = this.emptyBook();
        this.errorMessage = null;
      },
      error => this.handleError(error)
    );
  }

  updateBook(id: number, book: Book): void {
    this.bookService.updateBook(id, book).subscribe(
      updatedBook => {
        const index = this.books.findIndex(b => b.id === id);
        if (index > -1) {
          this.books[index] = updatedBook;
          this.formBook = this.emptyBook();
          this.errorMessage = null;
        }
      },
      error => this.handleError(error)
    );
  }

  deleteBook(id: number): void {
    this.bookService.deleteBook(id).subscribe(() => {
      const index = this.books.findIndex(b => b.id === id);
      if (index > -1) {
        this.books.splice(index, 1);
      }
    });
  }

  getAllUsers(): void {
    this.bookService.getAllBooks().subscribe(books => this.books = books);
  }
}
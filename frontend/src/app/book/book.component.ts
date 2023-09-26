import { Component, OnInit } from '@angular/core';
import { BookService } from './book.service';
import { Book } from './book.model';
import { SharedService } from '../shared/shared.service';
import {User} from "../user/user.model";

@Component({
  selector: 'app-books',
  templateUrl: './book.component.html'
})
export class BookComponent implements OnInit {

  books: Book[] = [];
  selectedBook: Book | null = null;
  selectedUser: User | null = null;
  formBook: Book = this.emptyBook();
  errorMessage: string | null = null;

  constructor(private bookService: BookService, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.getAllBooks();
    this.selectedUser = this.sharedService.getSelectedUser() as User | null;
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
    this.bookService.getBook(id).subscribe(book => {
      this.selectedBook = book;
      this.sharedService.setSelectedUser(book as any);
    });
  }

  editBook(book: Book): void {
    this.formBook = {...book};
  }

  createOrUpdateBook(): void {
    this.formBook.id ? this.updateBook(this.formBook.id, this.formBook) : this.createBook(this.formBook);
  }

  createBook(book: Book): void {
    if (this.selectedUser) {
      this.bookService.createBook(book, this.selectedUser.id).subscribe(
        newBook => {
          this.books.push(newBook);
          this.formBook = this.emptyBook();
          this.errorMessage = null;
        },
        error => this.handleError(error)
      );
    } else {
      this.errorMessage = 'No user selected';
    }
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

  getAllBooks(): void {
    this.bookService.getAllBooks().subscribe(books => this.books = books);
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { BookService } from './book.service';
import { Book } from './book.model';
import { SharedService } from '../shared/shared.service';
import { User } from "../user/user.model";
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-books',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit, OnDestroy {

  books: Book[] = [];
  selectedBook: Book | null = null;
  selectedUser: User | null = null;
  formBook: Book = this.emptyBook();
  errorMessage: string | null = null;

  private routerSubscription: Subscription | null = null;

  constructor(
    private bookService: BookService,
    private sharedService: SharedService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.selectedUser = this.sharedService.getSelectedUser();
    if (this.selectedUser) {
      this.getAllBooks(this.selectedUser.id);
    }

    this.routerSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        if (this.selectedUser) {
          this.getAllBooks(this.selectedUser.id);
        } else {
          this.errorMessage = 'No user selected';
        }
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  emptyBook(): Book {
    return {
      id: 0,
      title: '',
      author: {
        id: 0,
        name: '',
        surname: ''
      },
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
      const modal = document.getElementById('bookModal');
      if (modal) {
        modal.style.display = 'block';
      }
    });
  }

  closeModal(event?: MouseEvent): void {
    // Checking if the click was on a modal background
    if (event && (event.target as Element).classList.contains('modal')) {
      const modal = document.getElementById('bookModal');
      if (modal) {
        modal.style.display = 'none';
      }
    }
    // If the click was on the cross, just close the modal window
    else if (!event) {
      const modal = document.getElementById('bookModal');
      if (modal) {
        modal.style.display = 'none';
      }
    }
  }

  editBook(book: Book): void {
    this.formBook = {...book};
  }

  createOrUpdateBook(): void {
    this.formBook.id ? this.updateBook(this.formBook.id, this.formBook) : this.createBook(this.formBook);
  }

  createBook(book: Book): void {
    if (this.selectedUser) {
      book.author.id = 0;
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
    book.author.id = 0;
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

  deleteAllBooks(): void {
    if (this.selectedUser) {
      this.bookService.deleteAllBooks(this.selectedUser.id).subscribe(
        () => {
          this.books = [];
          this.errorMessage = null;
        },
        error => this.handleError(error)
      );
    } else {
      this.errorMessage = 'No user selected';
    }
  }

  getAllBooks(userId: number): void {
    this.bookService.getAllBooks(userId).subscribe(
      books => this.books = books,
      error => this.handleError(error)
    );
  }

  navigateToHome(): void {
    this.router.navigate(['/']);
  }
}

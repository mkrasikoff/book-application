import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { BookService } from '../book/book.service';
import { Book } from '../book/book.model';
import {Router} from "@angular/router";

@Component({
  selector: 'app-all-books',
  templateUrl: './all-books.component.html',
})
export class AllBooksComponent implements OnInit {
  books: Book[] = [];
  selectedBook: Book | null = null;
  errorMessage: string | null = null;

  @ViewChild('bookModal') bookModal!: ElementRef;

  constructor(
    private bookService: BookService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.getAllBooks();
  }

  handleError(error: any): void {
    this.errorMessage = error?.error?.message || 'An error occurred';
  }

  getAllBooks(): void {
    this.bookService.getAllBooks().subscribe(
      books => this.books = books,
      error => this.handleError(error)
    );
  }

  getBook(id: number): void {
    this.bookService.getBook(id).subscribe(book => {
      this.selectedBook = book;
      this.showModal();
    });
  }

  showModal(): void {
    if (this.bookModal && this.bookModal.nativeElement) {
      this.bookModal.nativeElement.style.display = 'block';
    }
  }

  closeModal(): void {
    this.selectedBook = null;
    if (this.bookModal && this.bookModal.nativeElement) {
      this.bookModal.nativeElement.style.display = 'none';
    }
  }

  navigateToHome(): void {
    this.router.navigate(['/']);
  }
}

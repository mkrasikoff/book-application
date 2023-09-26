import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Book } from './book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl = 'http://localhost:8181/books';

  constructor(private http: HttpClient) { }

  getBook(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/${id}`);
  }

  createBook(book: Book, userId: number): Observable<Book> {
    const url = `${this.baseUrl}?userId=${userId}`;
    return this.http.post<Book>(url, book).pipe(
      catchError((error: HttpErrorResponse) => {
        this.handleErrors(error);
        return throwError(() => error);
      })
    );
  }

  updateBook(id: number, book: Book): Observable<Book> {
    return this.http.put<Book>(this.baseUrl, book).pipe(
      catchError((error: HttpErrorResponse) => {
        this.handleErrors(error);
        return throwError(() => error);
      })
    );
  }

  deleteBook(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.baseUrl);
  }

  private handleErrors(error: HttpErrorResponse): void {
    if (error.status === 400) {
      console.log("Validation Error: " + error.error);
    } else if (error.status === 409) {
      console.log("Conflict: " + (error.error || 'Check your book data.'));
    } else {
      console.error(error);
    }
  }
}

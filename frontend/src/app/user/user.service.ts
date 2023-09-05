import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8182/users';

  constructor(private http: HttpClient) { }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl, user).pipe(
      catchError((error: HttpErrorResponse) => {
        this.handleErrors(error);
        return throwError(error);
      })
    );
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, user).pipe(
      catchError((error: HttpErrorResponse) => {
        this.handleErrors(error);
        return throwError(error);
      })
    );
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}`);
  }

  private handleErrors(error: HttpErrorResponse): void {
    if (error.status === 400) {
      alert("Validation Error: " + error.error);
    } else if (error.status === 409) {
      alert("Conflict: " + (error.error || 'Username or email already exists.'));
    } else {
      console.error(error);
    }
  }
}

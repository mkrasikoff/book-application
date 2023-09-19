import { Injectable } from '@angular/core';
import { User } from '../user/user.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private selectedUserSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public selectedUser$: Observable<User | null> = this.selectedUserSubject.asObservable();

  constructor() { }

  setSelectedUser(user: User | null): void {
    this.selectedUserSubject.next(user);
  }

  getSelectedUser(): Observable<User | null> {
    return this.selectedUser$;
  }
}

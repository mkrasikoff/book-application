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
    console.log('Setting user:', user);
    this.selectedUserSubject.next(user);
  }

  getSelectedUser(): Observable<User | null> {
    console.log('Getting user:', this.selectedUserSubject.value);
    return this.selectedUser$;
  }
}

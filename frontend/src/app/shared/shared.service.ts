import { Injectable } from '@angular/core';
import { User } from '../user/user.model';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private selectedUser: User | null = null;

  constructor() { }

  setSelectedUser(user: User | null): void {
    console.log('Setting user:', user);
    this.selectedUser = user;
  }

  getSelectedUser(): User | null {
    console.log('Getting user:', this.selectedUser);
    return this.selectedUser;
  }
}

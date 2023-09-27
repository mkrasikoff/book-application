import { Injectable } from '@angular/core';
import { User } from '../user/user.model';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  constructor() { }

  setSelectedUser(user: User | null): void {
    console.log('Setting user:', user);
    if (user) {
      localStorage.setItem('selectedUser', JSON.stringify(user));
    } else {
      localStorage.removeItem('selectedUser');
    }
  }

  getSelectedUser(): User | null {
    console.log('Getting user from localStorage');
    const userJson = localStorage.getItem('selectedUser');
    return userJson ? JSON.parse(userJson) as User : null;
  }
}

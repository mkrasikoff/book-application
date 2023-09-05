import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { User } from './user.model';

@Component({
  selector: 'app-users',
  templateUrl: './user.component.html'
})
export class UserComponent implements OnInit {

  users: User[] = [];
  selectedUser: User | null = null;
  formUser: User = this.emptyUser();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  emptyUser(): User {
    return {
      id: 0,
      username: '',
      password: '',
      email: '',
      createdAt: new Date(0),
      updatedAt: new Date(0),
    };
  }

  getUser(id: number): void {
    this.userService.getUser(id).subscribe(user => {
      this.selectedUser = user;
    });
  }

  editUser(user: User): void {
    this.formUser = {...user};
  }

  createOrUpdateUser(): void {
    if (this.formUser.id) {
      this.updateUser(this.formUser.id, this.formUser);
    } else {
      this.createUser(this.formUser);
    }
  }

  createUser(user: User): void {
    this.userService.createUser(user).subscribe(
      newUser => {
        this.users.push(newUser);
        this.formUser = this.emptyUser();
      },
      error => {
        if (error.status === 409) {
          alert('Username or email already exists.');
        } else {
          console.error(error);
        }
      }
    );
  }

  updateUser(id: number, user: User): void {
    this.userService.updateUser(id, user).subscribe(updatedUser => {
        const index = this.users.findIndex(u => u.id === id);
        if (index > -1) {
          this.users[index] = updatedUser;
          this.formUser = this.emptyUser();
        }
      },
      error => {
        console.error(error);
      }
    );
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(() => {
      const index = this.users.findIndex(u => u.id === id);
      if (index > -1) {
        this.users.splice(index, 1);
      }
    });
  }

  getAllUsers(): void {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }
}

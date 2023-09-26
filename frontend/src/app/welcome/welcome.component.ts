import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharedService } from '../shared/shared.service';
import { User } from '../user/user.model';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
})
export class WelcomeComponent implements OnInit {

  user: User | null = null;

  constructor(private router: Router, private sharedService: SharedService) {}

  ngOnInit(): void {
    this.user = this.sharedService.getSelectedUser();
  }

  navigateTo(path: string): void {
    if (path === 'books') {
      const user = this.sharedService.getSelectedUser();
      if (user) {
        this.router.navigate(['/books']);
      } else {
        alert('Please select a user before navigating to the Book API.');
      }
    } else {
      this.router.navigate([`/${path}`]);
    }
  }
}

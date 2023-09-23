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
    this.sharedService.getSelectedUser().subscribe(user => {
      this.user = user;
    });
  }

  navigateTo(path: string): void {
    if (path === 'books') {
      this.sharedService.getSelectedUser().subscribe(user => {
        if (user) {
          this.router.navigate(['/books']);
        } else {
          alert('Please select a user before navigating to the Book API.');
        }
      });
    } else {
      this.router.navigate([`/${path}`]);
    }
  }
}

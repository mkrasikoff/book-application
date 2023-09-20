import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SharedService } from '../shared/shared.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
})
export class WelcomeComponent {

  constructor(private router: Router, private sharedService: SharedService) {}

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

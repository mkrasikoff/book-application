import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SharedService } from './shared.service';

@Injectable({
  providedIn: 'root'
})
export class UserSelectionGuard implements CanActivate {

  constructor(private sharedService: SharedService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return new Observable(observer => {
      this.sharedService.getSelectedUser().subscribe(user => {
        if (user) {
          console.log('User is selected, allowing navigation');
          observer.next(true);
        } else {
          console.log('No user selected, redirecting to /users');
          observer.next(this.router.createUrlTree(['/users']));
        }
      });
    });
  }
}

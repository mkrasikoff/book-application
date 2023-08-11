import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';
import { BooksComponent } from './books/books.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'books', component: BooksComponent },
  { path: 'users', component: UsersComponent },
  { path: '**', redirectTo: '/404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

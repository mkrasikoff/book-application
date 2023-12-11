import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import { BookComponent } from '../book/book.component';
import { AllBooksComponent } from '../book/all-books.component';
import { UserComponent } from '../user/user.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'books', component: BookComponent },
  { path: 'all-books', component: AllBooksComponent },
  { path: 'users', component: UserComponent },
  { path: '**', redirectTo: '/404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

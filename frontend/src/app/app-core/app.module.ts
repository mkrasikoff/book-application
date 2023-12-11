import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from '../home/home.component';
import { BookComponent } from '../book/book.component';
import { AllBooksComponent } from "../book/all-books.component";
import { BookService } from "../book/book.service";
import { UserComponent } from '../user/user.component';
import { UserService } from '../user/user.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BookComponent,
    AllBooksComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [BookService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }

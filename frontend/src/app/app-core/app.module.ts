import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WelcomeComponent } from '../welcome/welcome.component';
import { BookComponent } from '../book/book.component';
import { BookService } from "../book/book.service";
import { UserComponent } from '../user/user.component';
import { UserService } from '../user/user.service';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    BookComponent,
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

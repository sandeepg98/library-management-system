import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';

import { Book } from 'src/app/domain/Book';
import { BooksService } from '../../service/books.service';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-booking-component',
  templateUrl: './booking-component.component.html',
  styleUrls: ['./booking-component.component.scss']
})

export class BookingComponentComponent implements OnInit {

  @Input()
  result$: Observable<Book[]>;

  borrowedBooks: string[] = new Array();

  userName = "";
  status = "";

  constructor(
    private bookService: BooksService, 
    private router: Router, 
    private matDialog: MatDialog, 
    private cartService: CartService) {

    this.result$ = bookService.getBooks();
  }

  ngOnInit() {
    this.userName = this.bookService.getBookerName();
    this.borrowedBooks = new Array();
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    this.matDialog.open(DialogComponent, dialogConfig);
  }

  borrowBook(title: any) {
  
    if (this.borrowedBooks.length >= 2) {
      this.openDialog();
    }
    else if(this.borrowedBooks[0] != title) {
      this.borrowedBooks.push(title);
    }
    this.cartService.cartValues(this.borrowedBooks);
  }

  unBook(bookId : String) {
      if(this.borrowedBooks[0] === bookId)
        this.borrowedBooks.splice(0, 1);
      else if(this.borrowedBooks[1] === bookId)
        this.borrowedBooks.splice(1, 1);
  }

  getBorrowingStatus() {
    this.bookService.getBorrowingStatus("Sudip",this.borrowedBooks);
  }

}
import { Component, Input, OnInit } from '@angular/core';
import { ReturnService } from '../../service/return.service';
import { Observable } from 'rxjs';
import { BookDTO } from 'src/app/domain/BookDTO';

@Component({
  selector: 'app-returning-component',
  templateUrl: './returning-component.component.html',
  styleUrls: ['./returning-component.component.scss']
})

export class ReturningComponentComponent implements OnInit {

  @Input()
  result$: Observable<BookDTO[]>;

  returnedBooks: string[] = new Array();
  
  constructor(private returnService: ReturnService
    ) {this.result$ = returnService.getBorrowedBooks() }

  ngOnInit(): void {
  }

  returnBook(bookId: string){
    if(this.returnedBooks[0] != bookId && this.returnedBooks[1] != bookId) {
      this.returnedBooks.push(bookId);
    }
    console.log(this.returnedBooks);
  }

  confirmReturn(){
    this.returnService.setReturnBookValues(this.returnedBooks);
    this.returnedBooks = new Array();
  }

}

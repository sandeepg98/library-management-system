import { Injectable } from '@angular/core';
import {BookDTO} from '../domain/BookDTO';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReturnService {

  userId = "";

  private viewBorrowedBooksURL = "http://localhost:8080/hexad/viewBorrowedBooks";
  private returnBookURL = "http://localhost:8080/hexad/returnBook";
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  } 

  setUserId(userId : string){
    this.userId = userId;
  }

  getBorrowedBooks(): Observable<BookDTO[]>
  {
    return this.http.get<BookDTO[]>(this.viewBorrowedBooksURL+"/"+this.userId);
  }

  setReturnBookValues(returnedBooks: string[]){
    const headers = { 'content-type': 'application/json'}
    const body = { userId: this.userId, bookIds: returnedBooks};
    console.log("BODY",body);
    this.http.post(this.returnBookURL,body,{'headers':headers}).subscribe(res => {
      console.log(res);
    }, error=>{
      alert(error.error.text);
    });
  }
}

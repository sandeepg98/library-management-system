import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Book } from '../domain/Book';

@Injectable({
  providedIn: 'root'
})

export class BooksService {

  private getBooksURL = 'http://localhost:8080/hexad/viewCatalogue';
  private getBookBorrowingStatusURL = 'http://localhost:8080/hexad/borrowBook';

  private bookerName = "";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }  

  getBooks(): Observable<Book[]>
  {
    return this.http.get<Book[]>(this.getBooksURL);
  }

  getBorrowingStatus(name: string,booksList: string[]){
    const headers = { 'content-type': 'application/json'}
    const body = { userId: this.bookerName, bookIds: booksList};
    console.log("BODY",body);
    this.http.post(this.getBookBorrowingStatusURL,body,{'headers':headers}).subscribe(res => {
      console.log(res);
    }, error=>{
      alert(error.error.text);
    });
  }

  setBookerName(name: string)
  {
    this.bookerName = name;
  }

  getBookerName()
  {
    return this.bookerName;
  }

}

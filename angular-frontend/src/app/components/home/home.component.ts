import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BooksService } from 'src/app/service/books.service';
import { ReturnService } from 'src/app/service/return.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})

export class HomeComponent implements OnInit {

  userName = "";

  constructor(private router:Router,
    private bookService: BooksService,
    private returnService: ReturnService
    ) { }

  ngOnInit(): void {
  }

  onClickBooking()
  {
    console.log("CLICKED");
    console.log(name);
    this.router.navigate(['/booking']);
  }

  
  onClickReturning(userId : string)
  {
    console.log("CLICKED");
    this.router.navigate(['/returning']);
    this.returnService.setUserId(userId);
  }

  setBookerName(name: string)
  {
    this.userName = name;
    this.bookService.setBookerName(this.userName);
  }

}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class CartService {

  borrowedBooks: string[] = new Array();

  constructor() { }

  cartValues(cartIncomingValue: string[])
  {
    this.borrowedBooks = cartIncomingValue;
  }

  getCartValue()
  {
    return this.borrowedBooks;
  }

}

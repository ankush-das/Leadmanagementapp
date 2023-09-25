import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: any[] = [];

  addToCart(product: any) {
    this.cart.push(product);
  }

  getCart() {
    return this.cart;
  }

  removeFromCart(product: any) {
    const index = this.cart.indexOf(product);
    if (index !== -1) {
      this.cart.splice(index, 1);
    }
  }
}

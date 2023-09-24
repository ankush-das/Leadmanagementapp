import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';


interface CycleData {
  id: number;
  cycle: {
    id: number;
    brand: string;
    stock: number;
    numBorrowed: number;
    price: number;
    numAvailable: number;
  };
  quantity: number;
  booked: boolean;
  returned: boolean;
}


@Component({
  selector: 'app-cartpage',
  templateUrl: './cartpage.component.html',
  styleUrls: ['./cartpage.component.css']
})
export class CartpageComponent {

  newdata: CycleData[] = [];
  subtotal: number = 0;
  grandTotal: number = 0;

  constructor(private _http: HttpClient) { }

  getallcycle() {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    });
    return this._http.get<CycleData[]>('http://localhost:8080/api/cycles/cart', { headers: headers });
  }

  ngOnInit() {
    this.getallcycle().subscribe((res: CycleData[]) => {
      this.newdata = res.filter(item => !item.booked && !item.returned);
      console.log('Success: Filtered Response from API:', this.newdata);
    });
  }

  calculateTotalPrice(item: any): number {
    return item.cycle.price * item.quantity;
  }

  calculateSubtotal(cartItems: CycleData[]): number {
    let subtotal = 0;
    for (const item of cartItems) {
      if (!item.booked && !item.returned) {
        subtotal += this.calculateTotalPrice(item);
      }
    }
    return subtotal;
  }

  checkout() {
    const grandTotal = this.calculateSubtotal(this.newdata);

    const postData = {
      totalAmount: grandTotal,
    };

    this._http.post('http://localhost:8080/api/cycles/checkOut', postData).subscribe(
      (response) => {
        this.subtotal = 0;
        this.grandTotal = 0;
        this.ngOnInit();
        console.log("chekcout done");
      },
      (error) => {
        console.log("No checkout");
      }
    );
  }

  removeFromCartserve(cartId: number, action: string) {
    const requestData = {
      cartId: cartId.toString(),
      action: action
    };

    this.ngOnInit();
    return this._http.post<string>(`http://localhost:8080/api/cycles/cart/remove`, requestData);
  }

  removeFromCart(cartId: number, action: 'remove' | 'reduce') {
    this.removeFromCartserve(cartId, action).subscribe(
      response => {
        if (action === 'remove') {
          console.log('Cycle removed from cart successfully', response);
          this.ngOnInit();
        } else if (action === 'reduce') {
          console.log('Cycle quantity reduced in cart successfully', response);
        }

      },
      error => {
        console.error('Error removing/reducing cycle from cart', error);
      }
    );
  }
}




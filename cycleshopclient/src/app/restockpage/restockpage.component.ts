import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-restockpage',
  templateUrl: './restockpage.component.html',
  styleUrls: ['./restockpage.component.css']
})
export class RestockpageComponent {
  cycleid: string = '';
  cyclecount: string = '';
  cyclestock: number = 0;
  cyclebrand: string = '';
  cycleprice: number = 0;

  newdata: any;

  constructor(private _http: HttpClient) { }

  submitForm() {
    const cycleData = {
      id: this.cycleid,
      count: this.cyclecount,
    };

    const id = this.cycleid;
    const url = `http://localhost:8080/api/cycles/${id}/restock`;

    this._http.post(url, cycleData, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log('Cycle restocked successfully:', response);
      },
      error: (error) => {
        console.error('Error restocking cycle:', error);
      }
    });
  }

  submitAddCycleForm() {
    const newcycleData = {
      brand: this.cyclebrand,
      stock: this.cyclestock,
      price: this.cycleprice
    }

    const url = `http://localhost:8080/api/cycles/addcycle`;


    this._http.post(url, newcycleData, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log('Cycle restocked successfully:', response);
      },
      error: (error) => {
        console.error('Error restocking cycle:', error);
      }
    });
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-returnpage',
  templateUrl: './returnpage.component.html',
  styleUrls: ['./returnpage.component.css']
})
export class ReturnpageComponent {

  newdata: any;

  constructor(private _http: HttpClient) { }

  getallcycle() {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    });

    return this._http.get('http://localhost:8080/api/cycles/cart', { headers });
  }

  ngOnInit() {
    this.getallcycle().subscribe({
      next: (res) => {
        this.newdata = res;
        console.log('Success: Response from API:', this.newdata);
      },
      error: (error) => {
        console.error('Error: Failed to fetch data from API:', error);
      }
    });
  }

  returnCycle(id: number) {

    const requestBody = { cartId: id };

    const url = `http://localhost:8080/api/cycles/return`;

    this._http.post(url, requestBody, { responseType: 'text' }).subscribe(response => {
      this.ngOnInit();
      console.log(`Cycle with ID ${id} returned successfully.`);
    });
  }
}

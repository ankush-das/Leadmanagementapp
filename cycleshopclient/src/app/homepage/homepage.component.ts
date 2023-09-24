import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";



@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {

  newdata: any;

  constructor(private _http: HttpClient) { }

  //getting list of cycle data from this endpoint
  getallcycle() {
    // const headers = new HttpHeaders({

    //   'Authorization': 'Bearer ' + localStorage.getItem('token')

    // });
    return this._http.get('http://localhost:8080/api/cycles/list-data');
  }

  //show data immidiatly on load
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
}

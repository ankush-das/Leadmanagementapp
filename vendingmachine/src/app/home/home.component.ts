import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  productId: number = 0;

  constructor(private router: Router) { }

  onButtonClick() {
    const productId = this.productId;
    this.router.navigate(['/sectionpage', productId]);
  }
}

import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-sectionpage',
  templateUrl: './sectionpage.component.html',
  styleUrls: ['./sectionpage.component.css']
})
export class SectionpageComponent {

  productId: number = 0;
  products: any[] = [];
  filteredProducts: any[] = [];
  sectionName: string = '';

  sectionCart: any[] = [];
  cartClosed: boolean = false;

  constructor(private route: ActivatedRoute, private http: HttpClient, private cartService: CartService) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.productId = +params['productId'];


      this.http.get('assets/data.json').subscribe((data: any) => {
        this.products = data.products;

        const productWithMatchingId = this.products.find(product => product.id === this.productId);

        if (productWithMatchingId) {
          this.sectionName = productWithMatchingId.section;

          // Filter products based on the section name
          this.filteredProducts = this.products.filter(product => product.section === this.sectionName);
        }
      });
    });
  }

  closeCart() {
    this.cartClosed = !this.cartClosed;
  }

  addToCart(product: any) {
    if (product.stock > 0) {
      this.cartService.addToCart(product);
      this.sectionCart.push(product);
      product.stock--;
    }
  }

  getTotalAmount(): number {
    let totalAmount = 0;
    for (const item of this.sectionCart) {
      totalAmount += item.price;
    }
    return totalAmount;
  }
}

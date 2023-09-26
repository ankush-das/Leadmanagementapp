import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'postdetailangular';

  postId: number = 0;

  constructor(private router: Router) { }

  onButtonClick() {
    const postId = this.postId;
    this.router.navigate(['/postdetail', postId]);
  }
}

import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  isCaptureLeadRoute: boolean = false;

  constructor(private route: ActivatedRoute) {
    this.route.url.subscribe((segments) => {
      this.isCaptureLeadRoute = segments.some(segment => segment.path === 'capturelead');
    });
  }

}

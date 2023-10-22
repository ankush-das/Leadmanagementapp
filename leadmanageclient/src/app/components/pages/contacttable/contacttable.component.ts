import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ContactInfo } from 'src/app/model/ContactInfo';
import { LeadFormService } from 'src/app/services/LeadFormService';

@Component({
  selector: 'app-contacttable',
  templateUrl: './contacttable.component.html',
  styleUrls: ['./contacttable.component.css']
})
export class ContacttableComponent implements OnInit {
  lead: any;
  leadContacts: ContactInfo[] = [];

  constructor(private leadContactService: LeadFormService, private router: Router) { }

  ngOnInit(): void {
    this.leadContactService.getAllLeadContacts().subscribe(data => {
      this.leadContacts = data;
      console.log(this.leadContacts);
    });
  }

  onFillButtonClick(contactId: number): void {
    this.router.navigate(['/leadform', contactId]);
  }

  onEditButtonClick(contactId: number): void {
    this.router.navigate(['/leadform', contactId]);
  }

}

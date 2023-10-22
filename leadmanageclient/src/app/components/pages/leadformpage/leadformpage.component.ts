import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeadDTO } from 'src/app/model/LeadDTO';
import { LeadFormService } from 'src/app/services/LeadFormService';

@Component({
  selector: 'app-leadformpage',
  templateUrl: './leadformpage.component.html',
  styleUrls: ['./leadformpage.component.css']
})
export class LeadformpageComponent {

  users: any[] = [];
  lead: LeadDTO = new LeadDTO();
  leadID: number = 0;
  isEditMode = false;

  constructor(private http: HttpClient, private leadFormService: LeadFormService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.leadFormService.getUsers().subscribe(data => {
      this.users = data;
    });

    this.route.params
      .subscribe(params => {
        this.leadID = +params['leadID']; // Use '+' to convert the parameter to a number

      });
  }

  onSubmit() {
    const url = `http://localhost:8080/leads/create/${this.leadID}`;

    this.http.post(url, this.lead).subscribe(
      (response) => {
        console.log('Data sent successfully:', this.lead);
      },
      (error) => {
        console.error('Error:', error);
      }
    );
  }

  updateLead(leadId: number, leadDTO: any) {
    this.leadFormService.updateLead(leadId, leadDTO).subscribe(
      (updatedLead) => {
        console.log('Lead updated successfully:', updatedLead);
      },
      (error) => {
        console.error('Update failed:', error);
      }
    );
  }

  patchSoloLead(leadId: number, leadDTO: any) {
    this.leadFormService.patchLead(leadId, leadDTO).subscribe(
      (updatedLead) => {
        // Handle the response, updatedLead will contain the updated Lead data.
        console.log('Lead updated successfully:', updatedLead);
      },
      (error) => {
        // Handle any errors, such as 404 Not Found if the Lead with the specified ID is not found.
        console.error('Error:', error);
      }
    );
  }

}

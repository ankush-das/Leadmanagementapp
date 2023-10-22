import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ActivityLog } from 'src/app/model/ActivityLog';
import { LeadDTO } from 'src/app/model/LeadDTO';
import { LeadFormService } from 'src/app/services/LeadFormService';
import { PipelineService } from 'src/app/services/PipelineService';
import { ActivityService } from 'src/app/services/activityService';

@Component({
  selector: 'app-leaddetails',
  templateUrl: './leaddetails.component.html',
  styleUrls: ['./leaddetails.component.css']
})
export class LeaddetailsComponent implements OnInit {
  leadID: number = 0;
  lead: any
  leadI = { quote: 'Your initial quote', budget: '', probability: '', tag: '', priority: '', stage: '', source: '' };

  logs: ActivityLog[] = [];

  editMode = false;

  constructor(
    private route: ActivatedRoute,
    private pipelineService: PipelineService,
    private leadFormService: LeadFormService,
    private activityService: ActivityService

  ) {
  }

  ngOnInit(): void {
    this.route.params
      .subscribe(params => {
        this.leadID = +params['leadID'];
      });
    this.pipelineService.getLeadById(this.leadID).subscribe((lead) => {
      this.lead = lead;
      console.log(this.lead);
    });

    this.activityService.getAllLogs(this.leadID)
      .subscribe((data: any[]) => {
        this.logs = data;
        console.log(this.logs);

      });
  }

  transitionLeadToLost(leadId: number) {
    this.pipelineService.lostLeadTransition(leadId)
      .subscribe(
        (response) => {
          console.log('Lead moved to Lost', response);
          window.location.reload();
        },
        (error) => {
          console.error('Transition failed:', error);
        }
      );
  }

  closedWONLeadStageTransition(leadId: number) {
    this.pipelineService.closedWONLeadStageTransition(leadId)
      .subscribe((lead: LeadDTO) => {
        console.log('Lead transitioned to Closed-WON:', lead);
      });
  }

  saveAll() {
    const leadDTO = {
      quote: this.lead.quote, budget: this.lead.budget,
      probability: this.lead.probability, tag: this.lead.tag,
      priority: this.lead.priority, stage: this.lead.stage,
      source: this.lead.source
    };

    this.leadFormService.patchLead(this.leadID, leadDTO).subscribe(
      (updatedLead) => {
        this.lead = updatedLead;
        this.editMode = false;
        console.log('Quote updated successfully.');
      },
      (error) => {
        console.error('Error updating quote:', error);
      }
    );
  }

  formatTimestamp(logDate: Date): string {
    const today = new Date();
    const logDateObj = new Date(logDate);

    if (logDateObj.toDateString() === today.toDateString()) {
      return 'Today';
    }

    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);

    if (logDateObj.toDateString() === yesterday.toDateString()) {
      return 'Yesterday';
    }

    return logDateObj.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
  }

}

import { Component, OnInit } from '@angular/core';
import { PipelineService } from 'src/app/services/PipelineService';
import { faLeftLong, faRightLong, faSearch, faStar, faClock } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

@Component({
  selector: 'app-pipeline',
  templateUrl: './pipeline.component.html',
  styleUrls: ['./pipeline.component.css']
})
export class PipelineComponent implements OnInit {
  faRightLong = faRightLong;
  faLeftLong = faLeftLong;
  faClock = faClock
  faStar = faStar;
  faSearch = faSearch;
  leads: any[] = [];
  searchTerm: string = '';

  activity = {
    activityType: '',
    dueDate: null,
    summary: '',
    detail: '',
    activityStatus: 'PENDING',
    assignedUser: null
  };

  constructor(private pipelineService: PipelineService, private router: Router) { }

  ngOnInit() {
    this.pipelineService.getAllLeads().subscribe((leads) => {
      this.leads = leads;
      console.log(this.leads);
    });
  }

  onTransitionNext(event: Event, leadId: number) {
    event.stopPropagation();
    this.pipelineService.nextLeadStageTransition(leadId)
      .subscribe(
        (response) => {
          console.log('Transition successful:', response);
          window.location.reload();
        },
        (error) => {
          console.error('Transition failed:', error);
        }
      );
  }

  onTransitionPrev(event: Event, leadId: number) {
    event.stopPropagation();
    this.pipelineService.prevLeadStageTransition(leadId)
      .subscribe(
        (response) => {
          console.log('Previous transition successful:', response);
          window.location.reload();
        },
        (error) => {
          console.error('Previous transition failed:', error);
        }
      );
  }

  searchLeadsBySource() {
    this.pipelineService.filterLeadsBySource(this.searchTerm)
      .subscribe(
        (leads) => {
          this.leads = leads;
        },
        (error) => {
          console.error('Error searching by source:', error);
        }
      );
  }

  generateStars(priority: string): { icon: IconProp; starClass: string }[] {
    const stars: { icon: IconProp; starClass: string }[] = [];
    switch (priority) {
      case 'HIGH':
        for (let i = 0; i < 3; i++) {
          stars.push({ icon: faStar, starClass: 'star-gold' });
        }
        break;
      case 'MEDIUM':
        for (let i = 0; i < 2; i++) {
          stars.push({ icon: faStar, starClass: 'star-gold' });
        }
        break;
      case 'LOW':
        stars.push({ icon: faStar, starClass: 'star-gold' });
        break;
      default:
        break;
    }
    return stars;
  }

  submitForm() {
    console.log(this.activity);
  }
}

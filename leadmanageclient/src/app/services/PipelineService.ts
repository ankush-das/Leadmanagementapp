import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LeadDTO } from '../model/LeadDTO';

@Injectable({
    providedIn: 'root',
})
export class PipelineService {
    private baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    getAllLeads(): Observable<any> {
        const url = `${this.baseUrl}/api/lead/all`
        return this.http.get<any>(url);
    }

    getLeadById(leadId: number): Observable<any> {
        const url = `${this.baseUrl}/leads/lead/${leadId}`;
        return this.http.get<any>(url);
    }

    nextLeadStageTransition(leadId: number) {
        const url = `${this.baseUrl}/api/lead/transition/next/${leadId}`;
        return this.http.put(url, {});
    }

    prevLeadStageTransition(leadId: number) {
        const url = `${this.baseUrl}/api/lead/transition/prev/${leadId}`;
        return this.http.put(url, {});
    }

    filterLeadsBySource(source: string): Observable<LeadDTO[]> {
        const url = `${this.baseUrl}/api/lead/search/source?source=${source}`;

        return this.http.get<LeadDTO[]>(url);
    }

    lostLeadTransition(leadId: number): Observable<LeadDTO> {
        const url = `${this.baseUrl}/api/lead/transition/lost/${leadId}`;
        return this.http.put<LeadDTO>(url, {});
    }

    closedWONLeadStageTransition(leadId: number): Observable<LeadDTO> {
        const url = `${this.baseUrl}/api/lead/transition/closedwon/${leadId}`;
        return this.http.put<LeadDTO>(url, {});
    }

}

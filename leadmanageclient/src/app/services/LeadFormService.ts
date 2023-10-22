import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LeadCaptureDTO } from '../model/LeadCaptureDTO';
import { ContactInfo } from '../model/ContactInfo';
import { LeadDTO } from '../model/LeadDTO';

@Injectable({
    providedIn: 'root'
})
export class LeadFormService {
    constructor(private http: HttpClient) { }

    private baseUrl = `http://localhost:8080/leads`;

    captureLead(leadCapture: LeadCaptureDTO): Observable<LeadCaptureDTO> {
        const url = `${this.baseUrl}/create/capture`;
        return this.http.post<LeadCaptureDTO>(url, leadCapture);
    }

    createLeadContact(capturedId: number, leadContactDTO: ContactInfo): Observable<ContactInfo> {
        const url = `${this.baseUrl}/contactInfo/${capturedId}`;
        return this.http.post<ContactInfo>(url, leadContactDTO);
    }

    createLead(leadId: number, leadDTO: LeadDTO): Observable<LeadDTO> {
        const url = `${this.baseUrl}/create/${leadId}`;
        return this.http.post<LeadDTO>(url, leadDTO);
    }

    updateLead(leadId: number, leadDTO: LeadDTO): Observable<LeadDTO> {
        const url = `${this.baseUrl}/lead/update/${leadId}`;
        return this.http.put<LeadDTO>(url, leadDTO);
    }

    patchLead(leadId: number, leadDTO: any): Observable<LeadDTO> {
        const url = `${this.baseUrl}/lead/patch/${leadId}`;
        return this.http.patch<LeadDTO>(url, leadDTO);
    }

    getUsers() {
        const url = `${this.baseUrl}/allusers`;
        return this.http.get<any[]>(url);
    }

    getAllLeadContacts(): Observable<ContactInfo[]> {
        const url = `${this.baseUrl}/allcontacts`;
        return this.http.get<ContactInfo[]>(url);
    }
}

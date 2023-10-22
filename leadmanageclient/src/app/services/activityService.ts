import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ActivityService {
    private baseUrl = 'http://localhost:8080/api/activities'; // Replace with your API URL

    constructor(private http: HttpClient) { }

    getAllLogs(capturedLeadId: number): Observable<any> {
        return this.http.get(`${this.baseUrl}/log/${capturedLeadId}`);
    }
}

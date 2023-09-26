import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root' // Register the service at the root level
})
export class CommentService {
    private apiUrl = 'http://localhost:8080/forum/addComment'; // Replace with your API endpoint

    constructor(private http: HttpClient) { }

    createComment(commentData: any): Observable<any> {
        return this.http.post(this.apiUrl, commentData, { responseType: 'text' });
    }
}

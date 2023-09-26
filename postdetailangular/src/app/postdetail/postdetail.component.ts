import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User, UserService } from '../UserService';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentService } from '../CommentService';

@Component({
  selector: 'app-postdetail',
  templateUrl: './postdetail.component.html',
  styleUrls: ['./postdetail.component.css']
})
export class PostdetailComponent implements OnInit {
  postId: number = 0;
  postDto: any | null = null;
  commentContent: string = '';
  suggestionsFetched: boolean = false;

  commentForm: FormGroup = new FormGroup({});
  showUserSuggestions = false;
  userSuggestions: User[] = [];

  private baseUrl = 'http://localhost:8080/forum/post';

  constructor(private http: HttpClient, private route: ActivatedRoute, private datePipe: DatePipe, private fb: FormBuilder, private userService: UserService, private commentService: CommentService) {
    this.commentForm = this.fb.group({
      commentText: ['', Validators.required]
    });
  }

  //get post data by id
  getPostById(postId: number) {
    const url = `${this.baseUrl}/${postId}`;
    return this.http.get(url);
  }

  ngOnInit(): void {
    const postIdParam = this.route.snapshot.paramMap.get('id');
    if (postIdParam !== null && postIdParam !== undefined && !isNaN(+postIdParam)) {
      this.postId = +postIdParam;
      this.getPostById(this.postId).subscribe({
        next: (data) => {
          this.postDto = data || null;
        },
        error: (error) => {
          console.error('Error fetching post data:', error);
        }
      });
    } else {
      console.error('Invalid post ID in the URL:', postIdParam);
    }
  }

  fetchUserSuggestions(usernameInitial: string) {
    this.userService.getUsers().subscribe(users => {
      this.userSuggestions = users.filter(user => {
        // Assuming 'name' is the property containing the user's name
        return user.name.toLowerCase().startsWith(usernameInitial.toLowerCase());
      });
      this.suggestionsFetched = true;
      this.showUserSuggestions = this.userSuggestions.length > 0;
    });
  }

  onCommentInputChange(event: Event) {
    const inputValue = (event.target as HTMLInputElement).value;

    // Check if "@" is typed in the textarea
    if (inputValue.includes('@')) {
      // Extract the text after "@" for suggestions
      const usernamePartial = inputValue.split('@').slice(-1)[0].trim();

      // Fetch user suggestions only if they haven't been fetched yet
      if (!this.suggestionsFetched && usernamePartial.length > 0) {
        this.fetchUserSuggestions(usernamePartial);
      } else {
        // Show existing suggestions without making another API call
        this.showUserSuggestions = this.userSuggestions.length > 0;
      }
    } else {
      this.showUserSuggestions = false;
    }
  }

  insertSuggestion(user: User) {
    // Insert the selected user suggestion into the comment box
    const commentTextControl = this.commentForm.get('commentText');
    if (commentTextControl) {
      const commentText = commentTextControl.value;
      const username = user.name;

      // Check if the username is already partially or fully present in the comment text
      if (!commentText.match(/\B@[\w\s]*$/)) {
        // Add the tagged user to the comment box
        commentTextControl.setValue(`${commentText ? commentText + ' ' : ''}@${username} `);
      } else {
        // Replace the last mention if it starts with @
        const updatedCommentText = commentText.replace(/\B@[\w\s]*$/, `@${username} `);
        commentTextControl.setValue(updatedCommentText);
      }
    }

    // Clear the user suggestions and show suggestions if "@" is typed again
    this.userSuggestions = [];
    this.suggestionsFetched = false;
  }

  submitComment() {

    const commentText = this.commentForm.get('commentText')!.value;
    const postId = 1; // Replace with the actual post ID

    const commentData = {
      content: commentText,
      postId: postId,
      // userId: userId
    };

    this.commentService.createComment(commentData).subscribe(
      response => {
        console.log('Comment created successfully', response);
        this.commentForm.reset();
      },
      error => {
        console.error('Failed to create comment', error);
      }
    );
  }

}
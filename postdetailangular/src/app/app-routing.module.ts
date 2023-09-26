import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostdetailComponent } from './postdetail/postdetail.component';

const routes: Routes = [
  { path: 'postdetail/:id', component: PostdetailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

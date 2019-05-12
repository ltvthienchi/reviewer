import {Component, Input, OnInit} from '@angular/core';
import {HttpService} from '../../../services/http/http.service';
import * as $ from 'jquery';
@Component({
  selector: 'app-comment-post',
  templateUrl: './comment-post.component.html',
  styleUrls: ['./comment-post.component.css']
})
export class CommentPostComponent implements OnInit {

  @Input() idProduct;
  isCheck:boolean;
  arrTest:any = [];
  dataComment:any = [];
  valueComment:any;
  userInfo:any;
  constructor(private http: HttpService) { }

  ngOnInit() {
    this.getData();
    this.http.getReviewerInfo(localStorage.getItem('email')).subscribe(res => {
      this.userInfo = res;
    });
  }

  getData() {
    this.http.getCommentByProduct(this.idProduct).subscribe(res => {
      this.arrTest = res;
      const arr = [];
      this.arrTest.forEach(itemOne => {
        if(!itemOne.reply){
          itemOne['children'] = [];
          arr.push(itemOne);
        } else {
          this.arrTest.find(itemTwo => {
            if(itemOne.idReply === itemTwo.idComment) {
              itemTwo.children.push(itemOne);
            }
          })
        }

      });
      this.dataComment = arr;
      if (this.dataComment.length < 3) {
        this.isCheck = true;
      } else {
        this.isCheck = false;
      }
    })
  }

  replyComment(item) {
    const id = '#reply-'+item.idComment;
    $('.reply-comment').hide();
    $(id).css('display', 'block');
  }

  postComment(item) {
    if (this.valueComment) {
      let newComment = {
        idProduct: item.idProduct,
        idReviewer: localStorage.getItem('idUser'),
        idReply: item.idComment,
        isReply: true,
        role_user: localStorage.getItem('role'),
        content: this.valueComment,
        dateCreate: new Date()
      };
      console.log(newComment);
      this.http.createComment(newComment).subscribe(res => {
        this.valueComment = '';
        $('.reply-comment').hide();
        this.getData();
      });
    }
  }

  showAllComment() {
    const id = '#btnShowAllComment'+this.idProduct;
    $(id).click();
    this.isCheck = true;
  }

  isRouterName(id) {
    const checkRole = id.split('_')[0] !== 'REVIEWER';
    if (checkRole) {
      return '/company/' + id;
    } else {
      return '/user-page/' + id;
    }
  }

}
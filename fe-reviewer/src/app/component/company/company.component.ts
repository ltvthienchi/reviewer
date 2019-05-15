import { Component, OnInit } from '@angular/core';
import { arrPostProduct } from '../../services/local_database/post-product';
import {AuthGuardService} from '../../services/auth/auth-guard.service';
import {HttpService} from '../../services/http/http.service';
import {ActivatedRoute} from '@angular/router';
import * as $ from 'jquery';
import {IdUserService} from '../../services/data-global/id-user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {

  private idUser;
  private idCompany: string;
  private detailCompany: any = {
    idCompany: '',
    nameCompany: '',
    addrCompany: '',
    webCompany: '',
    telCompany: '',
    imgAvatarCompany: '',
    imgPanelCompany: '',
    emailCompany: '',
    avgRatingComp: 0
  };
  private lstPost = [];
  isPostProduct = false;
  txtPostProduct = 'Post Product';

  constructor(private authGuard: AuthGuardService, private http: HttpService, private activatedRoute: ActivatedRoute,
              private idUserSer: IdUserService) { }

  ngOnInit() {
    $(document).ready(function () {
      $('html,body').animate({ scrollTop: 0 }, 'normal');
    });
    this.idCompany = this.activatedRoute.snapshot.paramMap.get('id');
    this.idUser = this.idUserSer.getId();
    this.getData();
  }

  getData() {
    this.http.getDetailCompany(this.idCompany).subscribe( (data: any) => {
      if (data) {
        this.detailCompany = data.company;
        data.lstPost.map(item => {
          item.idReviewer = this.idUser;
          this.lstPost.push(item);
        });
      }
    });
  }

  checkAuthGuard() {
    return this.authGuard.checkLogin();
  }

  isCompanyAccount() {
    return localStorage.getItem('role') === 'ROLE_COMPANY';
  }

  changeIsPostProduct() {
    if (!this.isPostProduct) {
      this.isPostProduct = true;
      this.txtPostProduct = 'Show timeline';
    } else {
      this.isPostProduct = false;
      this.txtPostProduct = 'Post Product';
      this.http.getDetailCompany(this.idCompany).subscribe( (data: any) => {
        this.lstPost = [];
        if (data) {
          this.detailCompany = data.company;
          data.lstPost.map(item => {
            item.idReviewer = this.idUser;
            this.lstPost.push(item);
          });
        }
      });
    }
  }
}

import { Component, OnInit } from '@angular/core';
import {arrPostProduct} from '../../../services/local_database/post-product';
import {AuthGuardService} from '../../../services/auth/auth-guard.service';
import {HttpService} from '../../../services/http/http.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {
  value = 5;
  max = 5;
  min = 0.5;
  step = 0.5;

  myData = arrPostProduct;
  newPost = {
    fileImage: null,
    nameProduct: 'My test',
    contentPost: 'My test',
    emailCompany: 'My test',
    infoBattery: {
      capacity: 'My test',
      type: 'My test'
    },
    infoDisplay: {
      type: 'My test',
      size: 'My test',
      resolution: 'My test',
    },
    infoPerformance: {
      platform: {
        os: 'My test',
        chip: 'My test',
        cpu: 'My test',
        gpu: 'My test'
      },
      memory: {
        card: 'My test',
        internal: 'My test'
      }
    },
    infoDesign: {
      dimensions: 'My test',
      weight: 'My test',
    },
    infoCamera: {
      main: {
        modules: 'My test',
        features: 'My test',
        video: 'My test'
      },
      self: {
        modules: 'My test',
        features: 'My test',
        video: 'My test'
      }
    }
  };
  fileToUpload: File = null;
  constructor(private authGuard: AuthGuardService, private http: HttpService) { }

  ngOnInit() {
  }

  checkAuthGuard() {
    return this.authGuard.checkLogin();
  }

  isCompanyAccount() {
    return localStorage.getItem('role') === 'ROLE_COMPANY';
  }

  uploadContent(): void {
    this.newPost.fileImage = this.fileToUpload;
    this.http.uploadImage(this.newPost).subscribe((data: any) => {
      console.log(data);
    })

  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

}

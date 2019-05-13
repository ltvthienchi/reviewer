import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {AuthGuardService} from '../../../services/auth/auth-guard.service';
import {NotifierService} from 'angular-notifier';
import * as $ from 'jquery';
import {MatDialog} from '@angular/material';
import {ModalRatingComponent} from '../modal-rating/modal-rating.component';
import {Product} from '../../../model/product.model';
import {DataService} from '../../../services/data-service/data.service';
import {HttpService} from '../../../services/http/http.service';
import {rating} from '../../../model/rating.model';
import {TopRatingService} from '../../../services/data-global/top-rating.service';

@Component({
  selector: 'app-content-post',
  templateUrl: './content-post.component.html',
  styleUrls: ['./content-post.component.css']
})
export class ContentPostComponent implements OnInit {

  @Input() item: any;
  valueComment = '';
  curItemReply;
  dataDetail = {
    infoBattery: {
      capacity: '',
      type: ''
    },
    infoDisplay: {
      type: '',
      size: '',
      resolution: '',
    },
    infoPerformance: {
      platform: {
        os: '',
        chip: '',
        cpu: '',
        gpu: ''
      },
      memory: {
        card: '',
        internal: ''
      }
    },
    infoDesign: {
      dimensions: '',
      weight: '',
    },
    infoCamera: {
      main: {
        modules: '',
        features: '',
        video: ''
      },
      self: {
        modules: '',
        features: '',
        video: ''
      }
    }
  };
  userInfo;
  // newItem = {
  //   idPostProduct: null,
  //   idProduct: null,
  //   idCompany: null,
  //   nameCompany: null,
  //   content: null,
  //   imgPost: '',
  //   avatarCompany: '',
  //   avgDisplay: 1,
  //   avgPerformance: 1,
  //   avgCamera: 1,
  //   avgBattery: 1,
  //   avgDesign: 1,
  //   totalComment: 1,
  //   infoBattery: '',
  //   infoDisplay: '',
  //   infoPerformance: '',
  //   infoDesign: '',
  //   infoCamera: '',
  //   dtCreated: null,
  //   idReviewer: ''
  // };

  constructor(private authGuard: AuthGuardService, public dialog: MatDialog, private topRating: TopRatingService,
              private notifier: NotifierService, private data: DataService, private http: HttpService) {
  }

  ngOnInit() {
    this.http.getReviewerInfo(localStorage.getItem('email')).subscribe(res => {
      this.userInfo = res;
    });
  }

  checkAuthGuard() {
    return this.authGuard.checkLogin();
  }

  toggleRatings(idProduct, idReviewer) {
    const dataRating = {
      idProduct: idProduct,
      idReviewer: idReviewer,
      dtCreated: new Date(),
      rtBattery: 1,
      rtDisplay: 1,
      rtPerformance: 1,
      rtDesign: 1,
      rtCamera: 1
    };
    const dialogRef = this.dialog.open(ModalRatingComponent, {
      width: '450px',
      data: dataRating
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.http.createRating(result).subscribe(res => {
          console.log(res);
          if (res.status === 'SUCCESS') {
            this.http.getProductById(this.item).subscribe(myProduct => {
              this.item.avgBattery = myProduct.avgBattery;
              this.item.avgCamera = myProduct.avgCamera;
              this.item.avgDesign = myProduct.avgDesign;
              this.item.avgDisplay = myProduct.avgDisplay;
              this.item.avgPerformance = myProduct.avgPerformance;
              this.showNotification('success', 'Thank you for your rating!');
              this.topRating.fire();
            });
          } else {
            this.showNotification('error', 'Your rating error, please try again!');
          }
        });
      }
    });
  }

  compareProduct(item: any) {
    item.infoBattery = JSON.parse(item.infoBattery);
    item.infoDisplay = JSON.parse(item.infoDisplay);
    item.infoPerformance = JSON.parse(item.infoPerformance);
    item.infoDesign = JSON.parse(item.infoDesign);
    item.infoCamera = JSON.parse(item.infoCamera);
    const content = {
      'Sub-1': '',
      productName: item.productName,
      imgPost: item.imgPost,
      'Sub-2': '',
      platformOne: item.infoPerformance.platform.os,
      platformTwo: item.infoPerformance.platform.chip,
      platformThree: item.infoPerformance.platform.cpu,
      platformFour: item.infoPerformance.platform.gpu,
      'Sub-3': '',
      memoryOne: item.infoPerformance.memory.card,
      memoryTwo: item.infoPerformance.memory.internal,
      'Sub-4': '',
      designOne: item.infoDesign.dimensions,
      designTwo: item.infoDesign.weight,
      'Sub-5': '',
      displayOne: item.infoDisplay.type,
      displayTwo: item.infoDisplay.size,
      displayThree: item.infoDisplay.resolution,
      'Sub-6': '',
      batteryOne: item.infoBattery.capacity,
      batteryTwo: item.infoBattery.type,
      'Sub-7': '',
      cameraMainOne: item.infoCamera.main.modules,
      cameraMainTwo: item.infoCamera.main.features,
      cameraMainThree: item.infoCamera.main.video,
      'Sub-8': '',
      cameraSelfOne: item.infoCamera.self.modules,
      cameraSelfTwo: item.infoCamera.self.features,
      cameraSelfThree: item.infoCamera.self.video
    };
    const product = {
      idPostProduct: item.idPostProduct,
      avgBattery: item.avgBattery,
      avgCamera: item.avgCamera,
      avgDesign: item.avgDesign,
      avgDisplay: item.avgDisplay,
      avgPerformance: item.avgPerformance,
      content: content
    };
    const lstPost = [];
    const sessionString = sessionStorage.getItem('lstProduct');
    const lstJson = JSON.parse(sessionString);
    if (lstJson === null) {
      lstPost.push(product);
      this.data.increaseProduct();
      const stringJson = JSON.stringify(lstPost);
      sessionStorage.setItem('lstProduct', stringJson);
    } else {
      if (!this.checkExistProductId(lstJson, product.idPostProduct)) {
        if (lstJson.length > 0 && lstJson.length < 3) {
          // const productString = JSON.stringify(product);
          this.data.increaseProduct();
          lstJson.push(product);
          const stringJson = JSON.stringify(lstJson);
          sessionStorage.setItem('lstProduct', stringJson);
        } else {
          this.showNotification('error', 'Maximum Product to compare is 3!!');
        }
      } else {
        this.showNotification('error', 'Product is existing!!');
      }

    }
    // sessionStorage.setItem('countProduct', this.count.toString());
  }

  showNotification(type: string, message: string): void {
    this.notifier.notify(type, message);
  }

  checkExistProductId(lstJson, idPostProduct) {
    for (const i in lstJson) {
      if (lstJson[i].idPostProduct === idPostProduct) {
        return true;
      }
    }
    return false;
  }

  detailProduct(item) {
    console.log(item);
    this.dataDetail = {
      infoBattery: JSON.parse(item.infoBattery),
      infoDisplay: JSON.parse(item.infoDisplay),
      infoPerformance: JSON.parse(item.infoPerformance),
      infoDesign: JSON.parse(item.infoDesign),
      infoCamera: JSON.parse(item.infoCamera)
    };
  }

  postComment() {
      if (this.valueComment) {
        let newComment = {
          idProduct: this.item.idProduct,
          idReviewer: localStorage.getItem('idUser'),
          idReply: null,
          isReply: false,
          role_user: localStorage.getItem('role'),
          content: this.valueComment,
          dateCreate: new Date()
        };
        console.log(newComment);
        this.http.createComment(newComment).subscribe(res => {
          console.log(res);
          const id = '#reload_'+this.item.idProduct;
          $(id).click();
          this.valueComment = '';
        });
      }
  }

  showComment(idProduct) {
    const id = '#container-comment-'+idProduct;
    $(id).toggle();
  }
}

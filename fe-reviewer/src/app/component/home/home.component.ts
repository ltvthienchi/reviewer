import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { Broadcaster } from '../../services/broadcaster/broadcaster.service';
import { EventMessage } from '../../services/event_message/event-message.service';
import * as $ from 'jquery';

import { arrPostProduct } from '../../services/local_database/post-product';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  encapsulation: ViewEncapsulation.None,
  preserveWhitespaces: false
})
export class HomeComponent implements OnInit {

  private notifier: NotifierService;
  toggleButton: boolean = false;
  toggleRating = true;

  invert = false;
  max = 10;
  min = 1;
  step = 1;
  thumbLabel = true;
  value = 1;

  myData = arrPostProduct;

  public constructor(notifier: NotifierService, private broad: Broadcaster, private eventMessage: EventMessage) {
      this.notifier = notifier;
  }

  ngOnInit() {

  }

  toggleRatings(id) {
    console.log(id);
    if (this.toggleRating) {
      let idItem = '#' + id;
      $('.container-ratting').css('display', 'inline');
      let offsetLeft = $('#quang-cao').offset()['left'] - 30;
      let offsetTop = $(idItem).offset()['top'] ;
      let width = $('#quang-cao').width();
      $('.container-ratting').offset({top: offsetTop, left: offsetLeft}).width(width + 30);
      console.log(offsetLeft, offsetTop);
    } else {
      $('.container-ratting').css('display', 'none');
    }
    this.toggleRating = !this.toggleRating
  }

  sendMess() {
    this.toggleButton = !this.toggleButton;
    let arr = this.toggleButton ? [1,2,3,4,5] : [];
    this.eventMessage.fire(this.toggleButton);
  }

  public showNotification(type: string, message: string): void {
    this.notifier.notify(type, message);
  }


  public hideOldestNotification(): void {
    this.notifier.hideOldest();
  }

  public hideNewestNotification(): void {
    this.notifier.hideNewest();
  }

  public hideAllNotifications(): void {
    this.notifier.hideAll();
  }

  public showSpecificNotification(type: string, message: string, id: string): void {
    this.notifier.show({id, message, type});
  }

  public hideSpecificNotification(id: string): void {
    this.notifier.hide(id);
  }

}

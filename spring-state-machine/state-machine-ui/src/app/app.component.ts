import{InterceptDialogComponent}from'./shared/intercept-dialog/intercept-dialog.component';
import {MatDialog}from '@angular/material';
import {Component, ViewChild, Renderer2, ElementRef, OnInit}from '@angular/core';
import {LoadingService}from './services/visuals/loading.service';
import {RxStompService}from '@stomp/ng2-stompjs';
import {Subscription}from 'rxjs';
import {ResourcesService}from 'src/app/services/resources.service';
import * as shape from 'd3-shape';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { StateMachineDataModel } from './models/statemachine-data-model';

@Component({
selector: 'app-root',
templateUrl: './app.component.html',
styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  hierarchialGraph = {nodes: [], links: []}
  curve = shape.curveBundle.beta(1);
@ViewChild('logAndSettings') logAndSettings: ElementRef;
@ViewChild('log') log: ElementRef;
@ViewChild('state') state: ElementRef;

isResizing = false;
startHeight: number;
startMouseY: number;
logLevel = null;
shouldScrollLogToBottom = false;
logRows = [];
infoLogRows = [];
debugLogRows = [];
errorLogRows = [];
states = 'SUCCESS';

interceptOutgoingMessagesCheckbox = false;
interceptIncomingMessagesCheckbox = false;

interceptOutgoingSubscription: Subscription;
interceptIncomingSubscription: Subscription;

softMobileAppUrl: string;

constructor(private renderer: Renderer2, public loadingService: LoadingService, private rxStompService: RxStompService, private resourcesService: ResourcesService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.logLevel= 'all';
      this.rxStompService.watch('/topic/state').subscribe(message => {
       let jsonObj: any = JSON.parse(message.body); 
       let data: StateMachineDataModel = <StateMachineDataModel>jsonObj
       this.showGraph(data);
     });
    this.rxStompService.watch('/topic/log-rows').subscribe(message => {
      this.shouldScrollLogToBottom = this.log.nativeElement.scrollHeight - this.log.nativeElement.scrollTop - this.log.nativeElement.clientHeight <= 100;
      this.logRows = this.logRows.concat(JSON.parse(message.body));
    });

    new MutationObserver(() => {
      if (this.shouldScrollLogToBottom) {
        this.scrollLogToBottom();
      }
    }).observe(this.log.nativeElement, {childList: true});

  }

  displayLogLevel(value: string){
    switch(value) {
      case "all":
        this.displayAllLog();
         break;
      case "debug":
        this.displayDebugLevelLog();
         break;
      case "info":
        this.displayInfoLevelLog();
         break;
      case "error":
        this.displayErrorLevelLog();
         break;
    }
  }

  displayAllLog(){
    this.logLevel = "all"
  }

  displayDebugLevelLog(){
    this.logLevel = "debug"
    for (let entry of this.logRows) {
      if(entry.includes(" DEBUG ") || entry.includes(" INFO ") || entry.includes(" ERROR ")){ 
        this.debugLogRows = this.debugLogRows.concat(entry);
      }
    }
  }

  displayInfoLevelLog(){
    this.logLevel = "info"
    for (let entry of this.logRows) {
         if(entry.includes(" INFO ") || entry.includes(" ERROR ")){  
            this.infoLogRows = this.infoLogRows.concat(entry);
        }
      }
    }

  displayErrorLevelLog(){
    this.logLevel = "error"
    for (let entry of this.logRows) {
      if(entry.includes(" ERROR ")){ 
        this.errorLogRows = this.errorLogRows.concat(entry);
      }
    }
  }

  deleteAllLogRows() {
    this.logRows = [];
    this.debugLogRows = [];
    this.infoLogRows = [];
    this.errorLogRows = [];
  }

  scrollLogToTop() {
    this.log.nativeElement.scrollTop = 0;
  }

  scrollLogToBottom() {
    this.log.nativeElement.scrollTop = this.log.nativeElement.scrollHeight;
  }

  startResize(event: MouseEvent) {
    this.startHeight = this.logAndSettings.nativeElement.offsetHeight;
    this.startMouseY = event.clientY;
    this.isResizing = true;
  }

  onResize(event: MouseEvent) {
    if (this.isResizing) {
      const mouseMovement = this.startMouseY - event.clientY;
      const newHeight = Math.max(this.startHeight + mouseMovement, 100) + 'px';

      this.renderer.setStyle(this.logAndSettings.nativeElement, 'height', newHeight);

      event.preventDefault();
    }
  }

  stopResize() {
    this.isResizing = false;
  }

  showGraph(data: StateMachineDataModel) {
    this.hierarchialGraph.nodes = [
  {
    id: '0',
    label: data.source,
    position: 'x0'
  }, {
    id: '1',
    label: data.target,
    position: 'x1'
  }
  ];
  this.hierarchialGraph.links = [
  {
    source: '0',
    target: '1',
    label: data.event,
  }
  ];

  }

}

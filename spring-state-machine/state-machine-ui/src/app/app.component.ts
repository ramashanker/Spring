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
import { GraphNode } from './models/graph-node';
import { GraphLink } from './models/graph-link';

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
graphNodes: Array<GraphNode> = [];
graphLinks: Array<GraphLink> = [];

graphNodeos= [];
graphLinkos= [];

interceptOutgoingMessagesCheckbox = false;
interceptIncomingMessagesCheckbox = false;

interceptOutgoingSubscription: Subscription;
interceptIncomingSubscription: Subscription;

softMobileAppUrl: string;

constructor(private renderer: Renderer2, public loadingService: LoadingService, private rxStompService: RxStompService, private resourcesService: ResourcesService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.logLevel= 'all';
      this.rxStompService.watch('/topic/state').subscribe(message => {
      //  let jsonObj: any = JSON.parse(message.body); 
      //  let data: StateMachineDataModel = <StateMachineDataModel>jsonObj
      let statusData: any =  JSON.parse(message.body);
      let data: StateMachineDataModel[] = <StateMachineDataModel[]>statusData;
      this.populateNodeAndLink(data);
      this.displayGraph(data);
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

  // displayGraph(data:  StateMachineDataModel[] ) {
  //   let index = 0;
  //   // for (let entry of data) {
  //   //   let nextIndex= index+1;
  //     this.hierarchialGraph.nodes = [
  //      {
  //     //       id: '0',
  //     //       label: data[0].source,
  //     //       position: 'x0'
  //     // }, {
  //     //       id: '1',
  //     //       label: data[0].target,
  //     //       position: 'x1'
  //     // }
  //     this.graphNodes
  //   ];
  //     this.hierarchialGraph.links = [
  //     {
  //       source:  '0',
  //       target:  '1',
  //       label: data[0].event,
  //     }
  //   ];
  //   // index++;
  //   // }
  // }

  displayGraph(data:  StateMachineDataModel[] ) {
      this.hierarchialGraph.nodes =  this.graphNodeos;
      this.hierarchialGraph.links = this.graphLinkos;
  }

//   populateNodeAndLink(data:  StateMachineDataModel[]) {
//     let index = 0;
//     let nextIndex=1;
//     for (let entry of data) {

//       let node = new GraphNode();
//       node.id=`${index}`;
//       node.label=entry.source;
//       node.position=`x${index}`;

//       let link= new GraphLink();
//       link.source=`${index}`;
//       link.target=`${nextIndex}`;
//       link.label=entry.event;
     
//       // let node = {id:`${index}`, label: entry.source, position: `x${index}`};
//       // let link=  {source: `${index}`, target: `${nextIndex}`, label: entry.event};
//       this.graphNodes.push(node);
//       this.graphLinks.push(link);
//   }
// }

populateNodeAndLink(data:  StateMachineDataModel[]) {
  let srcindex = 0;
  let dstIndex=1;
  this.graphNodeos=[];
  this.graphLinkos=[];
  for (let entry of data) {
    let nodesrc = {id:`${srcindex}`, label: entry.source, position: `x${srcindex}`};
    let nodedest = {id:`${dstIndex}`, label: entry.target, position: `x${dstIndex}`};
    let link=  {source: `${srcindex}`, target: `${dstIndex}`, label: entry.event};
    this.graphNodeos.push(nodesrc);
    this.graphNodeos.push(nodedest);
    this.graphLinkos.push(link);
    srcindex++;
    dstIndex++;
  }
}
  

}

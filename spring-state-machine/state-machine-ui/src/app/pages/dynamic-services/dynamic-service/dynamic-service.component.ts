import{Component, OnInit, HostListener}from '@angular/core';
import {ActivatedRoute}from '@angular/router';
import {Subscription} from 'rxjs';
import {RxStompService} from '@stomp/ng2-stompjs';
import {DynamicServiceService}from 'src/app/services/dynamic-service.service';
import {ResourcesService }from 'src/app/services/resources.service';
import * as d3 from 'd3-graphviz';
import {RestFunction}from 'src/app/models/rest-function.model';

@Component({
selector: 'app-dynamic-service',
templateUrl: './dynamic-service.component.html',
styleUrls: ['./dynamic-service.component.scss']
})
export class DynamicServiceComponent implements OnInit {

restFunctions: RestFunction[];

service: string;

stateMachineGraphSubscription: Subscription;

constructor(private route: ActivatedRoute, private rxStompService: RxStompService, private resourcesService: ResourcesService, private dynamicServiceService: DynamicServiceService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(paramMap => {
      this.service = paramMap.get('service');
    });
  }

  initiate() {
    if (this.service) {
      this.dynamicServiceService.getDynamicServiceSpecification(this.service).subscribe(dynamicServiceSpecification => {
        this.restFunctions = Object.entries(dynamicServiceSpecification.name).map(([name, urls]) => {
          return {
            onClick: () => this.dynamicServiceService.triggerExposedEvent(this.service, name),
            buttonLabel:name,
            description: urls
          };
        });
      });
    }
  }

}

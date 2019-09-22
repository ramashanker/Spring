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
        this.restFunctions = Object.entries(dynamicServiceSpecification.exposedEventTriggers).map(([name, exposedEventTrigger]) => {
          return {
            onClick: () => this.dynamicServiceService.triggerExposedEvent(this.service, name),
            buttonLabel: exposedEventTrigger.buttonLabel,
            description: exposedEventTrigger.description
          };
        });
      });

      // #################

      document.querySelector('#graph > div').remove();
      document.querySelector('#graph').appendChild(document.createElement('div'));

      this.stateMachineGraphSubscription = this.rxStompService.watch(`/topic/dynamic-service/state-machine-graph`).subscribe(message => {
        this.renderStateMachineGraph(message.body);
      });
    }
  }

  renderStateMachineGraph(stateMachineGraph: string) {
    d3.graphviz('#graph > div').renderDot(stateMachineGraph);
  }

  @HostListener('window:showStateMachineState', ['$event.detail'])
  showStateMachineState(detail) {
    console.log(atob(detail));
  }

}

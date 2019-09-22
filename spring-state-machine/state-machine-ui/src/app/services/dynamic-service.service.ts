import{Injectable}from'@angular/core';
import {AppConstants }from '../app.constants';
import {HttpClient}from '@angular/common/http';
import {ResourcesService }from './resources.service';
import {Observable}from 'rxjs';
import {DynamicServiceSpecification}from '../models/dynamic-service-specification';

@Injectable({
providedIn: 'root'
})
export class DynamicServiceService {

constructor(private httpClient: HttpClient, private resourcesService: ResourcesService) { }

  getDynamicServiceSpecification(service: string): Observable<DynamicServiceSpecification> {
    return this.httpClient.get<DynamicServiceSpecification>(`${AppConstants.API_ENDPOINT}/dynamic-service/${service}/dynamic-service-specification`);
  }

 
  triggerExposedEvent(service: string, name: string) {
    return this.httpClient.post(`${AppConstants.API_ENDPOINT}/dynamic-service/${service}/trigger-exposed-event`, name).subscribe();
  }
}

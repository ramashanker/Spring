import{Injectable}from'@angular/core';
import {HttpClient }from '@angular/common/http';
import {Observable, Subject}from 'rxjs';
import {AppConstants}from '../app.constants';
import { DynamicServiceSpecification } from '../models/dynamic-service-specification';

@Injectable({
providedIn: 'root'
})
export class ResourcesService {

constructor(private httpClient: HttpClient) { }

  getAvailableDynamicServices(): Observable<DynamicServiceSpecification[]> {
    return this.httpClient.get<DynamicServiceSpecification[]>(`${AppConstants.API_ENDPOINT}/resources/available-dynamic-services`);
  }
}

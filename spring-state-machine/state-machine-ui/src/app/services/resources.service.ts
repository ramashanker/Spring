import{Injectable}from'@angular/core';
import {HttpClient }from '@angular/common/http';
import {Observable, Subject}from 'rxjs';
import {AppConstants}from '../app.constants';

@Injectable({
providedIn: 'root'
})
export class ResourcesService {

private selectedCar: string;
private selectedCarSubject = new Subject<string>();

selectedCar$ = this.selectedCarSubject.asObservable();

constructor(private httpClient: HttpClient) { }

  getAvailableDynamicServices(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${AppConstants.API_ENDPOINT}/resources/available-dynamic-services`);
  }
}

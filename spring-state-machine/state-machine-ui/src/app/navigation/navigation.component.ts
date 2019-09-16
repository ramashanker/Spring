import{Component, OnInit, ElementRef, ViewChild}from '@angular/core';
import {ResourcesService}from '../services/resources.service';

@Component({
selector: 'app-navigation',
templateUrl: './navigation.component.html',
styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

navLinks = [
{path: 'dynamic-services', label: 'Dynamic Services'
}
];

@ViewChild('searchInput') searchInput: ElementRef;

searchText: string;

constructor(private resourcesService: ResourcesService) { }

  ngOnInit() {
  
  }

  selectedCarOpenedChange(opened: boolean) {
    if (opened) {
      this.searchInput.nativeElement.focus();
    }
  }

}

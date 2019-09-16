import { RestFunction } from './../../models/rest-function.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-rest-functions-list',
  templateUrl: './rest-functions-list.component.html',
  styleUrls: ['./rest-functions-list.component.scss']
})
export class RestFunctionsListComponent implements OnInit {

  @Input() restFunctions: RestFunction[];

  constructor() { }

  ngOnInit() {
  }

}

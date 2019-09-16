import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import * as vkbeautify from 'vkbeautify';

@Component({
  selector: 'app-intercept-dialog',
  templateUrl: './intercept-dialog.component.html',
  styleUrls: ['./intercept-dialog.component.scss']
})
export class InterceptDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public message: any) {
    message.headerXml = vkbeautify.xml(message.headerXml);
    message.messageXml = vkbeautify.xml(message.messageXml);
  }

  ngOnInit() {
  }

}

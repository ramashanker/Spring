import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-http-error-dialog',
  templateUrl: './http-error-dialog.component.html',
  styleUrls: ['./http-error-dialog.component.scss']
})
export class HttpErrorDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public error: any) { }

  ngOnInit() {
  }

}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  loading = false;

  constructor() { }

  setLoading(value: boolean) {
    this.loading = value;
  }
}

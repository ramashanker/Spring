import { Injectable } from '@angular/core';
import { LoadingService } from './loading.service';
import { HttpResponse, HttpRequest, HttpHandler, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';
import { MatSnackBar, MatDialog } from '@angular/material';
import { HttpErrorDialogComponent } from 'src/app/shared/http-error-dialog/http-error-dialog.component';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
  private totalRequests = 0;

  constructor(private loadingService: LoadingService, private snackBar: MatSnackBar, private dialog: MatDialog) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    this.totalRequests++;
    this.loadingService.setLoading(true);
    return next.handle(request).pipe(
      tap(res => {
        if (res instanceof HttpResponse) {
          this.decreaseRequests();
        }
      }),
      catchError((err: HttpErrorResponse) => {
        this.decreaseRequests();

        if ('message' in err.error) {
          this.snackBar.open(err.error.status + ': ' + err.error.message, 'Show more', { duration: 10000 }).onAction().subscribe(() => {
            this.dialog.open(HttpErrorDialogComponent, {
              data: err.error
            });
          });
        } else {
          this.snackBar.open(err.status + ': ' + err.statusText, null, { duration: 10000 });
        }

        throw err;
      })
    );
  }

  private decreaseRequests() {
    this.totalRequests--;
    if (this.totalRequests === 0) {
      this.loadingService.setLoading(false);
    }
  }
}

import{BrowserModule}from'@angular/platform-browser';
import {NgModule,CUSTOM_ELEMENTS_SCHEMA }from '@angular/core';
import {AppRoutingModule}from './app-routing.module';
import {AppComponent}from './app.component';
import {BrowserAnimationsModule}from '@angular/platform-browser/animations';
import {FormsModule}from '@angular/forms';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule}from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule}from '@angular/material/icon';
import {MatTabsModule} from '@angular/material/tabs';
import {MatProgressSpinnerModule}from '@angular/material/progress-spinner';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDialogModule}from '@angular/material/dialog';
import {MatExpansionModule} from '@angular/material/expansion';
import {HttpClientModule, HTTP_INTERCEPTORS}from '@angular/common/http';
import {RestFunctionsListComponent}from './shared/rest-functions-list/rest-functions-list.component';
import {CustomHttpInterceptor} from './services/visuals/custom.httpinterceptor';
import {HttpErrorDialogComponent}from './shared/http-error-dialog/http-error-dialog.component';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {rxStompConfig}from './rx-stomp.config';
import {FilterPipe}from './shared/filter.pipe';
import {MatCheckboxModule, MatCardModule}from '@angular/material';
import {InterceptDialogComponent}from './shared/intercept-dialog/intercept-dialog.component';
import {DynamicServicesComponent}from './pages/dynamic-services/dynamic-services.component';
import {DynamicServiceComponent}from './pages/dynamic-services/dynamic-service/dynamic-service.component';
import { NavigationComponent } from './navigation/navigation.component';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
declarations: [
AppComponent,
NavigationComponent,
RestFunctionsListComponent,
HttpErrorDialogComponent,
FilterPipe,
InterceptDialogComponent,
DynamicServicesComponent,
DynamicServiceComponent
],
imports: [
BrowserModule,
HttpClientModule,
AppRoutingModule,
BrowserAnimationsModule,
FormsModule,
MatToolbarModule,
MatSelectModule,
MatButtonModule,
MatIconModule,
MatTabsModule,
MatProgressSpinnerModule,
MatSnackBarModule,
MatDialogModule,
MatExpansionModule,
MatCheckboxModule,
MatCardModule,
NgxGraphModule,
NgxChartsModule
],
providers: [
{
provide: HTTP_INTERCEPTORS,
useClass: CustomHttpInterceptor,
multi: true
},
{
provide: InjectableRxStompConfig,
useValue: rxStompConfig
},
{
provide: RxStompService,
useFactory: rxStompServiceFactory,
deps: [InjectableRxStompConfig]
}
],
schemas: [CUSTOM_ELEMENTS_SCHEMA],
bootstrap: [AppComponent],
entryComponents: [HttpErrorDialogComponent, InterceptDialogComponent]
})
export class AppModule {

}

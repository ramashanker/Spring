import{DynamicServiceComponent}from'./pages/dynamic-services/dynamic-service/dynamic-service.component';
import {NgModule}from '@angular/core';
import {Routes, RouterModule}from '@angular/router';
import {DynamicServicesComponent}from './pages/dynamic-services/dynamic-services.component';

const routes: Routes = [
{
path: 'dynamic-services',
component: DynamicServicesComponent,
children: [
{path: ':service', component: DynamicServiceComponent},
]
}
];

@NgModule({
imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }

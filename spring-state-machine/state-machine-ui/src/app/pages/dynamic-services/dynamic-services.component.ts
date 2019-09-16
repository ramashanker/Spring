import { ResourcesService } from 'src/app/services/resources.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-dynamic-services',
  templateUrl: './dynamic-services.component.html',
  styleUrls: ['./dynamic-services.component.scss']
})
export class DynamicServicesComponent implements OnInit {

  navLinks: { path: string, label: string }[];

  constructor(private route: ActivatedRoute, private router: Router, private resourcesService: ResourcesService) { }

  ngOnInit() {
    this.resourcesService.getAvailableDynamicServices().subscribe(dynamicServices => {
      this.navLinks = dynamicServices.map(dynamicService => {
        return { path: dynamicService, label: dynamicService.toLowerCase().split('_').map((s) => s.charAt(0).toUpperCase() + s.substring(1)).join(' ') };
      });

      if (!this.route.firstChild && this.navLinks.length > 0) {
        this.router.navigate([this.navLinks[0].path],  {relativeTo: this.route });
      }
    });
  }

}

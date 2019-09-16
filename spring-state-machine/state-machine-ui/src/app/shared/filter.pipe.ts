import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(items: string[], filterText: string): string[] {
    if (!items) { return []; }
    if (!filterText) { return items; }

    filterText = filterText.toLowerCase();
    return items.filter(item => item.toLowerCase().includes(filterText));
  }

}

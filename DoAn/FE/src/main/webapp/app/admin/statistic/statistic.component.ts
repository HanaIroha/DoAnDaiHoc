import { Component, OnInit } from '@angular/core';
import { numberSymbols } from '@telerik/kendo-intl';
import { OrderService } from 'app/service/order.service';
import { StaticItem } from './static.model';

@Component({
  selector: 'jhi-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.scss']
})
export class StatisticComponent implements OnInit {
  staticItems: StaticItem[] = [];
  thisYear: number;

  constructor(
    private orderService: OrderService,
  ) { }

  ngOnInit(): void {
    this.thisYear = (new Date()).getFullYear();
    this.loadAll();
  }

  loadAll(): void{
    this.orderService.orderStatic(this.thisYear).subscribe(res=>{
      let a = res.split('|')
      this.staticItems = [];
      for(let b of a){
        let item = b.split('&');
        let itemz = new StaticItem();
        itemz.ordercount = Number(item[0]);
        itemz.itemcount = Number(item[1]);
        itemz.value = Number(item[2]);
        this.staticItems.push(itemz);
      }
    })
  }

}

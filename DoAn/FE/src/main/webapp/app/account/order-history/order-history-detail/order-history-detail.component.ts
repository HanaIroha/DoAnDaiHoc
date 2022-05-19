import { Component, OnInit } from '@angular/core';
import { OrderDetail } from 'app/checkout/orderDetail.model';
import { AccountService } from 'app/core/auth/account.service';
import { ImageService } from 'app/service/image.service';
import { OrderService } from 'app/service/order.service';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'app/service/product.service';
import { Account } from 'app/core/auth/account.model';
import { Order } from 'app/checkout/order.model';

@Component({
  selector: 'jhi-order-history-detail',
  templateUrl: './order-history-detail.component.html',
  styleUrls: ['./order-history-detail.component.scss']
})
export class OrderHistoryDetailComponent implements OnInit {

  defaultSort = "id_order,asc";
  imagePath = "\\content\\imageStorage\\";
  isLoading = false;
  totalItems = 0;
  itemsPerPage = 10;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  orderDetails: OrderDetail[];
  name: string[]=[];
  image: string[]=[];
  allPrice: number;
  order: Order;

  account: Account;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private imageService: ImageService,
    private accountService: AccountService,
    private productService: ProductService,
  ) { }

  ngOnInit(): void {
    this.load();
  }

  load(){
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.allPrice=0;
    let id = this.route.snapshot.paramMap.get('id');
    this.orderService.getOne(id).subscribe(res=>{
      this.order=res;
    })

    this.orderService
      .queryDetails({
        page: this.page - 1,
        size: 9999,
        sort: ['id_order_detail','asc'],
      },id)
      .subscribe({
        next: (res: HttpResponse<OrderDetail[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  private onSuccess(ordersdetail: OrderDetail[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.orderDetails = ordersdetail;
    
    for(let i=0;i<this.orderDetails.length;i++){
      this.productService.getOne(this.orderDetails[i].idProduct).subscribe(res=>{
          this.name.push(res.name);
          this.image.push(res.image);
      });
      this.allPrice=this.allPrice+this.orderDetails[i].price*this.orderDetails[i].quantity;
    }
  }

  previousState():void{
    window.history.back();
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProductService } from 'app/service/product.service';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { Product } from 'app/admin/products/product.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  androidAmount;
  iosAmount;
  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  products: Product[] | null = null;

  defaultSort = "id_product,desc";
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getAmountByOs("Android").subscribe(res=>(this.androidAmount=res));
    this.productService.getAmountByOs("iOS").subscribe(res=>(this.iosAmount=res));
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
      this.loadAll();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadAll(): void {
    this.isLoading = true;
    this.productService
      .queryActive({
        page: 0,
        size: 8,
        sort: [this.defaultSort],
      },0, 0, 0)
      .subscribe({
        next: (res: HttpResponse<Product[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  private onSuccess(products: Product[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.products = products;
  }

  addCart(index):void{
    let sl;
    let cartNumber;
      this.productService.getOne(this.products[index].idProduct).subscribe(res => {
        sl=res;
        if(1<=this.products[index].quantity){
          let data;
          if (!localStorage['cart']) data = [];
          else data = JSON.parse(localStorage['cart']); 
          if (!(data instanceof Array)) data = [];
    
          
          let item = { 'id':this.products[index].idProduct, 'quantity':1};
          let exist = false;
          let sl = 0;
            for (let i = 0; i< data.length; i++){
                if(data[i].id===item.id){
                  data[i].quantity = data[i].quantity + 1;
                  exist = true;
                }
                sl++;
            }
            if(!exist){
              if(!(data instanceof Array))
                data= [data];
              data.push(item);
            }
            cartNumber=sl;
            if(!exist)
              cartNumber++;
            localStorage.setItem('cart',JSON.stringify(data));
            document.getElementById("numberItemCart").innerHTML = cartNumber.toString();
          alert('Thêm thành công ' + 1 + ' ' + this.products[index].name + ' vào giỏ hàng!');
        }
        else{
          alert('Thêm vào giỏ hàng thất bại, trong kho không còn đủ số lượng yêu cầu!');
        }
      });
  }
  
}

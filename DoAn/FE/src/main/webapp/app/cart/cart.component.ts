import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'app/admin/products/product.model';
import { AccountService } from 'app/core/auth/account.service';
import { CommentService } from 'app/service/comment.service';
import { ImageService } from 'app/service/image.service';
import { ProducerService } from 'app/service/producer.service';
import { ProductService } from 'app/service/product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  imagePath = "\\content\\imageStorage\\";
  data = [];
  sl:number;
  allPrice:number;
  

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private producerService: ProducerService,
    private imageService: ImageService,
    private accountService: AccountService,
  ) { }

  ngOnInit(): void {
    this.load();
  }

  load(){
    this.allPrice=0;
    this.sl=0;
    if (!localStorage['cart']) this.data = [];
    else this.data = JSON.parse(localStorage['cart']); 
    if (!(this.data instanceof Array)) this.data = [];

    for (let i = 0; i< this.data.length; i++){
      this.sl++;
      this.productService.getOne(this.data[i].id).subscribe(res=>{
        this.data[i].name = res.name;
        this.data[i].image = res.image;
        this.data[i].price = res.salePercent ? (res.price/100*(100-res.salePercent)) : res.price;
        this.allPrice = this.allPrice + this.data[i].price * this.data[i].quantity;
      });
    }
  }

  increase(index): void{
    this.data[index].quantity++;
    localStorage.setItem('cart',JSON.stringify(this.data));
    this.load();
  }
  decrease(index): void{
    if(this.data[index].quantity>1){
      this.data[index].quantity--;
      localStorage.setItem('cart',JSON.stringify(this.data));
      this.load();
    }
  }
  deleteItem(i):void{
    this.data.forEach((element,index)=>{
      if(index==i) delete this.data[index];
   });
   this.data = this.data.filter(Boolean);
   document.getElementById("numberItemCart").innerHTML = (this.sl-1).toString();
   localStorage.setItem('cart',JSON.stringify(this.data));
      this.load();
  }

}

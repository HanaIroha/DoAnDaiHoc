import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Image } from 'app/admin/products/image/image.model';
import { Product } from 'app/admin/products/product.model';
import { ProductSpec } from 'app/admin/products/productSpec.model';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Login } from 'app/login/login.model';
import { CommentService } from 'app/service/comment.service';
import { ImageService } from 'app/service/image.service';
import { ProducerService } from 'app/service/producer.service';
import { ProductService } from 'app/service/product.service';
import { BuiltInParserName } from 'prettier';
import { Comment } from './comment.model';


@Component({
  selector: 'jhi-shop-detail',
  templateUrl: './shop-detail.component.html',
  styleUrls: ['./shop-detail.component.scss']
})
export class ShopDetailComponent implements OnInit {
  
  product = new Product();
  numberSpec = [];
  images: Image[] | null = null;
  comments: Comment[] | null = null;
  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  producerName;
  public publicVariable = 0;
  account: Account | null = null;
  commentCount;
  listAvt: Array<string>;
  id:string;
  buyAmount:number;

  cartNumber:number;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private producerService: ProducerService,
    private imageService: ImageService,
    private accountService: AccountService,
    private commentService: CommentService,
    ) {}

  ngOnInit(): void {
    this.buyAmount = 1;
    this.listAvt = [ ];

    this.id = this.route.snapshot.paramMap.get('id');
      this.productService.getOne(this.id).subscribe(res => {
        this.product=res;
        this.producerService.getOne(this.product.idProducer).subscribe(res=>{
          this.producerName=res.name;
        })
      });

      this.imageService
      .query({
        page: 0,
        size: 999,
        sort: ["id_image","asc"],
      }, this.id)
      .subscribe({
        next: (res: HttpResponse<Image[]>) => {
          this.images = res.body;
        },
      });

      this.accountService.getAuthenticationState().subscribe(account => {
        this.account = account;
      });

      this.productService
      .querySpecs({
        page: 0,
        size: 9999,
        sort: ['id_product_spec','asc'],
      },this.id)
      .subscribe({
        next: (res: HttpResponse<ProductSpec[]>) => {
          this.numberSpec = res.body;
        },
      });

      this.loadComment();
  }

  loadComment(){
    this.commentService
      .queryByProductId({
        page: 0,
        size: 999,
        sort: ["created_at","asc"],
      }, this.id)
      .subscribe({
        next: (res: HttpResponse<Comment[]>) => {
          this.comments = res.body;
          this.commentCount = this.comments.length;
          for(let o of this.comments){
            this.commentService.getAvatar(o.login.toString()).subscribe(res=>{
              this.listAvt.push(res);
           })
           
          }
        },
      });
  }

  getIndex() : number{
    this.publicVariable++;
    return this.publicVariable;
  }

  comment(content): void{
    let cmt = new Comment();
    cmt.content = content;
    cmt.idProduct = this.product.idProduct;
    cmt.login = this.account.login;
    this.commentService.create(cmt).subscribe(res=>{this.loadComment()});
  }

  increaseBuyAmount(): void{
    if(this.buyAmount<this.product.quantity)
      this.buyAmount++;
  }

  decreaseBuyAmount(): void{
    if(this.buyAmount>1)
      this.buyAmount--;
  }

  addCart():void{
    if(this.buyAmount<=this.product.quantity){
      let data;
      if (!localStorage['cart']) data = [];
      else data = JSON.parse(localStorage['cart']); 
      if (!(data instanceof Array)) data = [];

      
      let item = { 'id':this.product.idProduct, 'quantity':this.buyAmount};
      let exist = false;
      let sl = 0;
        for (let i = 0; i< data.length; i++){
            if(data[i].id===item.id){
              data[i].quantity = data[i].quantity + this.buyAmount;
              exist = true;
            }
            sl++;
        }
        if(!exist){
          if(!(data instanceof Array))
            data= [data];
          data.push(item);
        }
        this.cartNumber=sl;
        if(!exist)
          this.cartNumber++;
        localStorage.setItem('cart',JSON.stringify(data));
        document.getElementById("numberItemCart").innerHTML = this.cartNumber.toString();
      alert('Thêm thành công ' + this.buyAmount + ' ' + this.product.name + ' vào giỏ hàng!');
    }
    else{
      alert('Thêm vào giỏ hàng thất bại, trong kho không còn đủ số lượng yêu cầu!');
    }
  }

  updateCartNumber(isMore:boolean){
    if(isMore){
    }
  }

}

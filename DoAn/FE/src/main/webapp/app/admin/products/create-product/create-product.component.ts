import { HttpResponse } from '@angular/common/http';
import { TextAttribute } from '@angular/compiler/src/render3/r3_ast';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'app/admin/categories/category.model';
import { Producer } from 'app/admin/producers/producer.model';
import { CategoryService } from 'app/service/category.service';
import { ProducerService } from 'app/service/producer.service';
import { ProductService } from 'app/service/product.service';
import { Product } from '../product.model';
import { ProductSpec } from '../productSpec.model';

@Component({
  selector: 'jhi-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.scss']
})
export class CreateProductComponent implements OnInit {

  product = new Product();
  producers: Producer[];
  categories: Category[];
  numberSpec = [];
  url: any;
  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  productImage;
  countSpec: number;

  isSaving = false;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private producerService: ProducerService,
    private categoryService: CategoryService,
    ) {}

  ngOnInit(): void {
    // let id = this.route.snapshot.paramMap.get('id');
    //   this.productService.getOne(id).subscribe(res => {
    //     this.product=res;
    //     this.updateForm(this.product);
    //   });
    this.countSpec = 0;
    this.producerService.getAll().subscribe(res=>{
      this.producers = res;
    })
    this.categoryService.getAll().subscribe(res=>{
      this.categories = res;
    })

    if(this.route.snapshot.paramMap.has('id')){
      let id = this.route.snapshot.paramMap.get('id');
      this.productService.getOne(id).subscribe(res => {
        this.product=res;
        this.updateForm(this.product);
      });
      this.productService
      .querySpecs({
        page: 0,
        size: 9999,
        sort: ['id_product_spec','asc'],
      },id)
      .subscribe({
        next: (res: HttpResponse<ProductSpec[]>) => {
          this.numberSpec = res.body;
        },
      });
    }
  }

  preview(files) {
    if (files.length === 0)
      return;
 
    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    
    this.productImage = files[0];
 
    var reader = new FileReader();
    // this.imagePath = files;
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.url = reader.result; 
    }
  }

  addItem() {
    var item = new ProductSpec();
    this.numberSpec.push(item);
    this.countSpec++;
  }
  
  deleteSpecInlist(item) {
    this.numberSpec = this.numberSpec.filter(x => x.idProductSpec !== item.idProductSpec);
  }

  deleteSpec(item) {
    if (item.idProductSpec == '' || item.idProductSpec == undefined || item.idProductSpec == null) {
      this.numberSpec.pop();
      this.countSpec--;
    }
    else{
      this.productService.deleteSpec(item.idProductSpec).subscribe(res => {});
      this.deleteSpecInlist(item);
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateProduct(this.product);
    if (this.product.idProduct !== undefined) {
      const formData = new FormData();
      formData.append('product',JSON.stringify(this.product));
      formData.append('image', this.productImage);

      this.productService.update(this.product.idProduct,formData).subscribe({
        next: () => this.onUpdateSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      const formData = new FormData();
      formData.append('product',JSON.stringify(this.product));
      formData.append('image', this.productImage);

      this.productService.create(formData).subscribe(res=>{
        for(let i = 0; i<this.numberSpec.length;i++){
          let ps = new ProductSpec();
          ps = this.numberSpec[i];
          ps.idProduct = res.idProduct;
          this.productService.createSpec(ps).subscribe(res=>{});
        }
        this.onSaveSuccess();
      })
    }
  }

  private updateForm(product: Product): void {
    this.editForm.patchValue({
      idProduct: product.idProduct,
      name: product.name,
      idCategory: product.idCategory,
      idProducer: product.idProducer,
      code: product.code,
      price: product.price,
      salePercent: product.salePercent,
      quantity: product.quantity,
      information: product.information,
      informationDetails: product.informationDetails,
    });
    if(product.image){
      this.url = this.imagePath + product.image;
    }
  }

  private updateProduct(product: Product): void {
    product.name = this.editForm.get(['name'])!.value;
    product.idCategory = this.editForm.get(['idCategory'])!.value;
    product.idProducer = this.editForm.get(['idProducer'])!.value;
    product.code = this.editForm.get(['code'])!.value;
    product.price = this.editForm.get(['price'])!.value;
    product.salePercent = this.editForm.get(['salePercent'])!.value;
    product.quantity = this.editForm.get(['quantity'])!.value;
    product.information = this.editForm.get(['information'])!.value;
    product.informationDetails = this.editForm.get(['informationDetails'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onUpdateSuccess(): void {
    for(let i = this.numberSpec.length-1; i > this.numberSpec.length-this.countSpec-1;i--){
      var a = new ProductSpec();
      a.key = this.numberSpec[i].key;
      a.value = this.numberSpec[i].value;
      a.idProduct = this.product.idProduct;
      this.productService.createSpec(a).subscribe(res=>{});
  }
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  editForm = this.fb.group({
    idProduct: [],
    name: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
      ],
    ],
    code: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(45),
      ],
    ],
    price: [
      '',
      [
        Validators.required,
      ],
    ],
    salePercent: [],
    quantity: [
      '',
      [
        Validators.required,
      ],
    ],
    information: [],
    informationDetails: [],
    image: [],
    idCategory: [
      '',
      [
        Validators.required,
      ],
    ],
    idProducer: [
      '',
      [
        Validators.required,
      ],
    ],
  });

}

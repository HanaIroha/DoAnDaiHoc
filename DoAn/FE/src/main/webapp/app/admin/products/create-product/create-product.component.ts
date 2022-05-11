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

@Component({
  selector: 'jhi-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.scss']
})
export class CreateProductComponent implements OnInit {

  product = new Product();
  producers: Producer[];
  categories: Category[];
  url: any;
  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  productImage;

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

  loadOptions(): void {

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
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      const formData = new FormData();
      formData.append('product',JSON.stringify(this.product));
      formData.append('image', this.productImage);

      this.productService.create(formData).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
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
      supportSim: product.supportSim,
      monitor: product.monitor,
      color: product.color,
      frontCamera: product.frontCamera,
      rearCamera: product.rearCamera,
      cPU: product.cPU,
      gPU: product.gPU,
      rAM: product.rAM,
      rOM: product.rOM,
      oS: product.oS,
      pin: product.pin,
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
    product.supportSim = this.editForm.get(['supportSim'])!.value;
    product.monitor = this.editForm.get(['monitor'])!.value;
    product.color = this.editForm.get(['color'])!.value;
    product.frontCamera = this.editForm.get(['frontCamera'])!.value;
    product.rearCamera = this.editForm.get(['rearCamera'])!.value;
    product.cPU = this.editForm.get(['cPU'])!.value;
    product.gPU = this.editForm.get(['gPU'])!.value;
    product.rAM = this.editForm.get(['rAM'])!.value;
    product.rOM = this.editForm.get(['rOM'])!.value;
    product.oS = this.editForm.get(['oS'])!.value;
    product.pin = this.editForm.get(['pin'])!.value;
    product.informationDetails = this.editForm.get(['informationDetails'])!.value;
  }

  private onSaveSuccess(): void {
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
    supportSim: [],
    monitor: [],
    color: [],
    frontCamera: [],
    rearCamera: [],
    cPU: [],
    gPU: [],
    rAM: [],
    rOM: [],
    oS: [],
    pin: [],
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

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from 'app/service/image.service';
import { Image } from '../image.model';

@Component({
  selector: 'jhi-create-image',
  templateUrl: './create-image.component.html',
  styleUrls: ['./create-image.component.scss']
})
export class CreateImageComponent implements OnInit {

  image = new Image();
  isSaving = false;

  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  productImage;
  url: any;

  editForm = this.fb.group({
    idImage: [],
    name: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
      ],
    ],
  });

  constructor(private imageService: ImageService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    // let id = this.route.snapshot.paramMap.get('id');
    //   this.imageService.getOne(id).subscribe(res => {
    //     this.image=res;
    //     this.updateForm(this.image);
    //   });
    // if(this.route.snapshot.paramMap.has('id')){
    //   let id = this.route.snapshot.paramMap.get('id');
    //   this.imageService.getOne(id).subscribe(res => {
    //     this.image=res;
    //     this.updateForm(this.image);
    //   });
    // }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    if (this.image.idImage !== undefined) {
      this.imageService.update(this.image).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      let id = this.route.snapshot.paramMap.get('id');
      const formData = new FormData();
      formData.append('id',id);
      formData.append('image', this.productImage);

      this.imageService.create(formData).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private updateForm(image: Image): void {
    this.editForm.patchValue({
      idImage: image.idImage,
      imageUrl: image.imageUrl,
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
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


}

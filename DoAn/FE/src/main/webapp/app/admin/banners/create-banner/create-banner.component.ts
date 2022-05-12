import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BannerService } from 'app/service/banner.service';
import { Banner } from '../banner.model';

@Component({
  selector: 'jhi-create-banner',
  templateUrl: './create-banner.component.html',
  styleUrls: ['./create-banner.component.scss']
})
export class CreateBannerComponent implements OnInit {
  banner = new Banner();
  isSaving = false;

  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  productImage;
  url: any;

  editForm = this.fb.group({
    idBanner: [],
    upperTitle: [],
    mainTitle: [],
    image: [],
    linkNavigate: [],
  });

  constructor(
    private bannerService: BannerService, 
    private route: ActivatedRoute, 
    private fb: FormBuilder,
  ) { }

  ngOnInit(): void {
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    if (this.banner.idBanner !== undefined) {
      this.bannerService.update(this.banner).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      let id = this.route.snapshot.paramMap.get('id');
      const formData = new FormData();
      this.banner.upperTitle = this.editForm.get(['upperTitle'])!.value;
      this.banner.mainTitle = this.editForm.get(['mainTitle'])!.value;
      this.banner.linkNavigate = this.editForm.get(['linkNavigate'])!.value;
      formData.append('banner', JSON.stringify(this.banner));
      formData.append('image', this.productImage);

      this.bannerService.create(formData).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
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

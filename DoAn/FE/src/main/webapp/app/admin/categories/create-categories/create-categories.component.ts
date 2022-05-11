import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'app/service/category.service';
import { Category } from '../category.model';

@Component({
  selector: 'jhi-create-categories',
  templateUrl: './create-categories.component.html',
  styleUrls: ['./create-categories.component.scss'],
})
export class CreateCategoriesComponent implements OnInit {
  category = new Category();
  isSaving = false;

  editForm = this.fb.group({
    idCategory: [],
    name: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
      ],
    ],
  });

  constructor(private categoryService: CategoryService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ category }) => {
      if (category) {
        this.category=category;
        this.updateForm(category);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateCategory(this.category);
    if (this.category.idCategory !== undefined) {
      this.categoryService.update(this.category).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.categoryService.create(this.category).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private updateForm(category: Category): void {
    this.editForm.patchValue({
      idCategory: category.idCategory,
      name: category.name,
    });
  }

  private updateCategory(category: Category): void {
    category.name = this.editForm.get(['name'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }


}

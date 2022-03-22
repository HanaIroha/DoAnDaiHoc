import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { ImageService } from 'app/service/image.service';
import { Image } from './image.model';

@Component({
  selector: 'jhi-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss']
})
export class ImageComponent implements OnInit {

  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";

  defaultSort = "id_image,asc";
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  images: Image[] | null = null;

  constructor(
    private imageService: ImageService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackIdentity(index: number, item: Image): number {
    return item.idImage!;
  }

  deleteCategory(idImage): void {
    this.imageService.delete(idImage).subscribe(res => {});
    location.reload();
  }

  previousState(): void {
    window.history.back();
  }

  loadAll(): void {
    let id = this.activatedRoute.snapshot.paramMap.get('id');
    this.isLoading = true;
    this.imageService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      }, id)
      .subscribe({
        next: (res: HttpResponse<Image[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  transition(): void {
    this.router.navigate(['./producer'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? this.defaultSort).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id_image') {
      result.push('id_image');
    }
    return result;
  }

  private onSuccess(images: Image[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.images = images;
  }

}

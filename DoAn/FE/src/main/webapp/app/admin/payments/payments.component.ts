import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { Payment } from './payment.model';
import { PaymentService } from 'app/service/payment.service';

@Component({
  selector: 'jhi-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentsComponent implements OnInit {

  defaultSort = "idPayment,asc";
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  payments: Payment[] | null = null;

  constructor(
    private paymentService: PaymentService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackIdentity(index: number, item: Payment): number {
    return item.idPayment!;
  }

  deleteCategory(idPayment): void {
    this.paymentService.delete(idPayment).subscribe(res => {});
    location.reload();
  }

  loadAll(): void {
    this.isLoading = true;
    this.paymentService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<Payment[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  transition(): void {
    this.router.navigate(['./payment'], {
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
    if (this.predicate !== 'idPayment') {
      result.push('idPayment');
    }
    return result;
  }

  private onSuccess(payments: Payment[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.payments = payments;
  }

}

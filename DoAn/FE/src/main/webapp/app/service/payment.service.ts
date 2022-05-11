import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayment } from 'app/admin/payments/payment.model';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/payments');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IPayment[]>(`${this.applicationConfigService.getEndpointFor('api/payments')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/payments/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/payments/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(payment: IPayment): Observable<IPayment> {
    return this.http.post<IPayment>(this.resourceUrl, payment);
  }

  update(payment: IPayment): Observable<IPayment> {
    return this.http.put<IPayment>(`${this.applicationConfigService.getEndpointFor('api/payments/')}` + payment.idPayment, payment);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/payments/')}` + id);
  }

  query(req?: Pagination): Observable<HttpResponse<IPayment[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPayment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}

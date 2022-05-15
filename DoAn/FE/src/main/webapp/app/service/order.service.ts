import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrder } from 'app/checkout/order.model';
import { IOrderDetail } from 'app/checkout/orderDetail.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/orders');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IOrder[]>(`${this.applicationConfigService.getEndpointFor('api/orders')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/orders/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/catalogs/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(order: IOrder): Observable<IOrder> {
    return this.http.post<IOrder>(this.resourceUrl, order);
  }

  getUserOrderCountByLogin(login:string): Observable<any> {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/orders/userordercount')}`+"?login="+login);
  }

  createDetails(orderdetail: IOrderDetail): Observable<IOrderDetail> {
    return this.http.post<IOrderDetail>(`${this.applicationConfigService.getEndpointFor('api/order-details')}`, orderdetail);
  }

  update(order: IOrder): Observable<IOrder> {
    return this.http.put<IOrder>(`${this.applicationConfigService.getEndpointFor('api/orders/')}` + order.idOrder, order);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/orders/')}` + id);
  }

  query(req?: Pagination, status?: string): Observable<HttpResponse<IOrder[]>> {
    const options = createRequestOption(req);
    return this.http.get<IOrder[]>(`${this.applicationConfigService.getEndpointFor('api/ordersbystatus/')}` +status, { params: options, observe: 'response' });
  }

  queryByLogin(req?: Pagination): Observable<HttpResponse<IOrder[]>> {
    const options = createRequestOption(req);
    return this.http.get<IOrder[]>(`${this.applicationConfigService.getEndpointFor('api/ordersbylogin')}`, { params: options, observe: 'response' });
  }

  queryByLoginParam(req?: Pagination, login?:string): Observable<HttpResponse<IOrder[]>> {
    const options = createRequestOption(req);
    return this.http.get<IOrder[]>(`${this.applicationConfigService.getEndpointFor('api/ordersbylogin?login=')}`+login, { params: options, observe: 'response' });
  }

  queryDetails(req?: Pagination, id?: any): Observable<HttpResponse<IOrderDetail[]>> {
    const options = createRequestOption(req);
    return this.http.get<IOrderDetail[]>(`${this.applicationConfigService.getEndpointFor('api/order-detailsbyorderid/')}` +id, { params: options, observe: 'response' });
  }
  getOrderValue(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/ordervalue/')}` + id);
  }
  setOrderStatus(id,status) {
    return this.http.put(`${this.applicationConfigService.getEndpointFor('api/setordersstatus/')}` + id +'/'+status, null, { responseType: 'text' });
  }
  checkOrder(id) {
    return this.http.get(`${this.applicationConfigService.getEndpointFor('api/orderdetailscheck/')}` + id, { responseType: 'text' });
  }
  completeOrder(id) {
    return this.http.get(`${this.applicationConfigService.getEndpointFor('api/orderdetailsok/')}` + id, { responseType: 'text' });
  }
}

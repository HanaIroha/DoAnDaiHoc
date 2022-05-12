import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IProduct } from 'app/admin/products/product.model';
import { IProductSpec } from 'app/admin/products/productSpec.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/products');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'multipart/form-data',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IProduct[]>(`${this.applicationConfigService.getEndpointFor('api/products')}`);
  }

  getOne(id) {
    return this.http.get<IProduct>(`${this.applicationConfigService.getEndpointFor('api/products/')}` + id);
  }

  // checkExist(data) {
  //   return this.http.post<any>(
  //     `${this.applicationConfigService.getEndpointFor('api/management/catalogs/checkExist')}`,
  //     data,
  //     this.httpOptions
  //   );
  // }

  create(formData: FormData): Observable<any> {
    return this.http.post<IProduct>(this.resourceUrl, formData);
  }

  update(id, formData: FormData): Observable<any> {
    return this.http.put<IProduct>(`${this.applicationConfigService.getEndpointFor('api/products/')}` + id, formData);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/products/safedelete/')}` + id);
  }

  query(req?: Pagination): Observable<HttpResponse<IProduct[]>> {
    const options = createRequestOption(req);
    return this.http.get<IProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryActive(req?: Pagination, filterKey?, filterProducer?, filterPrice? ): Observable<HttpResponse<IProduct[]>> {
    const options = createRequestOption(req);
    return this.http.get<IProduct[]>(`${this.applicationConfigService.getEndpointFor('api/productsActive/')}` + filterKey + '/' + filterProducer +'/'+filterPrice, { params: options, observe: 'response' });
  }

  getAmountByOs(os:string){
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/products/amountbyos/'+os)}`);
  }

  getPriceById(id){
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/products/pricebyid/'+id)}`);
  }

  createSpec(productspec: IProductSpec): Observable<IProductSpec> {
    return this.http.post<IProductSpec>(`${this.applicationConfigService.getEndpointFor('api/product-specs')}`, productspec);
  }

  deleteSpec(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/product-specs/')}` + id);
  }

  querySpecs(req?: Pagination, id?: any): Observable<HttpResponse<IProductSpec[]>> {
    const options = createRequestOption(req);
    return this.http.get<IProductSpec[]>(`${this.applicationConfigService.getEndpointFor('api/product-specsByProductId/')}` +id, { params: options, observe: 'response' });
  }
}

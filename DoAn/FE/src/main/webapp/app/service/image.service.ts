import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IImage } from 'app/admin/products/image/image.model';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/images');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IImage[]>(`${this.applicationConfigService.getEndpointFor('api/images')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/images/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/payments/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(formData: FormData): Observable<any> {
    return this.http.post<IImage>(this.resourceUrl, formData);
  }

  update(payment: IImage): Observable<IImage> {
    return this.http.put<IImage>(`${this.applicationConfigService.getEndpointFor('api/images/')}` + payment.idImage, payment);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/images/')}` + id);
  }

  query(req?: Pagination, id?): Observable<HttpResponse<IImage[]>> {
    const options = createRequestOption(req);
    return this.http.get<IImage[]>(`${this.applicationConfigService.getEndpointFor('api/imagesofproduct/')}` + id, { params: options, observe: 'response' });
  }
}

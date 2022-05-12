import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IBanner } from 'app/admin/banners/banner.model';

@Injectable({
  providedIn: 'root',
})
export class BannerService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/banners');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IBanner[]>(`${this.applicationConfigService.getEndpointFor('api/banners')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/images/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/banners/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(formData: FormData): Observable<any> {
    return this.http.post<IBanner>(this.resourceUrl, formData);
  }

  update(banner: IBanner): Observable<IBanner> {
    return this.http.put<IBanner>(`${this.applicationConfigService.getEndpointFor('api/images/')}` + banner.idBanner, banner);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/banners/')}` + id);
  }

  query(req?: Pagination): Observable<HttpResponse<IBanner[]>> {
    const options = createRequestOption(req);
    return this.http.get<IBanner[]>(`${this.applicationConfigService.getEndpointFor('api/banners')}`, { params: options, observe: 'response' });
  }
}

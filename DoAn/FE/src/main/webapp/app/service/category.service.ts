import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ICategory } from 'app/admin/categories/category.model';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/categories');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<ICategory[]>(`${this.applicationConfigService.getEndpointFor('api/categories')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/categories/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/catalogs/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(category: ICategory): Observable<ICategory> {
    return this.http.post<ICategory>(this.resourceUrl, category);
  }

  update(category: ICategory): Observable<ICategory> {
    return this.http.put<ICategory>(`${this.applicationConfigService.getEndpointFor('api/categories/')}` + category.idCategory, category);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/categories/')}` + id);
  }

  query(req?: Pagination): Observable<HttpResponse<ICategory[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}

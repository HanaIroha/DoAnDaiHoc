import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IProducer } from 'app/admin/producers/producer.model';

@Injectable({
  providedIn: 'root',
})
export class ProducerService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/producers');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IProducer[]>(`${this.applicationConfigService.getEndpointFor('api/producers')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/producers/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/catalogs/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(producer: IProducer): Observable<IProducer> {
    return this.http.post<IProducer>(this.resourceUrl, producer);
  }

  update(producer: IProducer): Observable<IProducer> {
    return this.http.put<IProducer>(`${this.applicationConfigService.getEndpointFor('api/producers/')}` + producer.idProducer, producer);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/producers/')}` + id);
  }

  query(req?: Pagination): Observable<HttpResponse<IProducer[]>> {
    const options = createRequestOption(req);
    return this.http.get<IProducer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}

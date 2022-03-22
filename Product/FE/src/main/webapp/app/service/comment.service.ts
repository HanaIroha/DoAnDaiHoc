import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, never } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';
import { IComment } from 'app/shop-detail/comment.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/comments');
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true',
    }),
  };

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getAll() {
    return this.http.get<IComment[]>(`${this.applicationConfigService.getEndpointFor('api/comments')}`);
  }

  getOne(id) {
    return this.http.get<any>(`${this.applicationConfigService.getEndpointFor('api/comments/')}` + id);
  }

  checkExist(data) {
    return this.http.post<any>(
      `${this.applicationConfigService.getEndpointFor('api/management/catalogs/checkExist')}`,
      data,
      this.httpOptions
    );
  }

  create(comment: IComment): Observable<IComment> {
    return this.http.post<IComment>(this.resourceUrl, comment);
  }

  update(comment: IComment): Observable<IComment> {
    return this.http.put<IComment>(`${this.applicationConfigService.getEndpointFor('api/comments/')}` + comment.idComment, comment);
  }

  delete(id) {
    return this.http.delete<any>(`${this.applicationConfigService.getEndpointFor('api/comments/')}` + id);
  }

  query(req?: Pagination): Observable<HttpResponse<IComment[]>> {
    const options = createRequestOption(req);
    return this.http.get<IComment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByProductId(req?: Pagination, id?): Observable<HttpResponse<IComment[]>> {
    const options = createRequestOption(req);
    return this.http.get<IComment[]>(`${this.applicationConfigService.getEndpointFor('api/commentsByProductId/'+id)}`, { params: options, observe: 'response' });
  }
  
  getAvatar(login?){
      return this.http.get(`${this.applicationConfigService.getEndpointFor('api/admin/users/getavatarbylogin/'+login)}`,{responseType:'text'});
  }
}

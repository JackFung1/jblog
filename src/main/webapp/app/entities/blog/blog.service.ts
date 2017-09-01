import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Blog } from './blog.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BlogService {

    private resourceUrl = 'api/blogs';

    constructor(private http: Http) { }

    create(blog: Blog): Observable<Blog> {
        const copy = this.convert(blog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(blog: Blog): Observable<Blog> {
        const copy = this.convert(blog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Blog> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(blog: Blog): Blog {
        const copy: Blog = Object.assign({}, blog);
        return copy;
    }
}

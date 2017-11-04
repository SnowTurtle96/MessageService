import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { UserMessageAccountMySuffix } from './user-message-account-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserMessageAccountMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/user-message-accounts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(userMessageAccount: UserMessageAccountMySuffix): Observable<UserMessageAccountMySuffix> {
        const copy = this.convert(userMessageAccount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(userMessageAccount: UserMessageAccountMySuffix): Observable<UserMessageAccountMySuffix> {
        const copy = this.convert(userMessageAccount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<UserMessageAccountMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to UserMessageAccountMySuffix.
     */
    private convertItemFromServer(json: any): UserMessageAccountMySuffix {
        const entity: UserMessageAccountMySuffix = Object.assign(new UserMessageAccountMySuffix(), json);
        entity.dob = this.dateUtils
            .convertLocalDateFromServer(json.dob);
        return entity;
    }

    /**
     * Convert a UserMessageAccountMySuffix to a JSON which can be sent to the server.
     */
    private convert(userMessageAccount: UserMessageAccountMySuffix): UserMessageAccountMySuffix {
        const copy: UserMessageAccountMySuffix = Object.assign({}, userMessageAccount);
        copy.dob = this.dateUtils
            .convertLocalDateToServer(userMessageAccount.dob);
        return copy;
    }
}

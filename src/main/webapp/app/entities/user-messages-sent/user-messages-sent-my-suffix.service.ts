import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { UserMessagesSentMySuffix } from './user-messages-sent-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserMessagesSentMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/user-messages-sents';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(userMessagesSent: UserMessagesSentMySuffix): Observable<UserMessagesSentMySuffix> {
        const copy = this.convert(userMessagesSent);
        console.log("Recieved!");
        console.log(userMessagesSent);
        return  this.http.post(this.resourceUrl, copy).map((res: Response) => {
            console.log("Recieved1!");

            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(userMessagesSent: UserMessagesSentMySuffix): Observable<UserMessagesSentMySuffix> {
        const copy = this.convert(userMessagesSent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<UserMessagesSentMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return Observable.interval(500)
            .mergeMap(() => this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res)));
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
     * Convert a returned JSON object to UserMessagesSentMySuffix.
     */
    private convertItemFromServer(json: any): UserMessagesSentMySuffix {
        const entity: UserMessagesSentMySuffix = Object.assign(new UserMessagesSentMySuffix(), json);
        entity.timeSent = this.dateUtils
            .convertDateTimeFromServer(json.timeSent);
        return entity;
    }

    /**
     * Convert a UserMessagesSentMySuffix to a JSON which can be sent to the server.
     */
    private convert(userMessagesSent: UserMessagesSentMySuffix): UserMessagesSentMySuffix {
        const copy: UserMessagesSentMySuffix = Object.assign({}, userMessagesSent);

        copy.timeSent = this.dateUtils.toDate(userMessagesSent.timeSent);
        return copy;
    }
}

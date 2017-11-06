import {Observable} from "rxjs/Observable";
import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {Http, RequestOptions, URLSearchParams} from '@angular/http';
import { Account, LoginModalService, Principal } from '../shared';
import {HttpHeaders} from "@angular/common/http";
import {UserMessagesSentMySuffix} from "../entities/user-messages-sent/user-messages-sent-my-suffix.model";
import {UserMessageAccountMySuffixService} from "../entities/user-message-account/user-message-account-my-suffix.service";
import {UserMessagesSentMySuffixService} from "../entities/user-messages-sent/user-messages-sent-my-suffix.service";
import {ResponseWrapper} from "../shared/model/response-wrapper.model";
import {UserMessageAccountMySuffix} from "../entities/user-message-account/user-message-account-my-suffix.model";
import {JhiEventManager, JhiAlertService, JhiDateUtils} from 'ng-jhipster';
import {SERVER_API_URL} from "../app.constants";
import {WebSocketService} from "./websocket";


@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    username: String;
    message: string;
    usermessageaccounts: UserMessageAccountMySuffix[];
    userMessagesSent: UserMessagesSentMySuffix;
    private resourceUrl = SERVER_API_URL + 'api/user-messages-sents';
    isSaving: Boolean;
    userMessageAccounts: UserMessageAccountMySuffix[];
    private userMessagesSents = [];
    public dateString;
    public a;


        constructor(private principal: Principal,
                private loginModalService: LoginModalService,
                private eventManager: JhiEventManager, private http: Http,    private userMessagesSentService: UserMessagesSentMySuffixService,
                private userMessageAccountService: UserMessageAccountMySuffixService,
                    private jhiAlertService: JhiAlertService, private dateUtils: JhiDateUtils, private websocket: WebSocketService
    ) {
        this.userMessagesSent = new UserMessagesSentMySuffix();
        this.loadAll();
        this.loadAllSent();

    }

    ngOnInit() {
        this.userMessageAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.usermessageaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.isSaving = false;
        this.userMessagesSents = [ new UserMessagesSentMySuffix()];

        this.scroll();
        this.websocket.connect("www.google");

        // this.websocket.create();




    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }



    sendMessage() {

        this.message = (<HTMLInputElement>document.getElementById("messageSent")).value;
        (<HTMLInputElement>document.getElementById("messageSent")).value = ("");

        this.userMessagesSent.body = this.message;
        this.userMessagesSent.userMessageAccountId = 1;
        this.userMessagesSent.username = this.account.firstName;
        this.save();
    }

    save() {
        this.isSaving = true;
        if (this.userMessagesSent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userMessagesSentService.update(this.userMessagesSent));
        } else {
            this.subscribeToSaveResponse(
                this.userMessagesSentService.create(this.userMessagesSent));
            console.log("boom");
        }
    }

    private subscribeToSaveResponse(result: Observable<UserMessagesSentMySuffix>) {
        result.subscribe((res: UserMessagesSentMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserMessagesSentMySuffix) {
        this.eventManager.broadcast({ name: 'userMessagesSentListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }


    loadAll() {
        this.userMessageAccountService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userMessageAccounts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    convertTime(){

     for(let a of this.a)
     {
         console.log(a.timeSent);
     }



    }


    loadAllSent() {

        this.userMessagesSentService.query().subscribe(
            (res: ResponseWrapper) => {
                 this.a = res.json;
                this.scroll();


            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    scroll(){
        var textarea = document.getElementById('messageList');
        textarea.scrollTop = textarea.scrollHeight;
    }


}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserMessagesSentMySuffix } from './user-messages-sent-my-suffix.model';
import { UserMessagesSentMySuffixPopupService } from './user-messages-sent-my-suffix-popup.service';
import { UserMessagesSentMySuffixService } from './user-messages-sent-my-suffix.service';
import { UserMessageAccountMySuffix, UserMessageAccountMySuffixService } from '../user-message-account';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-messages-sent-my-suffix-dialog',
    templateUrl: './user-messages-sent-my-suffix-dialog.component.html'
})
export class UserMessagesSentMySuffixDialogComponent implements OnInit {

    userMessagesSent: UserMessagesSentMySuffix;
    isSaving: boolean;
    usermessageaccounts: UserMessageAccountMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userMessagesSentService: UserMessagesSentMySuffixService,
        private userMessageAccountService: UserMessageAccountMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userMessageAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.usermessageaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userMessagesSent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userMessagesSentService.update(this.userMessagesSent));
        } else {
            this.subscribeToSaveResponse(
                this.userMessagesSentService.create(this.userMessagesSent));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserMessagesSentMySuffix>) {
        result.subscribe((res: UserMessagesSentMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserMessagesSentMySuffix) {
        this.eventManager.broadcast({ name: 'userMessagesSentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserMessageAccountById(index: number, item: UserMessageAccountMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-messages-sent-my-suffix-popup',
    template: ''
})
export class UserMessagesSentMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMessagesSentPopupService: UserMessagesSentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userMessagesSentPopupService
                    .open(UserMessagesSentMySuffixDialogComponent as Component, params['id']);
            } else {
                this.userMessagesSentPopupService
                    .open(UserMessagesSentMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

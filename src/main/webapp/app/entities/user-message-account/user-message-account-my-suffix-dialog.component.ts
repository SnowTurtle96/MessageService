import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserMessageAccountMySuffix } from './user-message-account-my-suffix.model';
import { UserMessageAccountMySuffixPopupService } from './user-message-account-my-suffix-popup.service';
import { UserMessageAccountMySuffixService } from './user-message-account-my-suffix.service';

@Component({
    selector: 'jhi-user-message-account-my-suffix-dialog',
    templateUrl: './user-message-account-my-suffix-dialog.component.html'
})
export class UserMessageAccountMySuffixDialogComponent implements OnInit {

    userMessageAccount: UserMessageAccountMySuffix;
    isSaving: boolean;
    dobDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userMessageAccountService: UserMessageAccountMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userMessageAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userMessageAccountService.update(this.userMessageAccount));
        } else {
            this.subscribeToSaveResponse(
                this.userMessageAccountService.create(this.userMessageAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserMessageAccountMySuffix>) {
        result.subscribe((res: UserMessageAccountMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserMessageAccountMySuffix) {
        this.eventManager.broadcast({ name: 'userMessageAccountListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-user-message-account-my-suffix-popup',
    template: ''
})
export class UserMessageAccountMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMessageAccountPopupService: UserMessageAccountMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userMessageAccountPopupService
                    .open(UserMessageAccountMySuffixDialogComponent as Component, params['id']);
            } else {
                this.userMessageAccountPopupService
                    .open(UserMessageAccountMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserMessageAccountMySuffix } from './user-message-account-my-suffix.model';
import { UserMessageAccountMySuffixPopupService } from './user-message-account-my-suffix-popup.service';
import { UserMessageAccountMySuffixService } from './user-message-account-my-suffix.service';

@Component({
    selector: 'jhi-user-message-account-my-suffix-delete-dialog',
    templateUrl: './user-message-account-my-suffix-delete-dialog.component.html'
})
export class UserMessageAccountMySuffixDeleteDialogComponent {

    userMessageAccount: UserMessageAccountMySuffix;

    constructor(
        private userMessageAccountService: UserMessageAccountMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userMessageAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userMessageAccountListModification',
                content: 'Deleted an userMessageAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-message-account-my-suffix-delete-popup',
    template: ''
})
export class UserMessageAccountMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMessageAccountPopupService: UserMessageAccountMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userMessageAccountPopupService
                .open(UserMessageAccountMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

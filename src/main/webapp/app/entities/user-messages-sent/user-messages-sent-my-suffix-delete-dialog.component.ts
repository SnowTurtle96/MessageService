import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserMessagesSentMySuffix } from './user-messages-sent-my-suffix.model';
import { UserMessagesSentMySuffixPopupService } from './user-messages-sent-my-suffix-popup.service';
import { UserMessagesSentMySuffixService } from './user-messages-sent-my-suffix.service';

@Component({
    selector: 'jhi-user-messages-sent-my-suffix-delete-dialog',
    templateUrl: './user-messages-sent-my-suffix-delete-dialog.component.html'
})
export class UserMessagesSentMySuffixDeleteDialogComponent {

    userMessagesSent: UserMessagesSentMySuffix;

    constructor(
        private userMessagesSentService: UserMessagesSentMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userMessagesSentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userMessagesSentListModification',
                content: 'Deleted an userMessagesSent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-messages-sent-my-suffix-delete-popup',
    template: ''
})
export class UserMessagesSentMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMessagesSentPopupService: UserMessagesSentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userMessagesSentPopupService
                .open(UserMessagesSentMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

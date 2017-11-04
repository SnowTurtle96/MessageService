import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { UserMessageAccountMySuffix } from './user-message-account-my-suffix.model';
import { UserMessageAccountMySuffixService } from './user-message-account-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-message-account-my-suffix',
    templateUrl: './user-message-account-my-suffix.component.html'
})
export class UserMessageAccountMySuffixComponent implements OnInit, OnDestroy {
userMessageAccounts: UserMessageAccountMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userMessageAccountService: UserMessageAccountMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userMessageAccountService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userMessageAccounts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserMessageAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserMessageAccountMySuffix) {
        return item.id;
    }
    registerChangeInUserMessageAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('userMessageAccountListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

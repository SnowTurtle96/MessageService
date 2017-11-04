import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { UserMessagesSentMySuffix } from './user-messages-sent-my-suffix.model';
import { UserMessagesSentMySuffixService } from './user-messages-sent-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-messages-sent-my-suffix',
    templateUrl: './user-messages-sent-my-suffix.component.html'
})
export class UserMessagesSentMySuffixComponent implements OnInit, OnDestroy {
    userMessagesSents: UserMessagesSentMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userMessagesSentService: UserMessagesSentMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userMessagesSentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userMessagesSents = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserMessagesSents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserMessagesSentMySuffix) {
        return item.id;
    }
    registerChangeInUserMessagesSents() {
        this.eventSubscriber = this.eventManager.subscribe('userMessagesSentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

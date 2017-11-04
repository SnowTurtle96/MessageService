import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserMessageAccountMySuffix } from './user-message-account-my-suffix.model';
import { UserMessageAccountMySuffixService } from './user-message-account-my-suffix.service';

@Component({
    selector: 'jhi-user-message-account-my-suffix-detail',
    templateUrl: './user-message-account-my-suffix-detail.component.html'
})
export class UserMessageAccountMySuffixDetailComponent implements OnInit, OnDestroy {

    userMessageAccount: UserMessageAccountMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userMessageAccountService: UserMessageAccountMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserMessageAccounts();
    }

    load(id) {
        this.userMessageAccountService.find(id).subscribe((userMessageAccount) => {
            this.userMessageAccount = userMessageAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserMessageAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userMessageAccountListModification',
            (response) => this.load(this.userMessageAccount.id)
        );
    }
}

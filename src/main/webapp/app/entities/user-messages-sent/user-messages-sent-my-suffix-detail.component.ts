import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserMessagesSentMySuffix } from './user-messages-sent-my-suffix.model';
import { UserMessagesSentMySuffixService } from './user-messages-sent-my-suffix.service';

@Component({
    selector: 'jhi-user-messages-sent-my-suffix-detail',
    templateUrl: './user-messages-sent-my-suffix-detail.component.html'
})
export class UserMessagesSentMySuffixDetailComponent implements OnInit, OnDestroy {

    userMessagesSent: UserMessagesSentMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userMessagesSentService: UserMessagesSentMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserMessagesSents();
    }

    load(id) {
        this.userMessagesSentService.find(id).subscribe((userMessagesSent) => {
            this.userMessagesSent = userMessagesSent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserMessagesSents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userMessagesSentListModification',
            (response) => this.load(this.userMessagesSent.id)
        );
    }
}

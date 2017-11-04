import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserMessagesSentMySuffixComponent } from './user-messages-sent-my-suffix.component';
import { UserMessagesSentMySuffixDetailComponent } from './user-messages-sent-my-suffix-detail.component';
import { UserMessagesSentMySuffixPopupComponent } from './user-messages-sent-my-suffix-dialog.component';
import { UserMessagesSentMySuffixDeletePopupComponent } from './user-messages-sent-my-suffix-delete-dialog.component';

export const userMessagesSentRoute: Routes = [
    {
        path: 'user-messages-sent-my-suffix',
        component: UserMessagesSentMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessagesSents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-messages-sent-my-suffix/:id',
        component: UserMessagesSentMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessagesSents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userMessagesSentPopupRoute: Routes = [
    {
        path: 'user-messages-sent-my-suffix-new',
        component: UserMessagesSentMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessagesSents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-messages-sent-my-suffix/:id/edit',
        component: UserMessagesSentMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessagesSents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-messages-sent-my-suffix/:id/delete',
        component: UserMessagesSentMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessagesSents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

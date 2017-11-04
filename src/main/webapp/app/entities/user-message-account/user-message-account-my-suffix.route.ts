import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserMessageAccountMySuffixComponent } from './user-message-account-my-suffix.component';
import { UserMessageAccountMySuffixDetailComponent } from './user-message-account-my-suffix-detail.component';
import { UserMessageAccountMySuffixPopupComponent } from './user-message-account-my-suffix-dialog.component';
import { UserMessageAccountMySuffixDeletePopupComponent } from './user-message-account-my-suffix-delete-dialog.component';

export const userMessageAccountRoute: Routes = [
    {
        path: 'user-message-account-my-suffix',
        component: UserMessageAccountMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessageAccounts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-message-account-my-suffix/:id',
        component: UserMessageAccountMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessageAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userMessageAccountPopupRoute: Routes = [
    {
        path: 'user-message-account-my-suffix-new',
        component: UserMessageAccountMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessageAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-message-account-my-suffix/:id/edit',
        component: UserMessageAccountMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessageAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-message-account-my-suffix/:id/delete',
        component: UserMessageAccountMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMessageAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

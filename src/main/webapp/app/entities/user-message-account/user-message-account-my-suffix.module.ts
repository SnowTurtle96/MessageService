import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatapplicationSharedModule } from '../../shared';
import {
    UserMessageAccountMySuffixService,
    UserMessageAccountMySuffixPopupService,
    UserMessageAccountMySuffixComponent,
    UserMessageAccountMySuffixDetailComponent,
    UserMessageAccountMySuffixDialogComponent,
    UserMessageAccountMySuffixPopupComponent,
    UserMessageAccountMySuffixDeletePopupComponent,
    UserMessageAccountMySuffixDeleteDialogComponent,
    userMessageAccountRoute,
    userMessageAccountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userMessageAccountRoute,
    ...userMessageAccountPopupRoute,
];

@NgModule({
    imports: [
        ChatapplicationSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserMessageAccountMySuffixComponent,
        UserMessageAccountMySuffixDetailComponent,
        UserMessageAccountMySuffixDialogComponent,
        UserMessageAccountMySuffixDeleteDialogComponent,
        UserMessageAccountMySuffixPopupComponent,
        UserMessageAccountMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        UserMessageAccountMySuffixComponent,
        UserMessageAccountMySuffixDialogComponent,
        UserMessageAccountMySuffixPopupComponent,
        UserMessageAccountMySuffixDeleteDialogComponent,
        UserMessageAccountMySuffixDeletePopupComponent,
    ],
    providers: [
        UserMessageAccountMySuffixService,
        UserMessageAccountMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatapplicationUserMessageAccountMySuffixModule {}

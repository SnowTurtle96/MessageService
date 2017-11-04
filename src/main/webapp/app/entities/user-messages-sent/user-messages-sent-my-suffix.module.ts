import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChatapplicationSharedModule } from '../../shared';
import {
    UserMessagesSentMySuffixService,
    UserMessagesSentMySuffixPopupService,
    UserMessagesSentMySuffixComponent,
    UserMessagesSentMySuffixDetailComponent,
    UserMessagesSentMySuffixDialogComponent,
    UserMessagesSentMySuffixPopupComponent,
    UserMessagesSentMySuffixDeletePopupComponent,
    UserMessagesSentMySuffixDeleteDialogComponent,
    userMessagesSentRoute,
    userMessagesSentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userMessagesSentRoute,
    ...userMessagesSentPopupRoute,
];

@NgModule({
    imports: [
        ChatapplicationSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserMessagesSentMySuffixComponent,
        UserMessagesSentMySuffixDetailComponent,
        UserMessagesSentMySuffixDialogComponent,
        UserMessagesSentMySuffixDeleteDialogComponent,
        UserMessagesSentMySuffixPopupComponent,
        UserMessagesSentMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        UserMessagesSentMySuffixComponent,
        UserMessagesSentMySuffixDialogComponent,
        UserMessagesSentMySuffixPopupComponent,
        UserMessagesSentMySuffixDeleteDialogComponent,
        UserMessagesSentMySuffixDeletePopupComponent,
    ],
    providers: [
        UserMessagesSentMySuffixService,
        UserMessagesSentMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatapplicationUserMessagesSentMySuffixModule {}

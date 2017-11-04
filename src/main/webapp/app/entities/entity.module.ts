import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ChatapplicationUserMessageAccountMySuffixModule } from './user-message-account/user-message-account-my-suffix.module';
import { ChatapplicationUserMessagesSentMySuffixModule } from './user-messages-sent/user-messages-sent-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ChatapplicationUserMessageAccountMySuffixModule,
        ChatapplicationUserMessagesSentMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatapplicationEntityModule {}

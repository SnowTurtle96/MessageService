import { BaseEntity } from './../../shared';

export class UserMessagesSentMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public timeSent?: any,
        public body?: string,
        public userMessageAccountId?: number,
    ) {
    }
}

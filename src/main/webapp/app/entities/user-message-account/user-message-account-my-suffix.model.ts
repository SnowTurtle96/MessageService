import { BaseEntity } from './../../shared';

export class UserMessageAccountMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public firstname?: string,
        public lastname?: string,
        public dob?: any,
        public username?: string,
        public password?: string,
        public accessLevel?: number,
        public jobs?: BaseEntity[],
    ) {
    }
}

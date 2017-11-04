import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserMessageAccountMySuffix } from './user-message-account-my-suffix.model';
import { UserMessageAccountMySuffixService } from './user-message-account-my-suffix.service';

@Injectable()
export class UserMessageAccountMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userMessageAccountService: UserMessageAccountMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.userMessageAccountService.find(id).subscribe((userMessageAccount) => {
                    if (userMessageAccount.dob) {
                        userMessageAccount.dob = {
                            year: userMessageAccount.dob.getFullYear(),
                            month: userMessageAccount.dob.getMonth() + 1,
                            day: userMessageAccount.dob.getDate()
                        };
                    }
                    this.ngbModalRef = this.userMessageAccountModalRef(component, userMessageAccount);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userMessageAccountModalRef(component, new UserMessageAccountMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userMessageAccountModalRef(component: Component, userMessageAccount: UserMessageAccountMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userMessageAccount = userMessageAccount;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

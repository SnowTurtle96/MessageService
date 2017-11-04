import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { UserMessagesSentMySuffix } from './user-messages-sent-my-suffix.model';
import { UserMessagesSentMySuffixService } from './user-messages-sent-my-suffix.service';

@Injectable()
export class UserMessagesSentMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private userMessagesSentService: UserMessagesSentMySuffixService

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
                this.userMessagesSentService.find(id).subscribe((userMessagesSent) => {
                    userMessagesSent.timeSent = this.datePipe
                        .transform(userMessagesSent.timeSent, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.userMessagesSentModalRef(component, userMessagesSent);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userMessagesSentModalRef(component, new UserMessagesSentMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userMessagesSentModalRef(component: Component, userMessagesSent: UserMessagesSentMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userMessagesSent = userMessagesSent;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChatapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserMessageAccountMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/user-message-account/user-message-account-my-suffix-detail.component';
import { UserMessageAccountMySuffixService } from '../../../../../../main/webapp/app/entities/user-message-account/user-message-account-my-suffix.service';
import { UserMessageAccountMySuffix } from '../../../../../../main/webapp/app/entities/user-message-account/user-message-account-my-suffix.model';

describe('Component Tests', () => {

    describe('UserMessageAccountMySuffix Management Detail Component', () => {
        let comp: UserMessageAccountMySuffixDetailComponent;
        let fixture: ComponentFixture<UserMessageAccountMySuffixDetailComponent>;
        let service: UserMessageAccountMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChatapplicationTestModule],
                declarations: [UserMessageAccountMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserMessageAccountMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserMessageAccountMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMessageAccountMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMessageAccountMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserMessageAccountMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userMessageAccount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

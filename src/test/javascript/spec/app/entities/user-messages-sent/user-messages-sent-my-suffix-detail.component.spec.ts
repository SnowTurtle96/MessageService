/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChatapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserMessagesSentMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/user-messages-sent/user-messages-sent-my-suffix-detail.component';
import { UserMessagesSentMySuffixService } from '../../../../../../main/webapp/app/entities/user-messages-sent/user-messages-sent-my-suffix.service';
import { UserMessagesSentMySuffix } from '../../../../../../main/webapp/app/entities/user-messages-sent/user-messages-sent-my-suffix.model';

describe('Component Tests', () => {

    describe('UserMessagesSentMySuffix Management Detail Component', () => {
        let comp: UserMessagesSentMySuffixDetailComponent;
        let fixture: ComponentFixture<UserMessagesSentMySuffixDetailComponent>;
        let service: UserMessagesSentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChatapplicationTestModule],
                declarations: [UserMessagesSentMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserMessagesSentMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserMessagesSentMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMessagesSentMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMessagesSentMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserMessagesSentMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userMessagesSent).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

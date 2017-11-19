import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';
import {WebsocketService} from "./WebsocketService";
import {Observer} from "rxjs/Observer";

const CHAT_URL = 'localhost:9000/topic';
var SockJS = require('sockjs-client');
var Stomp = require("stompjs/lib/stomp.js").Stomp

@Injectable()
export class ChatService {

    stompClient: any;
    private observable: Observable<MessageEvent>;
    private observer: Subject<String>;
    activityId: any;
    text: any;
    messages: any;
    socket: any;


    constructor() {
    }

    send(message) {
        this.stompClient.send('/app/chat.message', {}, JSON.stringify({'message': message}));
        this.messages = 'hi';
        console.log(this.messages);
        console.log(this.messages);
    }

    connect() {
        console.log('IstisReal?');
        var that = this;
        this.socket = new SockJS('/hello');
        this.stompClient = Stomp.over(this.socket);

        this.stompClient.connect("guest", "guest", function (frame) {
            console.log('pissoff: ' + frame);
        })

        this.observable = Observable.create(
            (observer: Observer<MessageEvent>) => {
                this.socket.onmessage = observer.next.bind(observer);
                this.socket.onerror = observer.error.bind(observer);
                this.socket.onclose = observer.complete.bind(observer);
                return this.socket.close.bind(this.socket);
            }
        );

        this.observer = Subject.create({
            next: (data: String) => {
                if (this.socket.readyState === WebSocket.OPEN) {
                    this.socket.send(JSON.stringify(data));
                }
            }
        });

    }

    observeClient(): Observable<MessageEvent> {
        return this.observable;
    }












    subscription() {
        this.stompClient.subscribe('/app/chat.message', function(message: string){
            this.messages.unshift(JSON.parse(message));
        });
        console.log("gotcha" + this.messages);
        this.socket.onmessage( function(e)
        {
            console.log(e.data);
        })
    }

}

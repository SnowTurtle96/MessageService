import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';
import {WebsocketService} from "./WebsocketService";

const CHAT_URL = 'localhost:9000/topic';
var SockJS = require('sockjs-client');
var Stomp = require("stompjs/lib/stomp.js").Stomp

@Injectable()
export class ChatService {

    stompClient: any;

    activityId: any;
    text: any;
    messages: Array<String> = new Array<String>();

    constructor() {
    }

    send(message) {
        this.stompClient.send('/app/hello/' + this.activityId, {}, JSON.stringify({'name': message}));
    }

    connect() {
        var that = this;
        var socket = new SockJS('http://localhost:8080/hello');
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect("guest", "guest", function (frame) {
            console.log('Connected: ' + frame);
            that.stompClient.subscribe('http://localhost:8080/topic/greeting', function (greeting) {
                console.log("from from", greeting);
            });
        }, function (err) {
            console.log('err', err);
        });

    }
}

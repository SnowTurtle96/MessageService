import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';
import {WebsocketService} from "./WebsocketService";

const CHAT_URL = 'localhost:9000/topic';
var SockJS = require('sockjs-client');
var Stomp = require('stompjs');

@Injectable()
export class ChatService {

    stompClient: any;

    activityId: any;
    text: any;
    messages: Array<String> = new Array<String>();

    constructor() {
    }

    send() {
        this.stompClient.send('/app/hello/' + this.activityId, {},      JSON.stringify({ 'name': this.text }));
    }

    connect() {
        var that = this;
        var socket = new SockJS('tst-rest.mypageexample/hello?activityId=' + this.activityId);
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            that.stompClient.subscribe('/topic/greetings/' + that.activityId, function (greeting) {
                that.messages.push(JSON.parse(greeting.body).content);
            });
        }, function (err) {
            console.log('err', err);
        });
    }
}

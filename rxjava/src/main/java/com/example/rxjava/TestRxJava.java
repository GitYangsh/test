package com.example.rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/1/19 16:27
 * Description:
 */

public class TestRxJava {

    public static void main(String args[]) {
        System.out.println("RxJava");

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("observer:onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("observer:onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("observer:onNext,s=" + s);
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("subscriber:onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("subscriber:onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("subscriber:onNext,s=" + s);
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("AloHa");
                subscriber.onCompleted();
            }
        });

        Observable observable1 = Observable.just("Hello1", "Hi1", "AloHa1");

        String words[] = {"Hello2", "Hi2", "AloHa2"};
        Observable observable2 = Observable.from(words);

        observable.subscribe(observer);
        observable.subscribe(subscriber);

        observable1.subscribe(observer);
        observable2.subscribe(observer);


        Action1<String> onNextAction = new Action1<String>() {
            // onNext
            @Override
            public void call(String s) {
                System.out.println("nextAction:call,s=" + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError
            @Override
            public void call(Throwable throwable) {
                System.out.println("errorAction:call");
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted
            @Override
            public void call() {
                System.out.println("completedAction:call");
            }
        };
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

        String names[] = {"name1", "name2", "name3", "name4", "name5"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String name) {
                System.out.println("names=" + name);
            }
        });
    }
}

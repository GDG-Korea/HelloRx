package rx.android.gdg.kr.simplesubscriber;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello RxAndroid !!");
                        subscriber.onCompleted();
                    }
                });

        // 하나의 값만 전달하는 Observable을 만드는 유틸리티 just가 있다.
//        Observable<String> simpleObservable = Observable.just("Hello RxAndroid !!");

        // 두개 이상의 onNext를 전달하는 것을 돕는 유틸리티 from이 있다.
//        String[] array = {"Hello", "Nice"};
//        Observable<String> simpleObservable = Observable.from(array);

        simpleObservable
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String text) {
                        ((TextView) findViewById(R.id.textView)).setText(text);
                    }
                });

        // Action1과 Action0을 예외와 성공 처리에도 사용할 수 있다.
        // subscrbe는 파라미터 2개 (+예외), 3개(+예외, 성공)를 받는다.

//        simpleObservable
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String text) {
//                        ((TextView) findViewById(R.id.textView)).setText(text);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//
//                    }
//                });
    }
}

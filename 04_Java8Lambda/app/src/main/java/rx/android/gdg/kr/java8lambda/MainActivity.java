package rx.android.gdg.kr.java8lambda;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // JDK 1.8 버전을 사용하세요.
        // JDK 1.7 버전을 같이 사용하는 경우 JAVA_HOME8 등의 환경 변수를 설정하세요.

        Observable<String> simpleObservable = Observable.just("Hello Lambda!!");

        // 단일 라인 람다는 자동으로 반환값이 결정되며 세미콜론을 적지 않습니다.
        // 여러 라인으로 된 람다는 세미콜론을 적고 중괄호도 필요합니다.

        simpleObservable
                .map(text -> text.length())
                .subscribe(
                        length -> ((TextView) findViewById(R.id.textView))
                                .setText("length: " + length));
    }
}

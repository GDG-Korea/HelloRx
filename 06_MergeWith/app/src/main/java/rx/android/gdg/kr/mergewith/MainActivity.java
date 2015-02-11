package rx.android.gdg.kr.mergewith;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 왼쪽 버튼 클릭으로 발생된 이벤트는 left라는 문자열로 바꾸어 스트림으로 보냅니다.
        Observable<String> lefts = ViewObservable.clicks(findViewById(R.id.leftButton))
                .map(event -> "left");

        // 오른쪽 버튼 클릭은 right 문자열로 스트림에 보냅니다.
        Observable<String> rights = ViewObservable.clicks(findViewById(R.id.rightButton))
                .map(event -> "right");

        // 어느 스트림에서든 문자열이 보이면 통합된 스트림에 흘려보냅니다.
        Observable<String> together = Observable.merge(lefts, rights);

        // 텍스트 뷰를 변경합니다.
        together.subscribe(text -> ((TextView) findViewById(R.id.textView)).setText(text));

        // 스트림의 문자열을 대문자로 바꾸고 토스트로 뿌립니다.
        together.map(text -> text.toUpperCase())
                .subscribe(text -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show());
    }
}

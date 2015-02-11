package rx.android.gdg.kr.scan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observable;
import rx.android.view.ViewObservable;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 플러스, 마이너스 버튼에 대해 각기 -1 값과 +1 값을 스트림에 보냄.
        Observable<Integer> minuses = ViewObservable.clicks(findViewById(R.id.minusButton))
                .map(event -> -1);
        Observable<Integer> pluses = ViewObservable.clicks(findViewById(R.id.plusButton))
                .map(event -> 1);


        // -1과 +1값을 한번에 하나의 스트림으로
        Observable<Integer> together = Observable.merge(minuses, pluses);


        // scan의 첫번째 인자는 초기 값이고 두번째 인자에는 람다가 들어갑니다. 첫번째 값은 누적된 값이며 두번째 값은
        // 이번에 받은 값입니다. 리턴한 sum + 1의 값이 다음 번에 sum 인자로 넘어오게 됩니다.
        // 이 scan은 들어오는 이벤트의 수를 집계하기 위한 것입니다.

        together.scan(0, (sum, number) -> sum + 1)
                .subscribe(count ->
                        ((TextView) findViewById(R.id.count)).setText(count.toString()));


        // 들어오는 +1과 -1에 맞추어 sum을 변화시킨다.

        together.scan(0, (sum, number) -> sum + number)
                .subscribe(number ->
                        ((TextView) findViewById(R.id.number)).setText(number.toString()));
    }
}

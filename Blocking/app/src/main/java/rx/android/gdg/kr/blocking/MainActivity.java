package rx.android.gdg.kr.blocking;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;


public class MainActivity extends ActionBarActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = ((TextView) findViewById(R.id.txt_result));

        initView();
    }

    private List<String> getData() {

        List<String> list = new ArrayList<String>();

        char start = 'a';
        char end = 'z';

        for (int idx = start; idx <= end; ++idx) {
            list.add(String.valueOf((char) idx));
        }

        return list;
    }

    private void initView() {

        List<String> dataList = getData();

        findViewById(R.id.btn_first).setOnClickListener(v -> {
            String first = Observable.from(dataList)
                    .toBlocking()
                    .first();

            setResultText("first", first);
        });

        findViewById(R.id.btn_last).setOnClickListener(v -> {
            String last = Observable.from(dataList)
                    .toBlocking()
                    .last();

            setResultText("last", last);
        });

        findViewById(R.id.btn_to_iterator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterable<String> iterableDatas = Observable.from(dataList)
                        .toBlocking()
                        .toIterable();

                StringBuffer buffer = new StringBuffer();
                for (String data : iterableDatas) {
                    buffer.append(data);
                }

                setResultText("toIterator", buffer.toString());
            }
        });

        findViewById(R.id.btn_get_iterator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<String> iterator = Observable.from(dataList)
                        .toBlocking()
                        .getIterator();

                StringBuffer buffer = new StringBuffer();
                while (iterator.hasNext()) {
                    buffer.append(iterator.next());
                }
                setResultText("getIterator", buffer.toString());
            }
        });

        findViewById(R.id.btn_to_future).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Future<String> stringFuture = Observable.from(dataList)
                        .first()
                        .toBlocking()
                        .toFuture();

                StringBuffer buffer = new StringBuffer();
                int waitCount = 0;
                while (!stringFuture.isDone()) {
                    ++waitCount;
                }

                buffer.append("Wait : ").append(waitCount);

                try {
                    buffer.append(stringFuture.get());
                } catch (InterruptedException e) {
                    buffer.append("Fail");
                } catch (ExecutionException e) {
                    buffer.append("Fail");
                }

                setResultText("toFuture", buffer.toString());


            }
        });
    }

    private void setResultText(String methodName, String result) {
        resultTextView.setText(String.format("BlockingObservable.%s() = %s", methodName, result));
    }


}

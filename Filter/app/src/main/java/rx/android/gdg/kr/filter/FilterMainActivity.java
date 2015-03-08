package rx.android.gdg.kr.filter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class FilterMainActivity extends ActionBarActivity {

    private EditText editText;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_main);


        initView();
        initObject();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.et_main);
        listView = (ListView) findViewById(R.id.list_main);

        adapter = new ArrayAdapter<String>(FilterMainActivity.this, android.R.layout.simple_list_item_1);
        List<String> alphabet = getAlphabet();
        for (String s : alphabet) {
            adapter.add(s);
        }

        listView.setAdapter(adapter);

    }

    private List<String> getAlphabet() {

        List<String> list = new ArrayList<String>();

        int start = 'a';
        int end = 'z';

        for (int idx = start; idx <= end; ++idx) {
            list.add((String.valueOf((char) idx)));
        }

        return list;
    }

    private void initObject() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> alphabets = getAlphabet();

                adapter.clear();

                if (s.length() == 0) {
                    for (String alphabet : alphabets) {
                        adapter.add(alphabet);
                    }
                    return;
                }

                final String[] filters = new String[s.length()];
                for (int idx = 0; idx < s.length(); idx++) {
                    filters[idx] = s.subSequence(idx, idx + 1).toString();
                }

                Observable.from(alphabets)
                        .filter(new Func1<String, Boolean>() {
                            @Override
                            public Boolean call(final String alphabet) {

                                /*
                                for (String filter : filters) {
                                    if (alphabets.contains(filter)) {
                                        return true;
                                    }
                                }
                                return false;

                                // Like Below Code
                                 */

                                return Observable.from(filters)
                                        .filter(new Func1<String, Boolean>() {
                                            @Override
                                            public Boolean call(String filter) {
                                                return alphabet.contains(filter);
                                            }
                                        })
                                        .map(new Func1<String, Boolean>() {
                                            @Override
                                            public Boolean call(String s) {
                                                return true;
                                            }
                                        }).toBlocking()
                                        .firstOrDefault(false);
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                adapter.add(s);
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}

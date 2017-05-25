package iturchenko.demometagamesru.gui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import iturchenko.demometagamesru.R;
import iturchenko.demometagamesru.network.DataController;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private GamesAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gamesAdapter = new GamesAdapter(this);
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(gamesAdapter);

        DataController controller = new DataController();


        addDisposable(controller.getPcUpcomingRx()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gamesAdapter::setData, throwable -> {
                    Log.e("AA","Inner error", throwable);
                    Toast.makeText(getApplicationContext(), "Something gone wrong, we sorry", Toast.LENGTH_SHORT).show();
                }));
    }
}

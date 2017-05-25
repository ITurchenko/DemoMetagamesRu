package iturchenko.demometagamesru.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import iturchenko.demometagamesru.R;
import iturchenko.demometagamesru.model.GameCard;
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
                .subscribe(new Consumer<List<GameCard>>() {
                    @Override
                    public void accept(List<GameCard> cards) throws Exception {
                        gamesAdapter.setData(cards);
                    }
                }));
    }
}

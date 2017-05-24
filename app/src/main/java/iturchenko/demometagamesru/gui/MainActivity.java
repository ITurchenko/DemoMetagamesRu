package iturchenko.demometagamesru.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import iturchenko.demometagamesru.R;
import iturchenko.demometagamesru.model.GameCard;
import iturchenko.demometagamesru.network.DataController;

public class MainActivity extends AppCompatActivity {

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
        controller.setDataLoadedListener(new DataController.DataLoadedListener() {
            @Override
            public void onDataLoaded(List<GameCard> cards) {
                gamesAdapter.setData(cards);
            }
        });
        controller.getPcUpcoming();
    }
}

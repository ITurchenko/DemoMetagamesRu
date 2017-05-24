package iturchenko.demometagamesru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import iturchenko.demometagamesru.network.DataController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataController controller = new DataController();
        controller.getPcUpcoming();
    }
}

package iturchenko.demometagamesru.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import iturchenko.demometagamesru.model.GameCard;

public class DataController {
    private DataLoadedListener dataLoadedListener;

    public void getPcUpcoming() {
        new NetworkTask(NetworkConst.PC_UPCOMING).execute();
    }

    public Single<List<GameCard>> getPcUpcomingRx() {
        return Single.just(1)
                .observeOn(Schedulers.single())
                .map(new Function<Integer, List<GameCard>>() {
                    @Override
                    public List<GameCard> apply(Integer integer) throws Exception {
                        try {
                            Document document = Jsoup.connect(NetworkConst.PC_UPCOMING).get();
                            return parseDoc(document);
                        } catch (Exception e) {
                            Log.e("DataController","Error",e);
                        }

                        return Collections.emptyList();
                    }
                });
    }

    private void onDocumentLoaded(Document doc) {
        List<GameCard> gameCards = parseDoc(doc);

        if (dataLoadedListener != null) dataLoadedListener.onDataLoaded(gameCards);
    }

    @NonNull
    private List<GameCard> parseDoc(Document doc) {
        Elements sections = doc.select("section.b-games-block__group");
        Log.e("AA","> "+sections.size());

        List<GameCard> gameCards = new ArrayList<>();

        for (Element section : sections) {
            String month = section.select("span.b-games-block__info").text();

            Log.e("AA","> "+month);

            Elements gameList = section.select("li");
            for (Element gameElement : gameList) {
                GameCard gameCard = new GameCard();

                gameCard.url = gameElement.select("a").first().attr("href");
                gameCard.image = gameElement.select("img").first().attr("src");
                gameCard.name = gameElement.select("h2").first().text();
                gameCard.genre = gameElement.select("span.b-best-games__genre").first().text();
                gameCard.releaseData = gameElement.select("time").first().attr("datetime");
                gameCard.rating = gameElement.select("time").first().attr("datetime");

                Log.e("AA",">> "+gameCard);

                gameCards.add(gameCard);
            }
        }
        return gameCards;
    }

    public void setDataLoadedListener(DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
    }

    //----------------------------------------------------------------

    public interface DataLoadedListener {
        void onDataLoaded(List<GameCard> cards);
    }

    class NetworkTask extends AsyncTask<Void, Void, Document> {
        private String url;

        public NetworkTask(String url) {
            this.url = url;
        }

        @Override
        protected Document doInBackground(Void... voids) {
            try {
                return Jsoup.connect(url).get();
            } catch (Exception e) {
                Log.e("DataController","Error",e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            onDocumentLoaded(document);
        }
    }
}

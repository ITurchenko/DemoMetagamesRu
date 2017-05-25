package iturchenko.demometagamesru.network;

import android.support.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import iturchenko.demometagamesru.model.GameCard;

public class DataController {

    public Single<List<GameCard>> getPcUpcomingRx() {
        return Single.just(1)
                .observeOn(Schedulers.single())
                .map(integer -> Jsoup.connect(NetworkConst.PC_UPCOMING).get())
                .map(document -> parseDoc(document));
    }

    @NonNull
    private List<GameCard> parseDoc(Document doc) {
        Elements sections = doc.select("section.b-games-block__group");

        List<GameCard> gameCards = new ArrayList<>();

        for (Element section : sections) {
            String month = section.select("span.b-games-block__info").text();

            Elements gameList = section.select("li");
            for (Element gameElement : gameList) {
                GameCard gameCard = new GameCard();

                gameCard.url = gameElement.select("a").first().attr("href");
                gameCard.image = gameElement.select("img").first().attr("src");
                gameCard.name = gameElement.select("h2").first().text();
                gameCard.genre = gameElement.select("span.b-best-games__genre").first().text();
                gameCard.releaseData = gameElement.select("time").first().attr("datetime");
                gameCard.rating = gameElement.select("time").first().attr("datetime");

                gameCards.add(gameCard);
            }
        }
        return gameCards;
    }
}

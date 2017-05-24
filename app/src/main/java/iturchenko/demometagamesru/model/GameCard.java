package iturchenko.demometagamesru.model;

public class GameCard {
    public String url;
    public String image;
    public String name;
    public String genre;
    public String releaseData;
    public String rating;

    @Override
    public String toString() {
        return "GameCard{" +
                "url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseData='" + releaseData + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}

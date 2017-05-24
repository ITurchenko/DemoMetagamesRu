package iturchenko.demometagamesru.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import iturchenko.demometagamesru.R;
import iturchenko.demometagamesru.model.GameCard;
import iturchenko.demometagamesru.network.NetworkConst;

class GamesAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Picasso picasso;
    private List<GameCard> cards = new ArrayList<>();

    public GamesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public GameCard getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_gamecard, viewGroup, false);
        }

        GameCard card = getItem(i);

        ((TextView)view.findViewById(R.id.tv_title)).setText(card.name);
        ((TextView)view.findViewById(R.id.tv_genre)).setText(card.genre);
        ((TextView)view.findViewById(R.id.tv_release)).setText(card.releaseData);

        picasso.load(NetworkConst.BASIC_URL+card.image)
                .centerCrop()
                .fit()
                .into((ImageView) view.findViewById(R.id.image));

        return view;
    }

    public void setData(List<GameCard> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }
}

package iturchenko.demometagamesru.network;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DataController {
    public void getPcUpcoming() {
        new NetworkTask(NetworkConst.PC_UPCOMING).execute();
    }



    private void onDocumentLoaded(Document document) {
        Log.e("AA","Get document "+document);
    }

    //----------------------------------------------------------------

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

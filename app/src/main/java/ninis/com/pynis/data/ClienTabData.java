package ninis.com.pynis.data;

import java.util.ArrayList;

/**
 * Created by gypark on 2016. 1. 5..
 */
public class ClienTabData {

    private ArrayList<TabItem> items;

    public ArrayList<TabItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<TabItem> items) {
        this.items = items;
    }

    public class TabItem {
        private String link;
        private String title;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

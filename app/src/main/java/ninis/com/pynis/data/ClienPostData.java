package ninis.com.pynis.data;

import java.util.ArrayList;

/**
 * Created by gypark on 2015. 12. 16..
 */
public class ClienPostData {

    private ArrayList<ClienPostItem> items;

    public ArrayList<ClienPostItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ClienPostItem> items) {
        this.items = items;
    }

    public class ClienPostItem {
        private String title;
        private String link;
        private String number;
        private String user;
        private boolean hasImgID;
        private String imgUrl;
        private int replyCount;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public boolean isHasImgID() {
            return hasImgID;
        }

        public void setHasImgID(boolean hasImgID) {
            this.hasImgID = hasImgID;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }
    }
}

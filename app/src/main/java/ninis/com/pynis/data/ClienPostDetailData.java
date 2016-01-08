package ninis.com.pynis.data;

/**
 * Created by gypark on 2015. 12. 30..
 */
public class ClienPostDetailData {

    private DetailData data;

    public DetailData getData() {
        return data;
    }

    public void setData(DetailData data) {
        this.data = data;
    }

    public class DetailData {
        private String text;
        private String viewinfo;
        private String title;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getViewinfo() {
            return viewinfo;
        }

        public void setViewinfo(String viewinfo) {
            this.viewinfo = viewinfo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

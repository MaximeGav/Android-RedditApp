package school.reddittestvoorexamen1;

import java.io.Serializable;

public class Post implements Serializable {

    private String title, secondLine, thumbnail, fullImg;
    private int score;

    public Post(String title, String secondLine, String thumbnail, String fullImg, int score) {
        this.title = title;
        this.secondLine = secondLine;
        this.thumbnail = thumbnail;
        this.fullImg = fullImg;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFullImg() {
        return fullImg;
    }

    public void setFullImg(String fullImg) {
        this.fullImg = fullImg;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

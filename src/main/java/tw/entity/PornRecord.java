package tw.entity;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;

@Entity(name = "PORN_RECORD")
public class PornRecord {
    @Id
    @GeneratedValue
    private Long id;

    private String viewKey;
    @Column(length = 1000)
    private String imageUrl;//image_url
    @Column(length = 1000)
    private String linkUrl;//link_url
    private String videoTitle; //video_title
    private String videoDuration;//video_duration
    private String currentUp;
    private String currentDown;
    private String category;
    @Column(length = 1000)
    private String video240p;
    @Column(length = 1000)
    private String video480p;
    @Column(length = 1000)
    private String video720p;
    @Column(length = 1000)
    private String video1080p;
    private boolean download;
    private String filePath;

    public PornRecord(HashMap<String, Object> videoMap, HashMap<String, Object> likeMap, String viewKey, String filePathPrefix) {
        final String emptyString = "";
        this.viewKey = trimToNull(viewKey);
        this.imageUrl = trimToNull(videoMap.get("image_url"));
        this.linkUrl = trimToNull(videoMap.get("link_url"));
        this.videoTitle = trimToNull(videoMap.get("video_title"));
        this.videoDuration = trimToNull(videoMap.get("video_duration"));
        this.filePath = filePathPrefix + "/" + viewKey + ".mp4";
        this.currentUp = trimToNull(likeMap.get("currentUp"));
        this.currentDown = trimToNull(likeMap.get("currentDown"));

        video240p = trimToNull(videoMap.get("quality_240p"));
        video480p = trimToNull(videoMap.get("quality_480p"));
        video720p = trimToNull(videoMap.get("quality_720p"));
        video1080p = trimToNull(videoMap.get("quality_1080p"));
    }

    public String getVideo720p() {
        return video720p;
    }

    public void setVideo720p(String video720p) {
        this.video720p = video720p;
    }

    public String getVideo1080p() {
        return video1080p;
    }

    public void setVideo1080p(String video1080p) {
        this.video1080p = video1080p;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVideo480p() {
        return video480p;
    }

    public void setVideo480p(String video480p) {
        this.video480p = video480p;
    }

    public String getVideo240p() {
        return video240p;
    }

    public void setVideo240p(String video240p) {
        this.video240p = video240p;
    }

    private String trimToNull(Object object) {
        return object == null ? null : object.toString().trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getCurrentUp() {
        return currentUp;
    }

    public void setCurrentUp(String currentUp) {
        this.currentUp = currentUp;
    }

    public String getCurrentDown() {
        return currentDown;
    }

    public void setCurrentDown(String currentDown) {
        this.currentDown = currentDown;
    }

}

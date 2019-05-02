package tw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Entity(name = "PORN_RECORD")
public class PornRecord {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String viewKey;

    @Column(length = 1000)
    private String imageUrl;//image_url

    @Column(length = 1000)
    private String linkUrl;//link_url

    @Column(length = 1000)
    private String videoUrl;

    private String videoTitle; //video_title

    private int videoDuration;//video_duration

    private int videoQuality;

    private boolean download;

    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(length = 1000)
    private String filePath;


    /**
     *  @param videoMap
     * @param viewKey
     * @param filePathPrefix
     */
    public PornRecord(HashMap<String, Object> videoMap, String viewKey, String filePathPrefix, boolean download) {
        this.viewKey = trimToNull(viewKey);
        this.imageUrl = trimToNull(videoMap.get("image_url"));
        this.linkUrl = trimToNull(videoMap.get("link_url"));
        this.videoTitle = trimToNull(videoMap.get("video_title"));
        this.videoDuration = Integer.valueOf(videoMap.get("video_duration").toString());
        this.filePath = filePathPrefix + "/" + viewKey + ".mp4";
        this.download = download;
        this.videoUrl = "https://www.pornhub.com/embed/" + viewKey;
        this.videoQuality = (Integer)((List)videoMap.get("defaultQuality")).get(0);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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


    private String trimToNull(Object object) {
        return object == null ? null : object.toString().trim();
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


}

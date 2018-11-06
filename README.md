# Porn Bot：Pornhub-downloader


![java-1.8](https://img.shields.io/badge/java-1.8-green.svg)
![license](https://img.shields.io/badge/license-MIT-blue.svg)
<!-- 
[中文文檔](https://github.com/tim232385/PornBot/blob/master/README_ZH.md) -->

> Disclaimer: This project is intended to study the Java Crawler4j Framework and the Spring Boot H2 database, it cannot be used for commercial or other personal intentions. If used improperly, it will be the individuals bear.

* The project is mainly used for crawling a Website, the largest site in the world. In doing so it retrieves video titles, duration, mp4 link, cover url and direct Website`s url.
* This Project support muilti-threads request, If  your network is more performant you can request more threads and crawl a larger amount.([See Configuration](#Configuration))

### Environment, Architecture
- Java1.8

- Crawler4j

- Spring Boot x H2 Db

#### Run
``java -jar PornBot.jar``

![Demo](https://raw.githubusercontent.com/tim232385/PornBot/master/image/demo1.gif)
![h2_console](https://raw.githubusercontent.com/tim232385/PornBot/master/image/h2_console.gif)
### Database description
``http://localhost:8000/h2-console/``

JDBC URL: ``jdbc:h2:~/porn/porn-db``  

User Name: ``sa``  

Password: ~~empty~~  
    
Record Table：
```
 Table_Name               ：PORN_RECORD
 viewKey                  ：The website's video unique key.
 imageUrl                 ：Image url of video.
 linkUrl                  ：Video jump to Website`s link
 videoUrl                 ：Video adrress.
 videoTitle               ：Title of video.
 videoDuration            ：Video click count.
 videoQuality             ：Defualt quality - 240, 480, 960, 1280p.
 download                 ：Has been downloaded. True or false.
 createdTime              ：The record created time.
 filePath                 ：The video downloaded path.
```


### Configuration
<!-- | Key | Description | Default | 
|--------------|:-----:|-----:|
| | Video storage location | D:/video |
|  |  | 104857600 |
|  | Page limit | 10000 |
|  | Concurrent Thread | 10 |
|  | StartURL | `https://www.pornhub.com/` |
|  | download | Y | -->

```
 FILE_PATH                ：Video download path.
 VIDEO_DOWNLOAD_SIZE      ：Maximum download size of the video.(Byte)
 MAX_PAGE_SIZE            ：Crawling page size.
 CONCURRENT_THREAD_SIZE   ：Muilti-threads request amount.
 START_URL                ：Crawling url.
 DOWNLOAD_VIDEO           ：Download video. Y or N.
```

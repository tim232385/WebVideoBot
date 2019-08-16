<p align="center"> 
<img src ="https://raw.githubusercontent.com/tim232385/PornBot/master/image/logo.png" />
</p>

# Web Vedeo Bot
  

![java-1.8](https://img.shields.io/badge/java-1.8-green.svg)
[![license: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/tim232385/PornBot/blob/master/LICENSE)
<!--[![Build Status](https://travis-ci.org/tim232385/PornBot.svg?branch=master)](https://travis-ci.org/tim232385/WebVideoBot)
[![HitCount](http://hits.dwyl.io/tim232385/PornBot.svg)](http://hits.dwyl.io/tim232385/PornBot)-->

<!-- [![Code Coverage](https://codecov.io/github/tim232385/PornBot/coverage.svg)](https://codecov.io/gh/tim232385/PornBot)-->
<!-- 
[中文文檔](https://github.com/tim232385/PornBot/blob/master/README_ZH.md) -->

<!-- > Disclaimer: This project is intended to study the Java Crawler4j Framework and the Spring Boot H2 database, it cannot be used for commercial or other personal intentions. If used improperly, it will be the individuals bear. -->
* Easy install,  all dependencies are already included, just need to have a java environment.

![porn_flow](pronbot.flow.svg)

### Environment, Architecture
- Java1.8

- Crawler4j

- Spring Boot x H2 Db

#### How to use
`` git clone https://github.com/tim232385/WebVideoBot.git ``  
`` cd WebVideoBot ``  
`` java -jar PornBot.jar``  
`` **DOWNLOAD_VIDEO default is N**`` [See Default Configuration](https://github.com/tim232385/PornBot/blob/master/config.properties)

![Demo](https://raw.githubusercontent.com/tim232385/PornBot/master/image/5thread.gif)
![h2_console](https://raw.githubusercontent.com/tim232385/PornBot/master/image/h2_console.gif)
### Database Description
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
[See Default Configuration](https://github.com/tim232385/PornBot/blob/master/config.properties


## Stargazers over time

[![Stargazers over time](https://starcharts.herokuapp.com/tim232385/WebVideoBot.svg)](https://starcharts.herokuapp.com/tim232385/WebVideoBot)

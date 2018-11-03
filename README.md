# PornBot


![java-1.8](https://img.shields.io/badge/java-1.8-green.svg)
![license](https://img.shields.io/badge/license-MIT-blue.svg)

[中文文檔](https://github.com/tim232385/PornBot/blob/master/README_ZH.md)

### Introduction 

Pornhub-crawler.
The world's largest adult website  
  
### Getting started

#### Run
``java -jar PornBot.jar``

![Demo](https://raw.githubusercontent.com/tim232385/PornBot/master/image/demo.gif)

#### Database console
``http://localhost:8000/h2-console/``

JDBC URL: ``jdbc:h2:~/porn/porn-db``  

User Name: ``sa``  

Password: ~~empty~~

![h2_console](https://raw.githubusercontent.com/tim232385/PornBot/master/image/h2_console.gif)

### Built With
- Java1.8
- Crawler4j
- Spring x H2 Db

### Config


| Key | Description | Default | 
|--------------|:-----:|-----:|
|FILE_PATH | Video storage location | D:/video |
| VIDEO_DOWNLOAD_SIZE | Video allows to download Byte | 104857600 |
| MAX_PAGE_SIZE | Page limit | 10000 |
| CONCURRENT_THREAD_SIZE | Concurrent Thread | 10 |
| START_URL | StartURL | `https://www.pornhub.com/` |
| DOWNLOAD_VIDEO | download | Y |

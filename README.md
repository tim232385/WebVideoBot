# tw.PornBot


![Coveralls github branch](https://img.shields.io/badge/java-1.8-green.svg)
![Jenkins](https://img.shields.io/badge/license-MIT-blue.svg)

### 簡介

研究爬取網頁影片
研究對象:PornHub

### 環境
- Java1.8
- Crawler4j

### 開始

``java -jar PornBot.jar``

### Demo


![Demo](https://raw.githubusercontent.com/tim232385/PornBot/master/image/demo.gif)





### Config


| Key | Description | Default | 
|--------------|:-----:|-----:| ----:|
|FILE_PATH | 影片存放位置 | D:/video |
| VIDEO_DOWNLOAD_SIZE | 影片允許下載的最大Byte | 104857600 |
| MAX_PAGE_SIZE | 頁數限制 | 10000 |
| CONCURRENT_THREAD_SIZE | 同時運行的Thread數量 | 10 |
| START_URL | 起始URL | `https://www.pornhub.com/` |

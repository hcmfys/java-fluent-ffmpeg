
一个java版本的实现 [node-fluent-ffmpeg](https://github.com/fluent-ffmpeg/node-fluent-ffmpeg).

## 安装
`git clone https://github.com/hcmfys/java-fluent-ffmpeg`

### 安装要求
你需要安装ffmepg

```java 
// Provide an empty string to use default FFmpeg path
import org.springbus.ff.FFmpegCommand;
FFmpegCommand cmd =new FFmpegCommand();
// 指定ffmpeg的路径
cmd =new FFmpegCommand("/path/to/ffmpeg/binary");
```

## 快速开始

和node-fluent-ffmpeg的大部分API有点类似

```java
import org.springbus.ff.FFmpegCommand;

FFmpegCommand ff = new FFmpegCommand();
ff.input("/path/a.mp3");
ff.fpsInput(60);
ff.output("/path/b.mp3");
String rt = ff.run();
System.out.println(rt);
```

````java
FFmpegCommand fFmpegCommand = new FFmpegCommand();
fFmpegCommand.input("D:/v/a.mp3");
fFmpegCommand.fpsInput(60);
fFmpegCommand.startTime("2s");
fFmpegCommand.duration("3s");
fFmpegCommand.output("D:/v/b.mp3");
String rt = fFmpegCommand.run();
System.out.println(rt);
        
````

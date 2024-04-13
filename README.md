 
A Java version of [node-fluent-ffmpeg](https://github.com/fluent-ffmpeg/node-fluent-ffmpeg).

## Installation
`git clone https://github.com/hcmfys/java-fluent-ffmpeg`

### Requirements
You will need FFmpeg installed on your machine, or you can specify a path to a binary:

```java 
// Provide an empty string to use default FFmpeg path
import org.springbus.ff.FFmpegCommand;
FFmpegCommand cmd =new FFmpegCommand();
// Specify a path
cmd =new FFmpegCommand("/path/to/ffmpeg/binary");
```

## Quick Start

Create and run commands using an API similar to node-fluent-ffmpeg:

```java
import org.springbus.ff.FFmpegCommand;

FFmpegCommand ff = new FFmpegCommand();
ff.input("/path/a.mp3");
ff.fpsInput(60);
ff.output("/path/b.mp3");
String rt = ff.run();
System.out.println(rt);
```

 
## Credits

This repo was inspired by [node-fluent-ffmpeg](https://github.com/fluent-ffmpeg/node-fluent-ffmpeg) 


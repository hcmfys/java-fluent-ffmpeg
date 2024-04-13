package com.test;


import org.apache.commons.io.FileUtils;
import org.springbus.ff.FFmpegCommand;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class  Test {

    @org.junit.Test
    public void test1() {
        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.input("D:/v/a.mp3");
        fFmpegCommand.fpsInput(60);
        fFmpegCommand.output("D:/v/b.mp3");
        String rt = fFmpegCommand.run();
        System.out.println(rt);
    }

    @org.junit.Test
    public void test2() {
        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.input("D:/v/a.mp3");
        fFmpegCommand.fpsInput(60);
        fFmpegCommand.startTime("2s");
        fFmpegCommand.duration("3s");
        fFmpegCommand.output("D:/v/b.mp3");
        String rt = fFmpegCommand.run();
        System.out.println(rt);
    }

    @org.junit.Test
    public void test3() {
        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.input("D:/v/1.png");
        fFmpegCommand.size("x400");
        fFmpegCommand.output("D:/v/400.png");
        String rt = fFmpegCommand.run();
        System.out.println(rt);
    }

    @org.junit.Test
    public void test4() {
        String regex = "\\w(\\d\\d)(\\w+)";
        String candidate = "x99SuperJava";
        regex="(\\d+)x?";
        candidate="400x";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(candidate);
        if (matcher.find()) {
            int gc = matcher.groupCount();
            for (int i = 0; i <= gc; i++)
                System.out.println("group " + i + " :" + matcher.group(i));
        }

        String size = candidate;
        Matcher fixedSize = Pattern.compile("(\\d+)x(\\d+)").matcher(size);
        Matcher fixedWidth = Pattern.compile("(\\d+)x\\?").matcher(size);
        Matcher fixedHeight = Pattern.compile("\\?x(\\d+)").matcher(size);
        Matcher percentRatio = Pattern.compile("\\b(\\d{1,3})%").matcher(size);

        if (fixedWidth.matches()) {
            int gc = fixedWidth.groupCount();
            MatchResult result= fixedWidth.toMatchResult();
            System.out.println("width="+result.group(1));

            for (int i = 0; i <= gc; i++)
                System.out.println("group fixed >> " + i + " :" + fixedWidth.group(i));
        }

        if (fixedWidth.find()) {
            int gc = fixedWidth.groupCount();
            for (int i = 0; i <= gc; i++)
                System.out.println("group fixed >> " + i + " :" + fixedWidth.group(i));
        }

    }
    @org.junit.Test
    public void test5() {
        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.input("D:/v/1.png");
        fFmpegCommand.size("640x480","pink");
        fFmpegCommand.output("D:/v/640_pink.png");
        String rt = fFmpegCommand.run();
        System.out.println(rt);
    }

    @org.junit.Test
    public void test6() {
        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.input("/Volumes/f/v/a.jpg");
        fFmpegCommand.debug();
        fFmpegCommand.size("640x480","black");
        fFmpegCommand.output("/Volumes/f/v/pink-black.jpg");
        String rt = fFmpegCommand.run();
        System.out.println(rt);
    }

    @org.junit.Test
    public void test7() throws IOException {
        File file=new File("/Volumes/f/v");
        File[]fs=file.listFiles();

        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.inputOption("-framerate","1/2");
        fFmpegCommand.loop();
        String  baseDir="/Volumes/f/v/toImg/";
        new File(baseDir).mkdirs();
        int index=0;
        if (fs != null) {
            for(File f:fs) {
                if(f.getName().endsWith(".jpg")) {

                    ImageIO.write(ImageIO.read(f),"png",
                            new File(baseDir+index+".png"));
                    index++;
                }
            }
        }
        fFmpegCommand.input(baseDir+"%d.png");
        fFmpegCommand.debug();
        //fFmpegCommand.size("640x480","pink");
        fFmpegCommand.size("720x960","pink");
        fFmpegCommand.outputFPs(30);
        fFmpegCommand.duration(index+"s");
        fFmpegCommand.save("/Volumes/f/v/img_list.mp4");
        String rt = fFmpegCommand.run();
        System.out.println(rt);
    }

}
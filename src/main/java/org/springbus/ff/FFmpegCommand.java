package org.springbus.ff;

import com.google.common.collect.Lists;
import org.springbus.ff.options.input.*;
import org.springbus.ff.options.util.CommandUtil;
import org.springbus.ff.options.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class FFmpegCommand {

    private final CommandUtil commandUtil;
    private final Inputs cmdInputs;
    private final List<String> ffmpegCmds;
    private final Outputs cmdOutputs;
    private final Video cmdVideos;
    private final Audio cmdAudios;
    private boolean showLog;

    public FFmpegCommand(String ffmpegPath) {
        commandUtil = new CommandUtil();
        ffmpegCmds = commandUtil.getFFmpegBinary(ffmpegPath);
        cmdInputs = new Inputs();
        cmdVideos = new Video();
        cmdAudios = new Audio();
        cmdInputs.yes();
        cmdInputs.hideBanner();
        cmdOutputs = new Outputs();
    }

    public FFmpegCommand input(String input) {
        this.cmdInputs.input(input);
        return this;
    }

    public FFmpegCommand inputOption(String options) {
        this.cmdInputs.inputOption(options);
        return this;
    }
    public FFmpegCommand inputOption(String options,String value) {
        this.cmdInputs.inputOption(options);
        this.cmdInputs.inputOption(value);
        return this;
    }

    public FFmpegCommand fpsInput(int fps) {
        this.cmdInputs.fpsInput(fps);
        return this;
    }
    public FFmpegCommand durationInput(String duration) {
        this.cmdInputs.inputDuration(duration);
        return this;
    }

    public FFmpegCommand natives() {
        this.cmdInputs.natives();
        return this;
    }

    public FFmpegCommand noAudio() {
        this.cmdInputs.noAudio();
        return this;
    }

    public FFmpegCommand loop() {
        this.cmdInputs.loop();
        return this;
    }

    public FFmpegCommand startTime(String startTime) {
        this.cmdInputs.setStartTime(startTime);
        return this;
    }

    public FFmpegCommand format(String format) {
        this.cmdInputs.withInputFormat(format);
        return this;
    }

    public FFmpegCommand() {
        this(null);
    }

    // Method to keep Display Aspect Ratio
    public FFmpegCommand keepDAR() {
        List<Filter> filters=Lists.newArrayList(Utils.keepDar());
        videoFilter(filters);
        return this;
    }

    // Method to set the output size
    public FFmpegCommand size(String size) {
        Filter sizeFilter = Utils.createSizeFilters("size", size, false, null);
        videoFilter(Lists.newArrayList(sizeFilter));
        return this;
    }

    public FFmpegCommand size(String size, String color) {
        Filter sizeFilter = Utils.createSizeFilters("size", size, true, color);
        videoFilter(Lists.newArrayList(sizeFilter));
        return this;
    }

    // Method to set aspect ratio
    public FFmpegCommand aspectRatio(float aspect) {
        Filter sizeFilter = Utils.createSizeFilters("aspect", String.valueOf(aspect),
                false, "black");
        videoFilter(Lists.newArrayList(sizeFilter));
        return this;
    }

    public FFmpegCommand aspectRatio(double aspect, String color) {
        Filter sizeFilter = Utils.createSizeFilters("aspect", String.valueOf(aspect),
                true, "color");
        videoFilter(Lists.newArrayList(sizeFilter));
        return this;
    }

    public String run() {
        List<String> cmd = new ArrayList<>();
        cmd.addAll(ffmpegCmds);
        cmd.addAll(cmdInputs.getCurrentInput());
        cmd.addAll(cmdAudios.getCurrentInput());
        cmd.addAll(cmdVideos.getCurrentInput());
        cmd.addAll(Utils.makeComplexFilterCmd(cmdVideos.getVideoFilters()));
        cmd.addAll(cmdOutputs.getCurrentOutput());
        printLog(cmd);
        return commandUtil.runProcess(cmd);
    }

    private void printLog(List<String> cmd) {
        if (showLog) {
            StringBuffer sb = new StringBuffer();
            for (String c : cmd) {
                sb.append(c).append(" ");
            }
            System.out.println(sb);
        }
    }

    public FFmpegCommand map(String sepc) {
        cmdOutputs.map(sepc);
        return this;
    }

    public FFmpegCommand outputFormat(String outputFormat) {
        cmdOutputs.outputFormat(outputFormat);
        return this;
    }

    /**
     * duration
     *
     * @param duration
     * @return
     */
    public FFmpegCommand duration(String duration) {
        cmdOutputs.setDuration(duration);
        return this;
    }

    public FFmpegCommand seek(String seek) {
        cmdOutputs.seekOutput(seek);
        return this;
    }

    public FFmpegCommand output(String savePath) {
        cmdOutputs.output(savePath);
        return this;
    }

    public FFmpegCommand outputOptions(String options) {
        cmdOutputs.outputOptions(options);
        return this;
    }

    public FFmpegCommand outputFPs(int fps) {
        cmdVideos.fpsOutput(String.valueOf(fps));
        return this;
    }

    public FFmpegCommand noVideo() {
        cmdVideos.noVideo();
        return this;
    }

    public FFmpegCommand withFrames(int frames) {
        cmdVideos.withFrames(String.valueOf(frames));
        return this;
    }

    public FFmpegCommand videoCodec(String codec) {
        cmdVideos.videoCodec(String.valueOf(codec));
        return this;
    }

    public FFmpegCommand videoBitrats(String bitrate) {
        cmdVideos.videoBitrats(bitrate, false);
        return this;
    }

    public FFmpegCommand videoFilter(String filters) {
        cmdVideos.videoFilter(filters);
        return this;
    }

    public FFmpegCommand videoFilters(List<String> filters) {
        cmdVideos.videoFilter(filters);
        return this;
    }

    public FFmpegCommand videoFilter(List<Filter> filters) {
        cmdVideos.videoFilters(filters);
        return this;
    }

    public FFmpegCommand videoBitrats(String bitrate, boolean constant) {
        cmdVideos.videoBitrats(bitrate, constant);
        return this;
    }


    public FFmpegCommand audioChannels(String channels) {
        cmdAudios.audioChannels(channels);
        return this;
    }

    public FFmpegCommand audioCodec(String codec) {
        cmdAudios.audioCodec(codec);
        return this;
    }

    public FFmpegCommand audioFrequency(String freq) {
        cmdAudios.audioFrequency(freq);
        return this;
    }

    public FFmpegCommand audioQuality(String quality) {
        cmdAudios.audioQuality(quality);
        return this;
    }

    public FFmpegCommand save(String savePath) {
        cmdOutputs.output(savePath);
        return this;
    }

    public void debug() {
        showLog = true;
    }
}
package org.springbus.ff.options.input;

import com.google.common.collect.Lists;

import java.util.*;

public class Video {

    protected List<String> videos = new ArrayList<>();
    protected List<Filter> curVideoFilters = new ArrayList<>();

    public Video(){}

    public Video withNoVideo() {
        return noVideo();
    }

    public Video noVideo() {
        videos.clear();
        this.curVideoFilters.clear();
        this.videos.add("-vn");
        return this;
    }


    public Video withVideoCodec(String codec) {
        return videoCodec(codec);
    }

    public Video videoCodec(String codec) {
        this.videos.add("-vcodec");
        this.videos.add(codec);
        return this;
    }


    /**
     * Specify video bitrate
     *
     * @param {String|Number} bitrate video bitrate in kbps (with an optional 'k' suffix)
     * @param {Boolean}       [constant=false] enforce constant bitrate
     * @return FfmpegCommand
     * @method FfmpegCommand#videoBitrate
     * @category Video
     * @aliases withVideoBitrate
     */
    public Video withVideoBitrate(String bitrate, boolean constant) {
        return videoBitrats(bitrate, constant);
    }

    public Video videoBitrats(String bitrate, boolean constant) {
        // bitrate = ('' + bitrate).replace(/k?$/, 'k');
        this.videos.add("-b:v");
        this.videos.add(bitrate);
        if (constant) {
            this.videos.add("-maxrate");
            this.videos.add(bitrate);
            this.videos.add("-minrate");
            this.videos.add(bitrate);
            this.videos.add("-bufsize");
            this.videos.add("3M");
        }
        return this;
    }

    public Video videoFilter(String filters) {
         return videoFilter(Lists.newArrayList(filters));
    }

    public Video videoFilter(List<String> filters) {
        List<Filter> curFilter = new ArrayList<>();
        for(String d:filters){
            Map<String, Object> op = new LinkedHashMap<>();
            op.put("raw", d);
            curFilter.add(new Filter("",op));
        }
        return videoFilters(curFilter);
    }
    public Video videoFilters(List<Filter> filters) {
        curVideoFilters.addAll(filters);
        return this;
    }


    /**
     * Specify output FPS
     *
     * @param {Number} fps output FPS
     * @return FfmpegCommand
     * @method FfmpegCommand#fps
     * @category Video
     * @aliases withOutputFps, withOutputFPS, withFpsOutput, withFPSOutput, withFps, withFPS, outputFPS, outputFps, fpsOutput, FPSOutput, FPS
     */
    public Video withOutputFps(String fps) {
        return FPS(fps);
    }

    public Video withOutputFPS(String fps) {
        return FPS(fps);
    }

    public Video withFpsOutput(String fps) {
        return FPS(fps);
    }

    public Video withFPSOutput(String fps) {
        return FPS(fps);
    }

    public Video withFps(String fps) {
        return FPS(fps);
    }

    public Video withFPS(String fps) {
        return FPS(fps);
    }

    public Video outputFPS(String fps) {
        return FPS(fps);
    }

    public Video outputFps(String fps) {
        return FPS(fps);
    }

    public Video fpsOutput(String fps) {
        return FPS(fps);
    }

    public Video FPSOutput(String fps) {
        return FPS(fps);
    }

    public Video fps(String fps) {
        return FPS(fps);
    }

    public Video FPS(String fps) {
        this.videos.add("-r");
        this.videos.add(fps);
        return this;
    }


    /**
     * Only transcode a certain number of frames
     *
     * @param {Number} frames frame count
     * @return FfmpegCommand
     * @method FfmpegCommand#frames
     * @category Video
     * @aliases takeFrames, withFrames
     */
    public Video takeFrames(String frames) {
        return frames(frames);
    }

    public Video withFrames(String frames) {
        return frames(frames);
    }

    public Video frames(String frames) {
        this.videos.add("-vframes");
        this.videos.add(frames);
        return this;
    }

    public List<String> getCurrentInput() {
        return videos;
    }

    public List<Filter> getVideoFilters() {
        return curVideoFilters;
    }

}

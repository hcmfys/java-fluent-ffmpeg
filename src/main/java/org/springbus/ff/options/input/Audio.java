package org.springbus.ff.options.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Audio {

    protected List<String> audios = new ArrayList<>();
    protected List<String> audiosFilters = new ArrayList<>();

    /**
     * Disable audio in the output
     *
     * @return FfmpegCommand
     * @method FfmpegCommand#noAudio
     * @category Audio
     * @aliases withNoAudio
     */
    public Audio(){}

    public Audio withNoAudio() {
        return noAudio();
    }

    public Audio noAudio() {
        this.audios.clear();
        this.audiosFilters.clear();
        this.audios.add("-an");
        return this;
    }

    /**
     * Specify audio codec
     *
     * @param {String} codec audio codec name
     * @return FfmpegCommand
     * @method FfmpegCommand#audioCodec
     * @category Audio
     * @aliases withAudioCodec
     */
    public Audio withAudioCodec(String codec) {
        return audioCodec(codec);
    }

    public Audio audioCodec(String codec) {
        this.audios.add("-acodec");
        this.audios.add(codec);
        return this;
    }


    /**
     * Specify audio bitrate
     *
     * @param {String|Number} bitrate audio bitrate in kbps (with an optional 'k' suffix)
     * @return FfmpegCommand
     * @method FfmpegCommand#audioBitrate
     * @category Audio
     * @aliases withAudioBitrate
     */
    public Audio withAudioBitrate(String bitrate) {
        return audioBitrate(bitrate);
    }

    public Audio audioBitrate(String bitrate) {
        this.audios.add("-b:a");
        this.audios.add(bitrate);
        return this;
    }


    /**
     * Specify audio channel count
     *
     * @param {Number} channels channel count
     * @return FfmpegCommand
     * @method FfmpegCommand#audioChannels
     * @category Audio
     * @aliases withAudioChannels
     */
    public Audio withAudioChannels(String channels) {
        return audioChannels(channels);
    }

    public Audio audioChannels(String channels) {
        this.audios.add("-ac");
        this.audios.add(channels);
        return this;
    }


    /**
     * Specify audio frequency
     *
     * @param {Number} freq audio frequency in Hz
     * @return FfmpegCommand
     * @method FfmpegCommand#audioFrequency
     * @category Audio
     * @aliases withAudioFrequency
     */
    public Audio withAudioFrequency(String freq) {
        return audioFrequency(freq);
    }

    public Audio audioFrequency(String freq) {
        this.audios.add("-ar");
        this.audios.add(freq);
        return this;
    }

    /**
     * Specify audio quality
     *
     * @method FfmpegCommand#audioQuality
     * @category Audio
     * @aliases withAudioQuality
     *
     * @param {Number} quality audio quality factor
     * @return FfmpegCommand
     */
    public Audio withAudioQuality (String quality) {
        return audioQuality(quality);
    }
    public Audio audioQuality (String quality) {
        this.audios.add("-aq");
        this.audios.add(quality);
        return this;
    }

    public  List<String> getCurrentInput() {
        return audios;
    }
}

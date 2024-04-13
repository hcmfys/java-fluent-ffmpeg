package org.springbus.ff.options.input;

import java.util.ArrayList;
import java.util.List;

public class Inputs {

    protected List<String> _currentInput = new ArrayList<>();

    public Inputs withNoAudio() {
        return noAudio();
    }
    public Inputs input(String input){
        this._currentInput.add("-i");
        this._currentInput.add(input);
        return this;
    }
    public Inputs inputOption(String option){
        this._currentInput.add(option);
        return this;
    }
    public Inputs inputDuration(String duraton){
        this._currentInput.add("-t");
        this._currentInput.add(duraton);
        return this;
    }

    public Inputs noAudio() {
        this._currentInput.add("-an");
        return this;
    }

    public Inputs withInputFormat(String format) {
        return fromFormat(format);
    }

    public Inputs fromFormat(String format) {
        this._currentInput.add("-f");
        this._currentInput.add(format);
        return this;
    }

    public Inputs withInputFps(int fps) {
        return FPSInput(fps);
    }

    public Inputs withInputFPS(int fps) {
        return FPSInput(fps);
    }

    public Inputs withFpsInput(int fps) {
        return FPSInput(fps);
    }

    public Inputs withFPSInput(int fps) {
        return FPSInput(fps);
    }

    public Inputs inputFPS(int fps) {

        return FPSInput(fps);
    }

    public Inputs inputFps(int fps) {
        return FPSInput(fps);
    }

    public Inputs fpsInput(int fps) {
        return FPSInput(fps);
    }

    public Inputs FPSInput(int fps) {
        this._currentInput.add("-r");
        this._currentInput.add(String.valueOf(fps));
        return this;
    }

    /**
     * Use native framerate for the last specified input
     *
     * @method FfmpegCommand#native
     * @category Input
     * @aliases nativeFramerate,withNativeFramerate
     *
     * @return FfmmegCommand
     */
    public Inputs nativeFramerate  (){
        return natives();
    }
    public Inputs withNativeFramerate (){
        return natives();
    }
    public Inputs natives(){
        this._currentInput.add("-re");
        return this;
    }


    /**
     * Specify input seek time for the last specified input
     *
     * @method FfmpegCommand#seekInput
     * @category Input
     * @aliases setStartTime,seekTo
     *
     * @param {String|Number} seek time in seconds or as a '[hh:[mm:]]ss[.xxx]' string
     * @return FfmpegCommand
     */
    public Inputs setStartTime (String seek){
        return seekInput(seek);
    }

    public Inputs seekInput (String seek) {
        this._currentInput.add("-ss");
        this._currentInput.add(seek);
        return this;
    }


    /**
     * Loop over the last specified input
     *
     * @method FfmpegCommand#loop
     * @category Input
     *
     * @param {String|Number} [duration] loop duration in seconds or as a '[[hh:]mm:]ss[.xxx]' string
     * @return FfmpegCommand
     */
    public  Inputs loop() {
        this._currentInput.add("-loop");
        this._currentInput.add( "1");
        return this;
    }
    public  Inputs yes() {
        this._currentInput.add("-y");
        return this;
    }
    public  Inputs hideBanner() {
        this._currentInput.add("-hide_banner");
        return this;
    }
    public List<String> getCurrentInput(){
        return  this._currentInput;
    }
}

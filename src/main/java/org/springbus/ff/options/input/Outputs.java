package org.springbus.ff.options.input;

import java.util.ArrayList;
import java.util.List;

public class Outputs {

    public Outputs(){}

    protected List<String> _currentOutput =new ArrayList<>();
    public Outputs seekOutput (String seek){
        return  seek(seek);
    }

    public Outputs seek (String seek) {
        this._currentOutput.add("-ss");
        this._currentOutput.add(seek);
        return this;
    }


    /**
     * Set output duration
     *
     * @method FfmpegCommand#duration
     * @category Output
     * @aliases withDuration,setDuration
     *
     * @param {String|Number} duration in seconds or as a '[[hh:]mm:]ss[.xxx]' string
     * @return FfmpegCommand
     */
    public Outputs withDuration (String duration){
        return duration(duration);

    }
    public Outputs setDuration (String duration){
        return  duration(duration);
    }
    public Outputs duration(String  duration) {
        this._currentOutput.add("-t");
        this._currentOutput.add(duration);
        return this;
    }


    /**
     * Set output format
     *
     * @method FfmpegCommand#format
     * @category Output
     * @aliases toFormat,withOutputFormat,outputFormat
     *
     * @param {String} format output format name
     * @return FfmpegCommand
     */
    public Outputs toFormat (String format){
        return format(format);
    }
    public Outputs withOutputFormat (String format){
        return format(format);
    }
    public Outputs outputFormat (String format){
        return format(format);
    }
    public Outputs format (String format) {
        this._currentOutput.add("-f");
        this._currentOutput.add( format);
        return this;
    };


    /**
     * Add stream mapping to output
     *
     * @method FfmpegCommand#map
     * @category Output
     *
     * @param {String} spec stream specification string, with optional square brackets
     * @return FfmpegCommand
     */
    public Outputs map (String  spec) {
        this._currentOutput.add("-map");
        this._currentOutput.add("'["+ spec +"]'");
        return this;
    }

    public Outputs output (String  out) {
        this._currentOutput.add(out);
        return this;
    }

    public Outputs outputOptions (String  options) {
        this._currentOutput.add(options);
        return this;
    }


    public List<String> getCurrentOutput(){
        return  this._currentOutput;
    }
}
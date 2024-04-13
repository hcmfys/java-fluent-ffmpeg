package org.springbus.ff.options.input;

import java.util.List;
import java.util.Map;

public class Filter {
    private String name;
    private Map<String, Object> options;
    private String input;
    private String output;
    private List<Filter> linkFilterList;

    public Filter(String name, Map<String, Object> options) {
        this.name = name;
        this.options = options;
    }

    public Filter(String name, Map<String, Object> options,String  input,String output) {
        this.name = name;
        this.options = options;
        this.input = input;
        this.output = output;
    }

    public Filter input(List<String> inputs) {
        StringBuilder sb = new StringBuilder();
        for (String in : inputs) {
            sb.append("[");
            sb.append(in);
            sb.append("]");
        }
        this.input = sb.toString();
        return this;
    }

    public Filter input(String input) {
        this.input = "[" + input + "]";
        return this;
    }

    public Filter output(String output) {
        this.input = "\"[" + output + "]\"";
        return this;
    }


    public Filter outputs(List<String> outputs) {
        StringBuilder sb = new StringBuilder();
        for (String in : outputs) {
            sb.append("[");
            sb.append(in);
            sb.append("]");
        }
        this.output = sb.toString();
        return this;
    }

    /**
     * get field
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * set field
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get field
     *
     * @return options
     */
    public Map<String, Object> getOptions() {
        return this.options;
    }

    /**
     * set field
     *
     * @param options
     */
    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    /**
     * get field
     *
     * @return input
     */
    public String getInput() {
        return this.input;
    }

    /**
     * set field
     *
     * @param input
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * get field
     *
     * @return output
     */
    public String getOutput() {
        return this.output;
    }

    /**
     * set field
     *
     * @param output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    public  String  getValue(String key) {
        if(options==null){
            return null;
        }
        return (String) options.getOrDefault(key,null);
    }

    public  String  getRaw() {
        return getValue("raw");
    }

    public  void  setLinkFilterList(List<Filter> linkFilterList){
        this.linkFilterList=linkFilterList;
    }
    public List<Filter> getLinkFilterList(){
        return linkFilterList;
    }

}

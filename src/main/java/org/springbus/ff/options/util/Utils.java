package org.springbus.ff.options.util;

import org.apache.commons.lang3.StringUtils;
import org.springbus.ff.options.input.Filter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String makeFilterString(List<Filter> filters) {
        return  makeFilterString(filters,";");
    }
    public static String makeFilterString(List<Filter> filters,String dot) {
        StringJoiner joiner = new StringJoiner(dot);
        for (Filter f : filters) {
            String raw = f.getRaw();
            if (StringUtils.isNotEmpty(raw)) {
                joiner.add(raw);
            }else if(f.getLinkFilterList()!=null&& !f.getLinkFilterList().isEmpty()){
                joiner.add(makeFilterString(f.getLinkFilterList(),","));
            }else {
                StringBuffer sbFilter = new StringBuffer();
                String name = f.getName();
                String input = f.getInput();
                String output = f.getOutput();
                if (StringUtils.isNotEmpty(input)) {
                    sbFilter.append(input);
                }
                sbFilter.append(name).append("=");
                sbFilter.append(getFilterByKeys(f));
                if (StringUtils.isNotEmpty(output)) {
                    sbFilter.append(output);
                }
                joiner.add(sbFilter);
            }
        }
        return joiner.toString();
    }

    private static String getFilterByKeys(Filter f) {
        Map<String, Object> op = f.getOptions();
        Set<String> keys = op.keySet();
        StringJoiner keyJoin = new StringJoiner(":");
        for (String k : keys) {
            Object o = op.get(k);
            String par;
            if (o instanceof String) {
                par = k + "='" + o + "'";
            } else {
                par = k + "=" + o;
            }
            keyJoin.add(par);
        }
        return keyJoin.toString();
    }

    /**
     * Return filters to pad video to width*height.
     *
     * @param width  output width
     * @param height output height
     * @param aspect video aspect ratio (without padding)
     * @param color  padding color
     * @return List of scale/pad filters
     */
    private static Filter getScalePadFilters(int width, int height, double aspect, String color) {
        List<Filter> filters = new ArrayList<>();

        // scale filter
        Map<String, Object> scaleOptions = new HashMap<>();
        scaleOptions.put("w", "if(gt(a," + aspect + ")," + width + ",trunc(" + height + "*a/2)*2)");
        scaleOptions.put("h", "if(lt(a," + aspect + ")," + height + ",trunc(" + width + "/a/2)*2)");
        filters.add(new Filter("scale", scaleOptions));

        // pad filter
        Map<String, Object> padOptions = new HashMap<>();
        padOptions.put("w", width);
        padOptions.put("h", height);
        padOptions.put("x", "if(gt(a," + aspect + "),0,(" + width + "-iw)/2)");
        padOptions.put("y", "if(lt(a," + aspect + "),0,(" + height + "-ih)/2)");
        padOptions.put("color", color);
        filters.add(new Filter("pad", padOptions));
        Filter filter = new Filter("link", null);
        filter.setLinkFilterList(filters);
        return filter;
    }

    public static Filter createSizeFilters(String key, String value) {
        return createSizeFilters(key, value, false, null);
    }


    /**
     * Recompute size filters.
     *
     * @param key newly-added parameter name ('size', 'aspect' or 'pad')
     * @return List of filter objects
     */
    public static Filter createSizeFilters(
            String key, String value, boolean pad, String color) {
        Matcher fixedSize = Pattern.compile("(\\d+)x(\\d+)").matcher(value);
        Matcher fixedWidth = Pattern.compile("(\\d+)x?").matcher(value);
        Matcher fixedHeight = Pattern.compile("\\??x(\\d+)").matcher(value);
        Matcher percentRatio = Pattern.compile("\\b(\\d{1,3})%").matcher(value);

        if (percentRatio.matches()) {
            double ratio = Double.parseDouble(percentRatio.toMatchResult().group(1)) / 100;
            Map<String, Object> options = new HashMap<>();
            options.put("w", "trunc(iw*" + ratio + "/2)*2");
            options.put("h", "trunc(ih*" + ratio + "/2)*2");
            return new Filter("scale", options);

        } else if (fixedSize.matches()) {
            int width = Integer.parseInt(fixedSize.toMatchResult().group(1));
            int height = Integer.parseInt(fixedSize.toMatchResult().group(2));
            double aspect = width / (double) height;

            if (pad) {
                return getScalePadFilters(width, height, aspect, color);
            } else {
                Map<String, Object> options = new HashMap<>();
                options.put("w", String.valueOf(width));
                options.put("h", String.valueOf(height));
                return new Filter("scale", options);

            }
        } else if (fixedWidth.matches() || fixedHeight.matches()) {
            int width, height;
            if (key.equals("aspect")) {
                double aspect = Double.parseDouble(value);
                width = fixedWidth.matches() ?
                        Integer.parseInt(fixedWidth.toMatchResult().group(1)) :
                        (int) (Integer.parseInt(fixedHeight.toMatchResult().group(1)) * aspect);
                height = fixedHeight.matches() ?
                        Integer.parseInt(fixedHeight.toMatchResult().group(1)) :
                        (int) (Integer.parseInt(fixedWidth.toMatchResult().group(1)) / aspect);
                if (pad) {
                    return getScalePadFilters(width, height, aspect, color);
                } else {
                    Map<String, Object> options = new HashMap<>();
                    options.put("w", width);
                    options.put("h", height);
                    return new Filter("scale", options);
                }
            } else {
                // Keep input aspect ratio
                Map<String, Object> options = new HashMap<>();
                if (fixedWidth.matches()) {
                    options.put("w", Integer.parseInt(fixedWidth.toMatchResult().group(1)));
                    options.put("h", "trunc(ow/a/2)*2");
                } else {
                    options.put("w", "trunc(oh*a/2)*2");
                    options.put("h", Integer.parseInt(fixedHeight.toMatchResult().group(1)));
                }
                return new Filter("scale", options);
            }
        } else {
            throw new IllegalArgumentException("Invalid size specified: " + value);
        }
    }

    public static List<String> makeComplexFilterCmd(List<Filter> filters) {
        List<String> filter = new ArrayList<>();
        if (filters != null && !filters.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            filter.add("-filter_complex");
            sb.append(makeFilterString(filters).trim());
            filter.add(sb.toString().trim());
        }
        return filter;
    }

    public static Filter keepDar() {
        Filter linkF = new Filter("link", null);

        Map<String, Object> opScale = new LinkedHashMap<>();
        opScale.put("w", "if(gt(sar,1),iw*sar,iw)");
        opScale.put("h", "if(lt(sar,1),ih/sar,ih)");
        Filter scaleFilter = new Filter("scale", opScale);
        Map<String, Object> opSar = new LinkedHashMap<>();
        opSar.put("1", null);
        Filter sarFilter = new Filter("scale", opSar);
        List<Filter> filters = new ArrayList<>();
        filters.add(scaleFilter);
        filters.add(sarFilter);
        linkF.setLinkFilterList(filters);
        return linkF;

    }
}

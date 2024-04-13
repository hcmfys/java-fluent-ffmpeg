package org.springbus.ff.options.util;


import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CommandUtil {
    private static final Logger log = LoggerFactory.getLogger(CommandUtil.class);

    public CommandUtil() {
        checkEnv();
    }

    /**
     * runProcess
     *
     * @return
     * @throws Exception
     */
    public String runProcess(List<String> commands) {
        ProcessBuilder pb = null;
        Process process = null;

        if (log.isDebugEnabled())
            log.debug("start to run ffmpeg process... cmd : '{}'", commands);
        Stopwatch stopwatch = Stopwatch.createStarted();
        pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        try {
            process = pb.start();
           // log.info("ffmpeg exit code={}",process.exitValue());
            @Cleanup BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(),
                            StandardCharsets.UTF_8));
            List<String> buffer = Lists.newArrayList();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.add(line);
            }
            return String.join("\n", buffer);
        } catch (Exception e) {
            log.error("errorStream:", e);
        } finally {
            if (null != process) {
                try {
                    process.getInputStream().close();
                    process.getOutputStream().close();
                    process.getErrorStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            int flag = process.waitFor();
            if (flag != 0) {
                throw new IllegalThreadStateException("process exit with error value : " + flag);
            }
        } catch (InterruptedException e) {
            log.error("wait for process finish error", e);
        } finally {
            if (null != process) {
                process.destroy();
                pb = null;
            }

            stopwatch.stop();
        }
        if (log.isInfoEnabled()) {
            log.info("ffmpeg run {} seconds, {} milliseconds",
                    stopwatch.elapsed(TimeUnit.SECONDS),
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
        return null;
    }

    private void checkEnv() {
        String env = System.getProperty("os.name");
        log.debug("current operate system :{}", env);

        if (StringUtils.isNotEmpty(env)) {
            String os = env.toLowerCase();
            if (os.contains("win")) {
                isWin = true;
            } else if (os.contains("linux") || os.contains("mac")) {
                isWin = false;
            }
        }
    }

    /**
     * 得到ffmpeg命令参数
     *
     * @return
     */
    public List<String> getFFmpegBinary(String ffmpegPah) {
        if (ffmpegPah == null) {
            ffmpegPah = FFMPEG;
        }
        if (ffmpegBinary == null) {
            if (isWin) {
                ffmpegBinary = Lists.newArrayList(WINCMD, WINCMDOP, ffmpegPah);
            } else {
                ffmpegBinary = Lists.newArrayList(ffmpegPah);
            }
        }
        return ffmpegBinary;
    }

    /**
     * get ffprobe cmd
     *
     * @return
     */
    public List<String> getFFprobeBinary() {
        if (null == ffprobeBinary) {
            if (isWin) {
                ffprobeBinary = Lists.newArrayList(WINCMD, WINCMDOP, FFPROBE);
            } else {
                ffprobeBinary = Lists.newArrayList(LINUXCMD, FFPROBE);
            }
        }
        return ffprobeBinary;
    }


    private static boolean isWin = false;
    private static List<String> ffmpegBinary;
    private static List<String> ffprobeBinary;

    public static final String WINCMD = "cmd";
    public static final String WINCMDOP = "/c";
    public static final String LINUXCMDOP = "-c";
    public static final String LINUXCMD = "/bin/sh";
    public static final String FFMPEG = "ffmpeg";
    public static final String FFPROBE = "ffprobe";
}
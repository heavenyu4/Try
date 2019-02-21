package com.thunder.as31try.utils;


import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lvzhenwei
 * @version 1.0
 * @description 日志信息工具类
 * @date 2012-3-31 上午10:20:38
 * @update 2012-3-31 上午10:20:38
 */

public class Logger {
	final private static String TAG = "Logger";
	private static boolean enable_debug = true;
	private static boolean debug_able = true;
	private static boolean info_able = true;
	private static boolean verbose_able = false;
	private static boolean error_able = true;
	private static boolean file_able = true;
	private static final Format FORMAT = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS ", Locale.getDefault());
	private static final Format FORMAT_DAY = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
	private static final Format FORMAT_SECOND = new SimpleDateFormat("yy-MM-dd-HH-mm-ss", Locale.getDefault());

	private static ExecutorService sExecutor;
	private static String logDirName = "/storage/emulated/0/log/";
	private static String packname = "com.thunder.as31try";
	enum ERR_LEVEL{
        V,
        I,
        D,
        E
    }

	public static void setDebugEnable(boolean enable) {
		enable_debug = enable;
	}

	public static void debug(String msg) {
		debug(TAG, msg);
	}

	public static void debug(String tag, String msg) {
		if (enable_debug && debug_able) {
			Log.d(tag, msg);
			if (file_able) {
				print2File(ERR_LEVEL.D, tag, msg);
			}
		}
	}

	public static void info(String s) {
		info(TAG, s);
	}

	public static void info(String tag, String s) {
		if (enable_debug && info_able) {
			Log.i(tag, s);
			if (file_able) {
				print2File(ERR_LEVEL.I, tag, s);
			}
		}

	}

	public static void verbose(String s) {
		verbose(TAG, s);

	}

	public static void verbose(String tag, String s) {
		if (enable_debug && verbose_able) {
			Log.v(tag, s);
			if (file_able) {
				print2File(ERR_LEVEL.V, tag, s);
			}
		}

	}

	public static void error(String s) {
		error(TAG, s);
	}

	public static void error(String tag, String s) {
		if (enable_debug && error_able) {
			Log.e(tag, s);
			if (file_able) {
				print2File(ERR_LEVEL.E, tag, s);
			}
		}
	}

	public static void error(Throwable e) {
		error("", e);
	}

	public static void error(String msg, Throwable e) {
		if (enable_debug && error_able) {
			Log.e(TAG, msg, e);
			if (file_able) {
				print2File(ERR_LEVEL.E, TAG, msg + "\n" + Log.getStackTraceString(e));
			}
		}
	}

	public static void error(String tag, String msg, Throwable e) {
		if (enable_debug && error_able) {
			Log.e(tag, msg, e);
			if (file_able) {
				print2File(ERR_LEVEL.E, tag, msg + "\n" + Log.getStackTraceString(e));
			}
		}
	}

	public static void print2File(ERR_LEVEL level, final String tag, final String msg) {

		String logFileName = "";

		Date now = new Date(System.currentTimeMillis());
		String format = FORMAT.format(now);
		String dayFormat = FORMAT_DAY.format(now);
		logFileName = dayFormat + "-" + packname + ".log";
//        final String fullPath = "sdcard/com.thunder.ktv/log/" + logDirName + "/" + logFileName;
		final String fullPath = logDirName + logFileName;

		if (!createOrExistsFile(fullPath)) {
			Log.e(TAG, "create file " + fullPath + " failed!");
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(format)
//                .append(T[type - V])
//                .append("/")
//				.append("\t")
				.append("  " +packname + ":\t")
                .append(level+"/")
				.append(tag)
				.append(":\t")
				.append(msg)
				.append("\n");
		final String content = sb.toString();
		if (sExecutor == null) {
			sExecutor = Executors.newSingleThreadExecutor();
		}
		sExecutor.execute(new Runnable() {
			@Override
			public void run() {
				BufferedWriter bw = null;
				try {
					bw = new BufferedWriter(new FileWriter(fullPath, true));
					bw.write(content);
//                    Log.d(tag, "log to " + fullPath + " success!");
				} catch (IOException e) {
					e.printStackTrace();
					Log.e(tag, "log to " + fullPath + " failed!");
				} finally {
					try {
						if (bw != null) {
							bw.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private static boolean createOrExistsFile(final String filePath) {
		File file = new File(filePath);
		if (file.exists()) return file.isFile();
		if (!createOrExistsDir(file.getParentFile())) return false;
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean createOrExistsDir(final File file) {
		return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
	}

	private static boolean isSpace(final String s) {
		if (s == null) return true;
		for (int i = 0, len = s.length(); i < len; ++i) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除一周前的log
	 */
	public static void delWeekBeforeFile() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		final String week_befor_sdf = FORMAT_DAY.format(calendar.getTime());

		File file = new File(logDirName);
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().substring(0, week_befor_sdf.length()).compareTo(week_befor_sdf) < 0) {
					return true;
				} else {
					return false;
				}
			}
		});
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
				Logger.debug(TAG, "delWeekBeforeFile: " + files[i].getAbsolutePath() + " deleted!");
			}
		}
	}

}

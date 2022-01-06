package com.tcn.cosmoslibrary.management;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author TheCosmicNebula_
 */
@SuppressWarnings("unused")
public class CosmosConsoleManager {
	
	private enum LEVEL {
		
		PRINT(-1, "print", "Print", " [PRINT] ", Level.INFO),
		INFO(0, "info", "Info", " [INFO] ", Level.INFO),
		DEBUG(1, "debug", "Debug", " [DEBUG] ", Level.DEBUG),
		STARTUP(2, "startup", "Startup", " [STARTUP] ", Level.INFO),
		SHUTDOWN(2, "shutdown", "Shutdown", " [SHUTDOWN] ", Level.INFO),
		WARNING(3, "warning", "Warning", " [WARNING] ", Level.WARN),
		FATAL(4, "fatal", "Fatal", " [FATAL] ", Level.FATAL);
		
		private int index;
		private String simple_name;
		private String display_name;
		private String console_name;
		private Level log_level;
		
		private LEVEL(int indexIn, String simple_nameIn, String display_nameIn, String console_nameIn, Level levelIn) {
			index = indexIn;
			simple_name = simple_nameIn;
			display_name = display_nameIn;
			console_name = console_nameIn;
			log_level = levelIn;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getSimpleName() {
			return this.simple_name;
		}
		
		public String getDisplayName() {
			return this.display_name;
		}
		
		public String getConsoleName() {
			return this.console_name;
		}
		
		public Level getLevel() {
			return this.log_level;
		}
	}

	public static final Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID;
	public boolean debugEnabled;
	public boolean infoEnabled;
	
	public CosmosConsoleManager(String modId, boolean debugEnabledIn, boolean infoEnabledIn) {
		MOD_ID = modId;
		this.debugEnabled = debugEnabledIn;
		this.infoEnabled = infoEnabledIn;
	}
	
	public CosmosConsoleManager getInstance() {
		return this;
	}
	
	public void print(Object object) {
		message(LEVEL.PRINT, object);
	}
	
	public void info(Object object) {
		message(LEVEL.INFO, object);
	}

	public void debug(Object object) {
		message(LEVEL.DEBUG, object);
	}

	public void startup(Object object) {
		message(LEVEL.STARTUP, object);
	}

	public void shutdown(Object object) {
		message(LEVEL.SHUTDOWN, object);
	}

	public void warning(Object object) {
		message(LEVEL.WARNING, object);
	}

	public void fatal(Object object) {
		message(LEVEL.FATAL, object);
	}
	
	private void message(LEVEL level, Object object) {
		message(level, object.toString());
	}
	
	private void message(LEVEL level, String message) {
		message(level, message, null);
	}

	public void message(LEVEL level, Object object, Throwable t) {
		if (level.equals(LEVEL.DEBUG)) {
			if (this.debugEnabled) {
				LOGGER.log(level.getLevel(), "[" + MOD_ID + "]" + level.getConsoleName() + "[" + getSimpleCallerCallerClassName() + "] " + object, t);
			}
		} else if (level.equals(LEVEL.INFO)) {
			if (this.infoEnabled) {
				LOGGER.log(level.getLevel(), "[" + MOD_ID + "]" + level.getConsoleName() + "[" + getSimpleCallerCallerClassName() + "] " + object, t);
			}
		} else {
			LOGGER.log(level.getLevel(), "[" + MOD_ID + "]" + level.getConsoleName() + "[" + getSimpleCallerCallerClassName() + "] " + object, t);
		}
	}
	
	/**
	 * Gets the current time.
	 * @return The current time in the format: [YYYY-MM-DD | HH-MM-SS]
	 */
	public String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		return dtf.format(now).replace("/", "-").replace(" ", " | ");
	}
	
	/**
	 * Method to access the current class.
	 * @return String [full.class.name]
	 */
	public String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(CosmosConsoleManager.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return ste.getClassName().replace("_", "\\.");
			}
		}
		return null;
	}
	
	/**
	 * Method to return the simple class name of the current class.
	 * @return String [simpleclassname]
	 */
	public String getSimpleCallerClassName() {
		String c = getCallerClassName();
		String[] split = c.split("\\.");
		int last = (split.length - 1);
		return split[last];
	}

	/**
	 * Method to return the class name of the class calling the method;
	 * @return String [full.class.name]
	 */
	public String getCallerCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		String callerClassName = null;
		
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(CosmosConsoleManager.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				if (callerClassName == null) {
					callerClassName = ste.getClassName().replace("_", "\\.");
				} else if (!callerClassName.equals(ste.getClassName())) {
					return ste.getClassName().replace("_", "\\.");
				}
			}
		}
		return null;
	}
	
	/**
	 * Method to return the simple class name of the class calling the method.
	 * @return String [simpleclassname]
	 */
	public String getSimpleCallerCallerClassName() {
		String c = getCallerCallerClassName();
		String[] split = c.split("\\.");
		int last = (split.length - 1);
		return split[last];
	}
}
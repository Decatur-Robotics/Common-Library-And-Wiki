package frc.lib.core.util;

import com.google.gson.Gson;

public class FileUtils {

	private static final Gson Gson = new Gson();

	/**
	 * Don't do circular references.
	 */
	public static String toJson(Object object) {
		return Gson.toJson(object);
	}

	public static <T> T fromJson(String json, Class<T> dataType) {
		// I'm not sure (Class<T>) Object.class what is, but I'm trusting it.
		return Gson.fromJson(json, dataType);
	}

}
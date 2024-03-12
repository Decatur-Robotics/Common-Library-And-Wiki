package frc.lib.core.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.Scanner;

public class FileUtils {

	private static final Gson Gson = new Gson();

	/**
	 * Don't do circular references.
	 * <p>
	 * Ex: If you do: class A { A b; } A a = new A(); a.b = a; You will get a StackOverflowError.
	 */
	public static String toJson(Object object) {
		return Gson.toJson(object);
	}

	public static <T> T fromJson(String json, Class<T> dataType) {
		// I'm not sure (Class<T>) Object.class what is, but I'm trusting it.
		return Gson.fromJson(json, dataType);
	}

	/**
	 * Reads a json file and returns it as json.
	 * @param filePath The file to read from.
	 * @return The JsonObject returned from the file.
	 * @throws FileNotFoundException Throws an error if the file does not exist.
	 */
	public static <T> T readJsonFromFilePath(String filePath, Class<T> classType) throws FileNotFoundException {
		return readJsonFromFile(new File(filePath), classType);
	}

	/**
	 * Reads a json file and returns it as json.
	 * @param file The file to read from.
	 * @return The JsonObject returned from the file.
	 * @throws FileNotFoundException Throws an error if the file does not exist.
	 */
	public static <T> T readJsonFromFile(File file, Class<T> classType) throws FileNotFoundException {
		if (file.exists()) {

			Scanner fileReader = new Scanner(file);
			StringBuilder stringBuilder = new StringBuilder();

			while (fileReader.hasNextLine()) {
				stringBuilder.append(fileReader.nextLine());
			}

			fileReader.close();

			return fromJson(stringBuilder.toString(), classType);

		} else {
			throw new FileNotFoundException("File at " + file.getPath() + "does not exist.");
		}
	}


	public static void saveJsonToFile(String path, Object json) {

		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(toJson(json));

			fileWriter.close();

		} catch (IOException ignored) {}
	}


}
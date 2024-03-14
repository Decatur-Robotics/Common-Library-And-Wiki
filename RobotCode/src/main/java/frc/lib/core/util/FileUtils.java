package frc.lib.core.util;

import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class FileUtils {

	private static final Gson Gson = new Gson();
	private static String toJson(Object object) {
		return Gson.toJson(object);
	}

	private static <T> T fromJson(String json, Class<T> dataType) {
		return Gson.fromJson(json, dataType);
	}

	/**
	 * Reads a json file and returns it as an object.
	 * Don't do circular references.
	 * <p>
	 * Ex: If you do: class A { A b; } A a = new A(); a.b = a; You will get a StackOverflowError.
	 * @param filePath The file path to read from.
	 * @return The JsonObject returned from the file.
	 * @throws FileNotFoundException Throws an error if the file does not exist.
	 */
	public static <T> T readObjectFromFilePath(String filePath, Class<T> classType) throws FileNotFoundException {
		return readObjectFromFile(new File(filePath), classType);
	}

	/**
	 * Reads a json file and returns it as an object.
	 * Don't do circular references.
	 * <p>
	 * Ex: If you do: class A { A b; } A a = new A(); a.b = a; You will get a StackOverflowError.
	 * @param file The file to read from.
	 * @return The Object returned from the file.
	 * @throws FileNotFoundException Throws an error if the file does not exist.
	 */
	public static <T> T readObjectFromFile(File file, Class<T> classType) throws FileNotFoundException {
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

	/**
	 * Saves an object as a json file.
	 * Don't do circular references.
	 * <p>
	 * Ex: If you do: class A { A b; } A a = new A(); a.b = a; You will get a StackOverflowError
	 * @param path The file path to save to.
	 * @param object The object to save to the file.
	 */
	public static void saveObjectToFile(String path, Object object) {

		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(toJson(object));

			fileWriter.close();

		} catch (IOException ignored) {}
	}


}
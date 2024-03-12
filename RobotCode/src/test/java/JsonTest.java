import com.google.gson.JsonObject;
import frc.lib.core.ILogSource;
import frc.lib.core.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

class JsonTest implements ILogSource {

	private int x;

//	@Test
	void testJson() {
		x = 5;

//		String json = FileUtils.toJson(this);
//		logInfo("json: " + json);
//		JsonTest obj = FileUtils.fromJson(json, JsonTest.class);
//		logInfo("obj.x: " + obj.x);
//		logInfo("x: " + x);
//
//		FileUtils.saveJsonToFile("jsonTest.json", FileUtils.fromJson(json, JsonObject.class));

		FileUtils.saveJsonToFile("jsonTest.json", this);

		int numberRead = 0;
		try {
			numberRead = FileUtils.readJsonFromFilePath("jsonTest.json", JsonTest.class).x;
		} catch(FileNotFoundException ignored) {}

//		logInfo("original json: " + json);
		logInfo("Number read: " + numberRead);
//		assert Objects.equals(json, readJson);
//		assert obj.x == x;

		new File("jsonTest.json").delete();


		assert numberRead == 5;
	}

}

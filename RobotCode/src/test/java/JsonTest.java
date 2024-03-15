import frc.lib.core.ILogSource;
import frc.lib.core.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;

class JsonTest implements ILogSource {

	private int x;

//	@Test
	void testJson() {
		x = 5;

		FileUtils.saveObjectToFile("jsonTest.json", this);

		int numberRead = 0;
		try {
			numberRead = FileUtils.readObjectFromFilePath("jsonTest.json", JsonTest.class).x;
		} catch(FileNotFoundException ignored) {}

		logInfo("Number read: " + numberRead);

		new File("jsonTest.json").delete();


		assert numberRead == 5;
	}

}

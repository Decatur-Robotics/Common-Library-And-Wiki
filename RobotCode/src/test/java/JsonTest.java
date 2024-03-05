import frc.lib.core.ILogSource;
import frc.lib.core.util.FileUtils;
import org.junit.jupiter.api.Test;

class JsonTest implements ILogSource {

	private int x;

	@Test
	void testJson() {
		x = 5;

		String json = FileUtils.toJson(this);
		logFine("json: " + json);
		JsonTest obj = FileUtils.fromJson(json, JsonTest.class);
		logFine("obj.x: " + obj.x);
		logFine("x: " + x);

		assert obj.x == x;
	}

}

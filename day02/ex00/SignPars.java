import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class SignPars {

	private int countBytes(String key) {
		int count = 0;

		Scanner keyParser = new Scanner(key).useRadix(16);

		while (keyParser.hasNextShort()) {
			keyParser.nextShort();
			count++;
		}
		keyParser.close();
		return (count);
	}

	private short[] convertKey(String line){
		int count = countBytes(line);
		short[] keys = new short[count];
		Scanner keyParser = new Scanner(line).useRadix(16);
		for(int i = 0; i < count; i++){
			keys[i] = keyParser.nextShort();
		}
		return keys;
	}

	public Map<short[], String> getKey(FileInputStream sign) {
		String line = null;
		Map<short[], String> newMap = new HashMap<>();
		Scanner scanner = new Scanner(sign);
		if (scanner.hasNextLine())
			line = scanner.nextLine();
		else
			line = "";
		while (!line.isEmpty()){
			short[] key = convertKey(line.substring(line.indexOf(',') + 2));
			String val = line.substring(0, line.indexOf(","));
			if(key.length > 0 && !val.isEmpty())
				newMap.put(key, val);
			if (scanner.hasNextLine())
				line    = scanner.nextLine();
			else
				line = "";
		}
		try {
			sign.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newMap;
	}
}

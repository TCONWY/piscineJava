import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileParser {
	public List<String> getRes (Map<short[], String> map) {
		Scanner scanner = new Scanner(System.in);
		List<String> ret = new ArrayList<>();
		String in = scanner.nextLine();

		while (!in.equals("42")) {
			boolean proces = false;
			for (short[] key : map.keySet()) {
				try {
					FileInputStream fileScanner = new FileInputStream(in);
					short[] fileCon = getByte(key.length, fileScanner);
					fileScanner.close();
					if (comBytes(key, fileCon)){
						ret.add(map.get(key));
						proces = true;
						break;
					}
					fileScanner.close();
				} catch (IOException e) {
					System.out.print("");
					proces = false;
				}
			}
			if (proces)
				System.out.println("PROCESSED");
			else
				System.out.println("UNDEFINED");
			in = scanner.nextLine();
		}

		return ret;
	}


	public short[] getByte(int len, FileInputStream fileScan){
		short[] bytes = new short[len];
		for (int i = 0; i < len; i++) {
			try {
				bytes[i] = (short)fileScan.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	public boolean comBytes(short[] key, short[] fileCon) {
		for (int i = 0; i < key.length; i++) {
			if (key[i] != fileCon[i])
				return (false);
		}
		return true;
	}
}

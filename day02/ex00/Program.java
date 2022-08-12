import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Program {

	private static final String SIGNATURES = "/Users/tconwy/Desktop/qqq/java-piscine/src/day02/ex00/signatures.txt";
	private static final String RESULT = "/Users/tconwy/Desktop/qqq/java-piscine/src/day02/ex00/result.txt";

	public static void main(String[] args) throws IOException {

		SignPars signatureParser = new SignPars();

		Map<short[], String> parsedSignatures = signatureParser.getKey(new FileInputStream(SIGNATURES));

		List<String> result = new FileParser().getRes(parsedSignatures);

		saveResult(result);
	}

	public static void saveResult(List<String> result) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(RESULT);

		for (String line : result) {
			outputStream.write((line + '\n').getBytes(StandardCharsets.UTF_8));
		}
		outputStream.close();
	}
}
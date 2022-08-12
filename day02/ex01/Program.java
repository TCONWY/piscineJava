import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Program {
	public static void main(String[] args) throws IOException {
		if(args.length != 2){
			System.out.println("error argument");
			System.exit(-1);
		}
		FileReader fileA = null;
		FileReader fileB = null;
		FileWriter dict = null;
		fileA = new FileReader(args[0]);
		fileB = new FileReader(args[1]);
		dict = new FileWriter("dictionary.txt");
		BufferedReader buffA = new BufferedReader(fileA);
		ArrayList<Word> fileAOccur = arrayStream(buffA);
		BufferedReader buffB = new BufferedReader(fileB);
		ArrayList<Word> fileBOccur = arrayStream(buffB);
		fillFrom(fileAOccur, fileBOccur);
		fillFrom(fileBOccur, fileAOccur);

		sort(fileAOccur);
		sort(fileBOccur);

		buffA.close();
		buffB.close();
		for (int i = 0; i < fileAOccur.size(); i++){
			if (!fileAOccur.get(i).body.isEmpty()) {
				dict.flush();
				dict.write(fileAOccur.get(i).body + "\n");
			}
		}
		dict.close();
		double res1 = res(fileAOccur, fileBOccur);

		BigDecimal result = new BigDecimal(res1);
		result = result.setScale(2, RoundingMode.DOWN);
		System.out.println("Similarity = " + result);
	}

	public static double res(ArrayList<Word> dictA, ArrayList<Word> dictB){
		double doub = 0;
		for (int i = 0; i < dictA.size(); i++){
			doub += (long) dictA.get(i).occurrence * dictB.get(i).occurrence;
		}
		long sumOne = 0;
		for (int counter = 0; counter < dictA.size(); counter++) {
			sumOne += (long) dictA.get(counter).occurrence * dictA.get(counter).occurrence;
		}
		long sumTwo = 0;
		for (int counter = 0; counter < dictB.size(); counter++) {
			sumTwo += (long) dictB.get(counter).occurrence * dictB.get(counter).occurrence;
		}
		Double res = Math.sqrt(sumOne) * Math.sqrt(sumTwo);
		if (res == 0){
			return 0;
		}
		res = res * 100;
		res = Math.floor(res);
		res = res / 100;
		return (doub / (res));
	}

	public static ArrayList<Word> arrayStream(BufferedReader bufferedReader) throws IOException {
		ArrayList<Word> lst = new ArrayList<Word>();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			String[] words = line.split(" ");
			for (int i = 0; i < words.length; i++){
				boolean exit = false;
				for (int j = 0; j < lst.size(); j++){
					if (lst.get(j).body.equals(words[i])){
						lst.get(j).occurrence++;
						exit = true;
						break;
					}
				}
				if (!exit){
					lst.add(new Word(words[i], 1));
				}
			}
		}
		bufferedReader.close();
		return lst;
	}

	public  static void fillFrom(ArrayList<Word> dictOne, ArrayList<Word> dictTwo) throws IOException{
		for (int i = 0; i < dictOne.size(); i++){
			boolean exit = false;
			for (int j = 0 ;j < dictTwo.size(); ++j){
				if (dictOne.get(i).body.equals(dictTwo.get(j).body)){
					exit = true;
					break;
				}
			}
			if (!exit)
				dictTwo.add(new Word(dictOne.get(i).body, 0));
		}
	}
	public  static void sort(ArrayList<Word> dict) {
		for (int i = 0; i < dict.size(); i++){
			for (int j = 0; j < dict.size() - 1; j++){
				if (dict.get(j).body.compareTo(dict.get(j + 1).body) > 0){
					Word tmp = dict.get(j);
					dict.set(j, dict.get(j+1));
					dict.set((j+1), tmp);
				}
			}
		}
	}
}

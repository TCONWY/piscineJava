import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ParsUrls {
	private int n = 0;
	private int i = 0;
	private int index = -1;
	private Object object = new Object();
	private ArrayList<String> urls;

	public int getIndex(){
		synchronized (object){
			index++;
			object.notify();
			return index;
		}
	}

	public void downloadUrls(String fileUrl, int countThread) throws IOException {
		Object object = new Object();
		this.urls = pars(fileUrl);
		MyThread[] th = new MyThread[countThread];
		while (n < countThread) {
			th[n] = new MyThread(this, n, urls);
			n++;
		}
		n = 0;
		while (n < countThread) {
			th[n].start();
			n++;
		}
	}

	public void urlDown(String urls) throws IOException {
		URL url = new URL(urls);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fis = new FileOutputStream(""
				+ urls.split("/")[urls.split("/").length - 1]);
		byte[] buffer = new byte[1024];
		int count=0;
		while((count = bis.read(buffer,0,1024)) != -1)
			fis.write(buffer, 0, count);
		fis.close();
		bis.close();
	}

	private ArrayList<String> pars(String fileUrl) throws IOException{

		BufferedReader urls = null;
		try {
			urls = new BufferedReader(new FileReader(fileUrl));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (urls == null)
			System.out.println("Error open file" + " files_urls.txt");
		ArrayList<String> urlsStr = new ArrayList<>();
		String str;
		str = urls.readLine();
		urlsStr.add(str);
		while ((str = urls.readLine()) != null)
			urlsStr.add(str);
		urls.close();
		return urlsStr;
	}

	public int getN() {
		return n;
	}

	public int getI() {
		return i;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}
}

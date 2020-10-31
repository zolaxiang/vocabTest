import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Model {

	ArrayList<Entry> dict;
	int curWord;

	Model() {
		dict = new ArrayList<>();
		try {
			Scanner dictFile = new Scanner(Paths.get("dict.txt"), "UTF-8");
			Scanner countFile = new Scanner(Paths.get("count.txt"), "UTF-8");

			while (dictFile.hasNext()) {
				String str = dictFile.nextLine();
				int count;
				if (countFile.hasNext()) // countFile may be shorter because dict.txt has grown.
					count = countFile.nextInt();
				else
					count = 0;
				Entry entry = new Entry(str, count);
				dict.add(entry);
			}
		} catch (IOException e) {
		}
	}

	String getWord() {
		int size = dict.size();
		curWord = (int) (Math.random() * size);
		return dict.get(curWord).getWord();
	}

	void yes() {
		dict.get(curWord).step();
	}

	void saveResults() {
		try {
			PrintWriter dictFile = new PrintWriter("dict.txt", "UTF-8");
			PrintWriter countFile = new PrintWriter("count.txt", "UTF-8");
			PrintWriter learnedFile = new PrintWriter("learned.txt", "UTF-8");

			for (Entry e : dict) {
				if (e.getCount() < 3) {
					dictFile.println(e.getWord());
					countFile.println(e.getCount());
				} else
					learnedFile.println(e.getWord());
			}
			dictFile.close();
			countFile.close();
			learnedFile.close();
		} catch (IOException e) {
		}
	}

}

class Entry {
	private String word;
	private int count;

	Entry(String word, int count) {
		this.word = word;
		this.count = count;
	}

	String getWord() {
		return word;
	}

	int getCount() {
		return count;
	}

	void step() {
		count++;
	}
}
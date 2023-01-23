import java.util.*;

public class A1_Q3 {

	public static void main(String[] args) {
		String[] p1 = {"David no no no no nobody never",
				"Jennifer why ever not",
				"Parham no not never nobody",
				"Shishir no never know nobody",
				"Alvin why no nobody",
				"Alvin nobody never know why nobody",
				"David never no nobody",
				"Jennifer never never nobody no"
		};

		String[] p2 = {"user1 chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp",
		"user2 chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp",
		"user3 chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp",
		"user1 chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp chomp",};

		String[] p3 = {"user1 doubledutch double double dutch",
		"user2 dutch doubledutch doubledutch double",
		"user3 not double dutch doubledutch"};

		String[] p4 = {"James gobble de gook",
				"Bill gobble",
				"james de gook"};

		String[] posts = p3;

		ArrayList<String> wordsByUser = getWordsByUser(posts);
		HashMap<String, Integer> commonWords = getCommonWordsWithCount(wordsByUser);
		for(String word : wordsByUser){
			System.out.println(word);
		}
		System.out.println("---------------------------------");
		for(String key : commonWords.keySet()){
			System.out.println(key);
		}
		System.out.println("----------------------------------");
		ArrayList<String> words = new ArrayList<>();
		ArrayList<Integer> wordCounts = new ArrayList<>();
		for(String key : commonWords.keySet()){
			words.add(key);
			wordCounts.add(commonWords.get(key));
		}
		System.out.println(words);
		System.out.println(wordCounts);

		System.out.println();
		System.out.println();
		ArrayList<String> list = heapSort(words, wordCounts);
		for(String key : list){
			System.out.println(key);
		}


	}

	public static ArrayList<String> Discussion_Board(String[] posts){
		ArrayList<String> wordsByUser = getWordsByUser(posts);
		HashMap<String, Integer> commonWords = getCommonWordsWithCount(wordsByUser);

		if(commonWords.size() < 1){
			return new ArrayList<>();
		}

		ArrayList<String> words = new ArrayList<>();
		ArrayList<Integer> wordCounts = new ArrayList<>();
		for(String key : commonWords.keySet()){
			words.add(key);
			wordCounts.add(commonWords.get(key));
		}

		ArrayList<String> returnList = heapSort(words, wordCounts);

		return returnList;
	}


	/*
	private static HashMap<String, Integer> getCommonWordsWithCount(ArrayList<String> lines){

		if(lines.size() < 1){
			return new HashMap<>();
		}

		HashMap<String, Integer> words = new HashMap<>();
		List<String> firstLine = Arrays.asList(lines.get(0).split(" "));	//all words in first line

		if(lines.size() > 1){
			for(String word : firstLine){
				int count;
				if(words.containsKey(word)){
					count = words.get(word);
				}else{
					count = 1;
				}

				boolean common = true;


				for(int i = 1; i < lines.size(); i++){
					List<String> wordsInLine = Arrays.asList(lines.get(i).split(" "));	//all words in ith line
					if(!wordsInLine.contains(word)){
						common = false;
						continue;
					}
					for(int j = 0; j < wordsInLine.size(); j++){
						String thisWord = wordsInLine.get(j);
						if(thisWord.equals(word)){
							count++;
						}
					}
				}


				if(common == true){
					words.put(word, count);
				}
			}

		}else if(lines.size() == 1){
			for(String word : firstLine){
				if(words.containsKey(word)){
					int val = words.get(word);
					val++;
					words.put(word, val);
				}else{
					words.put(word, 1);
				}

			}
		}


		return words;
	}
	 */

	private static HashMap<String, Integer> getCommonWordsWithCount(ArrayList<String> lines){

		if(lines.size() < 1){
			return new HashMap<>();
		}

		ArrayList<HashMap<String, Integer>> maps = new ArrayList<>();
		for(String line : lines){
			HashMap<String, Integer> map = new HashMap<>();
			List<String> wl = Arrays.asList(line.split(" "));
			for(String w : wl){
				if(map.containsKey(w)){
					int c = map.get(w);
					c++;
					map.put(w, c);
				}else{
					map.put(w, 1);
				}
			}
			maps.add(map);
		}

		HashMap<String, Integer> words = new HashMap<>();
		HashMap<String, Integer> firstLine = maps.get(0);	//all words in first line

		if(lines.size() > 1){
			for(String word : firstLine.keySet()){
				int count = firstLine.get(word);;
				boolean common = true;


				for(int i = 1; i < lines.size(); i++){
					HashMap<String, Integer> line = maps.get(i);
					if(line.containsKey(word)){
						count = count + line.get(word);
					}else {
						common = false;
					}

				}


				if(common == true){
					words.put(word, count);
				}
			}

		}else if(lines.size() == 1){
			for(String word : firstLine.keySet()){
				if(words.containsKey(word)){
					int val = words.get(word);
					val++;
					words.put(word, val);
				}else{
					words.put(word, 1);
				}

			}
		}


		return words;
	}



	private static void maxHeapify(ArrayList<String> words, ArrayList<Integer> wordCounts, int i) {

		int largest;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int n = words.size();

		if((l<n) && ((wordCounts.get(l)>wordCounts.get(i)) || ((wordCounts.get(l)==wordCounts.get(i)) && (words.get(l).compareTo(words.get(i))<0)))){
			largest = l;
		}else {
			largest = i;
		}

		if((r<n) && ((wordCounts.get(r)>wordCounts.get(largest)) || ((wordCounts.get(r)==wordCounts.get(largest)) && (words.get(r).compareTo(words.get(largest))<0)))){
			largest = r;
		}

		if (largest != i){
			int tempI = wordCounts.get(i);
			String tempS = words.get(i);
			wordCounts.set(i, wordCounts.get(largest));
			words.set(i, words.get(largest));
			wordCounts.set(largest, tempI);
			words.set(largest, tempS);
			maxHeapify(words, wordCounts, largest);
		}
	}


	private static void buildMaxHeap(ArrayList<String> words, ArrayList<Integer> wordCounts){
		 int n = words.size();
		 int x = n/2;
		 for (int i = x; i >= 1; i--){
			 int y = i-1;
			 maxHeapify(words, wordCounts, y);
		 }
	}

	private static ArrayList<String> heapSort(ArrayList<String> words, ArrayList<Integer> wordCounts){
		ArrayList<String> returnList = new ArrayList<>();
		buildMaxHeap(words, wordCounts);
		for(int i = words.size()-1; i >= 1; i--){
			int tempI = wordCounts.get(i);
			String tempS = words.get(i);
			wordCounts.set(i, wordCounts.get(0));
			words.set(i, words.get(0));
			wordCounts.set(0, tempI);
			words.set(0, tempS);
			String removedWord = words.remove(i);
			wordCounts.remove(i);
			returnList.add(removedWord);
			maxHeapify(words, wordCounts, 0);
		}
		if(words.size() >= 1){
			returnList.add(words.get(0));
		}
		return returnList;
	}

	private static ArrayList<String> getWordsByUser(String[] posts){		//O(n)
		HashMap<String, String> wordsByUser = new HashMap<>();
		ArrayList<String> returnList = new ArrayList<>();

		for(int i = 0; i < posts.length; i++){
			String post = posts[i];
			String arr[] = post.split(" ", 2);
			String username = arr[0];
			String theRest = arr[1];

			if(wordsByUser.containsKey(username)){
				String value = wordsByUser.get(username);
				String newVal = value + " " + theRest;
				wordsByUser.put(username, newVal);
			} else{
				wordsByUser.put(username, theRest);
			}

		}

		for(String key : wordsByUser.keySet()){
			String value = wordsByUser.get(key);
			returnList.add(value);
		}
		return returnList;
	}

}

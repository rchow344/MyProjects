import java.util.Scanner;
/**
 *	Provides utilities for word games:
 *	1. finds all words in the dictionary that match a list of letters
 *	2. prints an array of words to the screen in tabular format
 *	3. finds the word from an array of words with the highest score
 *	4. calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Riley Chow
 *	@since	October 18th, 2024
 */
 
public class WordUtils
{
	private String[] words;		// the dictionary of words
	
	// File containing dictionary of almost 100,000 words.
	private final String WORD_FILE = "wordList.txt";
	
	/* Constructor */
	public WordUtils() { }
	
	/**	Load all of the dictionary from a file into words array. */
	private void loadWords () {
		int numberOfLines = 0;
		Scanner input = FileUtils.openToRead(WORD_FILE);
		while(input.hasNext()){
			input.next();
			numberOfLines++;
		}
		words = new String[numberOfLines];
		input = FileUtils.openToRead(WORD_FILE);
		numberOfLines = 0;
		while(input.hasNext()){
			words[numberOfLines] = input.next();
			numberOfLines++;
		}
	}
	
	/**	Find all words that can be formed by a list of letters.
	 *  @param letters	string containing list of letters
	 *  @return			array of strings with all words found.
	 */
	public String[] findAllWords(String letters) {
		int numberOfLines = 0;
		String [] wordsFound;
		Scanner input = FileUtils.openToRead(WORD_FILE);
		while(input.hasNext()){
			input.next();
			numberOfLines++;
		}
		wordsFound = new String[numberOfLines];
		numberOfLines = 0;
		input = FileUtils.openToRead(WORD_FILE);
		while(input.hasNext()){
			String word = input.next();
			if(isWordMatch(word, letters)){
				wordsFound[numberOfLines] = word;
				numberOfLines++;
			}
		}
		input.close();
		return wordsFound;
	}
	
	
	/**
	 *  Decides if a word matches a group of letters.
	 *
	 *  @param word  The word to test.
	 *  @param letters  A string of letters to compare
	 *  @return  true if the word matches the letters, false otherwise
	 */
	public boolean isWordMatch (String word, String letters) {
		for(int a = 0; a < word.length(); a++){
			char c = word.charAt(a);
			if(letters.indexOf(c) > -1) 
				letters = letters.substring(0,letters.indexOf(c)) +
				letters.substring(letters.indexOf(c)+1);
			else return false;
		}
		return true;
	}
	/**	Print the words found to the screen.
	 *  @param words	array containing the words to be printed
	 */
	public void printWords (String [] wordList) {
		for(int i = 1; i < wordList.length-1; i++){
			if(wordList[i-1] != null){
				if(i%5 == 0 && i !=0) System.out.printf("%s\t\t\n", wordList[i-1]);
				else System.out.printf("%s\t\t", wordList[i-1]);
			}
		}
	}
	
	/**	Finds the highest scoring word according to a score table.
	 *
	 *  @param word  		An array of words to check
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return   			The word with the highest score
	 */
	public String bestWord (String [] wordList, int [] scoreTable)
	{
		int sum = 0;
		String best = "";
		for(int i = 0; i < wordList.length; i++){
			if(wordList[i] != null){
				int currentSum = 0;
				for(int j = 0; j < wordList[i].length(); j++){
					int indexAlpha = (int)(wordList[i].charAt(j))-(int)('a');
					currentSum += scoreTable[indexAlpha];
				}
				if(currentSum>sum){
					sum = currentSum;
					best = wordList[i];
				}
			}
		}
		return best;
	}
	
	/**	Calculates the score of one word according to a score table.
	 *
	 *  @param word			The word to score
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return				The integer score of the word
	 */
	public int getScore (String word, int [] scoreTable)
	{
		int sum = 0;
		for(int i = 0; i < word.length(); i++){
			sum += scoreTable[(int)(word.charAt(i))-(int)('a')];
		}
		return sum;
	}
	
	/***************************************************************/
	/************************** Testing ****************************/
	/***************************************************************/
	public static void main (String [] args)
	{
		WordUtils wu = new WordUtils();
		wu.run();
	}
	
	public void run() {
		String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
		loadWords();
		String [] word = findAllWords(letters);
		System.out.println();
		printWords(word);
		
		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		String best = bestWord(word,scoreTable);
		System.out.println("\n\nHighest scoring word: " + best + "\nScore = " 
							+ getScore(best, scoreTable) + "\n");
	}
}

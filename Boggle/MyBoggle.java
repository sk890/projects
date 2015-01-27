/////////////////////////////////////////////
///////NAME:Shimpei Kurokawa
///////TITLE: Boggle Game
///////DESCRIPTION:
///////      An implementation of boggle, a game in which the player is given a matrix of letters
///////      and the player enters all the words that are within the matrix and are connected.
///////      The game implement a De la Briandais Trie or a Simple dictionary to construct a dictionary
//////       and to enumerate all possible words in the matrix. The De La Briandas trie was used instead of 
//////	     an R-way trie to avoid the memory overhead and avoiding a O(N) search time cause by arrays. An array set is implemented
//////       as well to prevent the same word being guessed twice. The word finding algorithm implements
//////		 a recursive algorithm that utilizes back-tracing and pruning to improve on runtime.
///////NOTE:
///////      The command line arguments as follow:
///////        java MyBoggle -d dictionaryType -b BoardName
///////
///////        DictionaryType can either be "simple" (for the simple array list implementation) 
///////        or "dlb"(for the De La Briandais Trie Implementation)
///////
///////		   BoardName is a text file with a matrix of letters used in the boggle game. "board1.txt" is provided.
/////////////////////////////////////////////

import java.io.*;
import java.util.*;
public class MyBoggle
{
	public static void main(String [] args) throws IOException
	{	
		//Create two dictionary, D is words from dictionary.txt and F is all the words found on the board
		DictionaryInterface D,F;
		
		//Boolean to check to use DLBTrie or not
		boolean DLB=false;
		
		String boardname="";
		
		// flip through args to find argument
		for(int i=0;i<args.length;++i){
			if(args[i].equals("-d")){
				if(args[i+1].equals("dlb")){
					DLB=true;
				}
			}
			else if(args[i].equals("-b")){
				boardname=args[i+1];
			}
			
		}
		
		//Intialize Dictionary 
		if (DLB==true){
			 D = new DLBTrie();
			 F = new DLBTrie();
		}
		
		else{
			D = new SimpleDictionary();
			F = new SimpleDictionary();
		}
		
		//Read in words to dictionary from dictonary.txt
		Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
		String st;
		StringBuilder sb;
		while (fileScan.hasNext())
		{
			st = fileScan.nextLine();
			D.add(st);
		}
		
		
		// Set for all the correct words
		ArraySet correctwords =new ArraySet(2000);
		
		//boolean for gamplay loop
		boolean guest=true;
		
		//intialize boggleboard
		boggleboard b = new boggleboard(D,F,boardname);
		
		//intialize input stream
		StringBuilder user_word;
		Scanner user_input = new Scanner( System.in );
		
		
		System.out.println("Welcome to the Boggle Simulator: \n");
		b.printboard();
		System.out.println();
		
		//Query user for words 
		while(guest){
			
			//Game play
			System.out.println("\nPlease enter a word or press x to exit: ");
			user_word=new StringBuilder( user_input.nextLine().toLowerCase());
			
			if(user_word.toString().equals("x")){
				guest=false;
			}
		
			else if(F.search(user_word)==2 || F.search(user_word)==3){
				System.out.println("\nYou are correct, the word exist !");
				correctwords.add(user_word.toString());
			}
			else{
				System.out.println("You are wrong, the word does not exist !");
			}
		}
		System.out.println("\n\n");
		
		//print all words
		b.printwords();
		
		//print all guessesd words
		System.out.println("The words you guess correctly: \n");
		correctwords.printData();
		
		//print stats
		System.out.println("You guessed "+correctwords.size()+ " out of the "+b.words.size()+" possible words correctly\n");
		float percent=( (float)correctwords.size()/(float)b.words.size())*100;
		System.out.println("You got "+percent+ "% right");

	}
}



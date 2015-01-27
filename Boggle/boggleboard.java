import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class boggleboard {
	
	//2d array that repersents the boggleboard
	private char boggleboard[][]=new char[4][4];
	
	//An array implementation of set to record all the valid words
	ArraySet words= new ArraySet(5000);
	
	//Array of the alphabet for the *
	private char alphabet []={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	// dictionary for the dictionary.txt
	private DictionaryInterface dict;
	
	//dictionary for all the words that exist on the board
	private DictionaryInterface found;

	
	public boggleboard(DictionaryInterface dictionary, DictionaryInterface foundwords, String boardname) {
		
		
		dict=dictionary;
		found=foundwords;
		
		StringBuilder user_word;
		Scanner finput = new Scanner( System.in );
		boolean shouldRetry = true;
		
		do {

		     try {
		    	 
		    	Scanner boggleScan = new Scanner(new FileInputStream(boardname));
		    	String bogglein = boggleScan.nextLine();
				bogglein=bogglein.toLowerCase();
				boggleScan.close();
		        shouldRetry= false;
		        
		      //Set up boggleboard from board.txt
		        int k=0;
				for (int i=0; i<4;i=i+1){
					for(int j=0; j<4; j=j+1){
					boggleboard[j][i]=bogglein.charAt(k);
					k=k+1;
					}
				}
		      } catch(FileNotFoundException e) {
		        shouldRetry= true;  
		        System.out.println("File not Found, Please enter valid file: ");
		        boardname=finput.nextLine();
		      }
		}while(shouldRetry);
		
		
		
		
		
		
		
		
		
		//pass each letter of the boggleboard to function checkboard
		for (int i=0; i<4;i=i+1){
			for(int j=0; j<4; j=j+1){
			StringBuilder buf= new StringBuilder();
			boolean [][] vis= new boolean[4][4];
			checkboard(i,j,buf,vis);
			}
		}
		
	}
	
	public void checkboard(int row, int cols,StringBuilder wordbuffer,boolean[][]visted){
		
		//Make sure row and cols are both in range
		if( row<4 && row>-1 && cols<4 && cols>-1
				&& visted [row][cols]==false){
			
			//add a char to Stringbuilder newword
			StringBuilder newword= new StringBuilder(wordbuffer);
			newword.append(boggleboard[row][cols]);
			
			//Handle '*'case
			if(newword.charAt(newword.length()-1)=='*'){
				int starindex=newword.length()-1;
				
				//replace * with all the letters in the alphabet
				for (int i=0; i<alphabet.length;++i){
					newword.setCharAt(starindex,alphabet[i]);
					if (dict.search(newword)==0 && newword.length()>2){
						//base case
						
					}
				
					
					//add if word is valid and the word is longer than 3 characters
					else if (newword.length()>2 && dict.search(newword)==2){
						words.add(newword.toString());
						found.add(newword.toString());
					}
					
					else {
						//add prefix, valid word
						if (dict.search(newword)==3 && newword.length()>2){
						words.add(newword.toString());
						found.add(newword.toString());
					
						}
						//recursive call
						visted[row][cols]=true;
						checkboard(row+1,cols,newword,visted);
						checkboard(row-1,cols,newword,visted);
						checkboard(row,cols-1,newword,visted);
						checkboard(row,cols+1,newword,visted);
						checkboard(row-1,cols-1,newword,visted);
						checkboard(row+1,cols+1,newword,visted);
						checkboard(row-1,cols+1,newword,visted);
						checkboard(row+1,cols-1,newword,visted);	
						visted[row][cols]=false;
				}
				}
			}// end of '*' case
			
			
			
			if (dict.search(newword)==0 && newword.length()>2){
				//base case, if word is unvalid and is not a prefix, do nothing
				
			}
		
			
			//add if word is valid and the word is longer than 3 characters
			else if (newword.length()>2 && dict.search(newword)==2){
				words.add(newword.toString());
				found.add(newword.toString());
			}
			
		
			else {
				//add prefix, valid word
				if (dict.search(newword)==3 && newword.length()>2){
					words.add(newword.toString());
					found.add(newword.toString());
				}
				//recusrisve call
				visted[row][cols]=true;
				checkboard(row+1,cols,newword,visted);
				checkboard(row-1,cols,newword,visted);
				checkboard(row,cols-1,newword,visted);
				checkboard(row,cols+1,newword,visted);
				checkboard(row-1,cols-1,newword,visted);
				checkboard(row+1,cols+1,newword,visted);
				checkboard(row-1,cols+1,newword,visted);
				checkboard(row+1,cols-1,newword,visted);	
				visted[row][cols]=false;
			}
		
		}
		
	}
	
	

	//method to print all valid words
	public void printwords(){
		words.sort();
		System.out.println("Complete Word list from the Boggle Board: \n");
		words.printData();
	}
	
	//print boggle board
	public void printboard(){
		for (int i=0; i<4;i=i+1){
			for(int j=0; j<4; j=j+1){
			System.out.print(Character.toUpperCase(boggleboard[j][i])+" ");
			
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	


}

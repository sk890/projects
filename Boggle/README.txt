//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////
///// NAME:Shimpei Kurokawa
/////
///// TITLE: Boggle Game
/////
///// MAIN: MyBoggle.java
/////
///// DESCRIPTION:
/////      An implementation of Boggle, a game in which the player is given a matrix of letter
/////      and the player enters all the words that are within the matrix.
/////      The game implement a De la Briandais Trie or a Simple dictionary to construct a dictionary
/////      and enumerates all possible words in the matrix. The De La Briandas trie was used instead of 
/////	   an R-way trie to avoid the memory overhead. The trie also avoids the O(n) search runtime for arrays 
/////      and has a logathrimic runtime. An array set is implemented to prevent the same word being guessed twice. 
/////      The word finding algorithm implements recursion and utilizes back-tracing and pruning to improve on runtime.
/////NOTE:
/////      The command line arguments as follow:
/////      java MyBoggle -d dictionaryType -b BoardName
/////
/////        DictionaryType can either be "simple" (for the simple array list implementation) 
/////        or "dlb"(for the De La Briandais Trie Implementation)
/////        BoardName is a text file with a matrix of letters used in the boggle game. "board1.txt" is provided.
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

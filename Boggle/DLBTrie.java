//De la Brinadais Impementation




public class DLBTrie implements DictionaryInterface {

	private Node root;
	
	public DLBTrie(){
		root=new Node();
	}
	
	
	//method that adds a string and returns true once its add
	public boolean add(String s) {
		
		Node previousnode= nodefinder(root,s.charAt(0));
		Node currentnode= new Node();
		
		for (int i=1; i<s.length();i=i+1){
			currentnode=nodefinder(previousnode,s.charAt(i));
			previousnode=currentnode;
		}
			previousnode.exist=true;
			return true;
		
	}
	
	//Helper function that finds the next node of current with character chartofind
	private Node nodefinder(Node current,char chartofind){
		
		Node returnnode= new Node();
		//if empty create new node
		if (current.nextlist.size()==0){
			returnnode.letter=chartofind;
			current.nextlist.add(returnnode);
			
			return returnnode;	
		}
		
		//Flip through the linked list in node to find the node with the character chartofind
		for(int i=0; i< current.nextlist.size(); i=i+1){
			Node checknode= (Node)current.nextlist.get(i);
			if (chartofind==checknode.letter){
				return checknode;
			}
		}
		
		//if new node with a new char in linked list add then return.
		returnnode.letter=chartofind;
		current.nextlist.add(returnnode);
		return returnnode;	
	}

	/* Returns 0 if s is not a word or prefix within the DictInterface
	 * Returns 1 if s is a prefix within the DictInterface but not a 
	 *         valid word
	 * Returns 2 if s is a word within the DictInterface but not a
	 *         prefix to other words
	 * Returns 3 if s is both a word within the DictInterface and a
	 *         prefix to other words
	 */
	
	public int search(StringBuilder s) {
		boolean prefix;
		int result;
		char check;
		
		Node previousnode= nodesearcher(root,s.charAt(0));
		Node currentnode= new Node();
		
		//flip s to traverse DLB trie
		for(int i=1;i<s.length();i=i+1){
			if(previousnode==null){
				return 0;
			}
			
			check=s.charAt(i);
			currentnode=nodesearcher(previousnode,s.charAt(i));
			previousnode=currentnode;
		}
		
		
		if(previousnode==null){
			return 0;
		}
		else if (previousnode.exist==false){
			return 1;
		}
		else if (previousnode.exist==true && previousnode.nextlist.get(0)==null){
			return 2;
		}
		else{
			return 3;
		}
		
	}
	
//Finds the next node of node current with the character chartofind	
private Node nodesearcher(Node current,char chartofind){
		
		//return null if no nest node exist
		if (current.nextlist==null){
			return null;	
		}
		
		//flip through linked list to find the node with chartofind
		for(int i=0; i< current.nextlist.size(); i=i+1){
			Node checknode= (Node)current.nextlist.get(i);
			if (chartofind==checknode.letter){
				return checknode;
			}
		}
		
		//return null if not found
		return null;	
	}


//private node class
private static class Node {
		//the character
		private char letter;
		//boolean to check if a word is a complete word
        private boolean exist=false;
        //linked list that points to other node
        private LinkedList nextlist = new LinkedList();
    }

}

//Array set implemenation
import java.util.Arrays;




public class ArraySet {

    private String [] data;
    int lsize;  //lsize is the logical size of array or rather the number of items.
    boolean found;
    
    //Constructor
   public ArraySet (int maximumSize)
   {
       data= new String[maximumSize];
       lsize=0;
   }
   
   //Add string to arrayset
   public void add(String value)
   {
       //Check array size
	   if (lsize>=data.length){
		   System.out.println("Error Array is full\n");
	   }
	   
	   //Make sure value is not repeated then add to array
	   else{
       found=contains(value);
       if (found==false){
           data[lsize]=value;
           lsize=lsize+1;
       }
       
	   }
       
   
   }
    public boolean contains(String value)
       {
          
           for(int i=0;i<lsize;i++){
           if(value.equals(data[i])){
               return true;
           }
           }
           return false;
         }
       
   public void remove (String value){
       
           for(int i=0;i<=lsize;i++){
           if(value.equals(data[i])){
               data[i]=data[lsize-1];
               data[lsize-1]=null;
               lsize=lsize-1;
           }
           }
           
       }
    
   public int size(){
       return lsize;
   }
       
          
   public boolean isFull(){
           if(lsize==data.length){
               return true;
           }
           return false;
       }
       
   //method to show the contents of array
   
    public void printData(){
       for(int i=0;i<lsize;i++){
       System.out.print(data[i]+" \n");
       }
       System.out.println("\n");
       }
    
    public void sort(){
    	Arrays.sort(data,0,lsize-1);
    }
    
    
   
    
 
   
}
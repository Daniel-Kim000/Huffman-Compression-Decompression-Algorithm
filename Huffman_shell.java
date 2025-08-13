// Name: Daniel Kim             
// Partner (if applicable):
// Date: 4/8
import java.util.*;
import java.io.*;
import java.util.PriorityQueue;
import java.io.FileWriter; 
/** A program to implement Huffman style compression to encode/compress text into binary. */
public class Huffman_shell
{
   /** Data field to read input from the client at the keyboard */
   public static Scanner keyboard = new Scanner(System.in);
   
  /** a main function to run 
   *  @param args  'cause, you know, we need this
   *  @exception IOException  in case we can't find a file we are looking for 
   */
   public static void main(String[] args) throws IOException
   {
      //Prompt for two strings 
      System.out.println("Encoding using Huffman codes");
      System.out.println("Enter a message to compress in code ");
      String message = keyboard.nextLine();
   
      System.out.println("Enter middle part of filename:  ");
      String middlePart = keyboard.nextLine();
   
      huffmanize( message, middlePart );
   }
   
  /** Given a message to encode and a name for a file, this will figure out the Huffman scheme and compress/enocde the message,
    * then the encoded message and scheme will be written out to files.
    * The message will be written out to ("message." + middlePart + ".txt") and the scheme will be written out to ("scheme." + middlePart + ".txt").
    * @param message  the message that the client wants to compress; pre-condition: message is not null
    * @param middlePart  the valid file name that we want to use; pre-condition: message is not null and consistes of alpha-numeric characters (no meta-characters).
    * @exception IOException  in case we can't find a file we are looking for
    */
   public static void huffmanize(String message, String middlePart) throws IOException
   {
         //Make a frequency table of the letters
         //
      Map<String, Integer> freqMap = new HashMap<String, Integer>();
      freqMap = freqTable(message);
         
         
      	//Put each letter-frequency pair into a HuffmanTreeNode. Put each node into a priority queue (or a min-heap).     
      	//
   
      HuffmanTreeNode temp;
      PriorityQueue<HuffmanTreeNode> que = new PriorityQueue();
         
      for (String key : freqMap.keySet()) { // Iterating over the elements of the hash map
         System.out.println("Key: " + key + ", Value: " + freqMap.get(key)); // key is the character, value is the freq
         temp = new HuffmanTreeNode(key, freqMap.get(key), null, null); // new(character, frequency, initLeft, initRight)
         que.add(temp);
         
         
      }
         
         //Use the priority queue of nodes to build the Huffman tree
         //
         
      HuffmanTreeNode root = makeHuffmanTree(que); // finished i think
      
      	
         //Process the string letter-by-letter and search the tree for the letter. It's recursive. As you recur, build the path through the tree, left is 0 and right is 1.
         //System.out.println the binary message 
         //Write the binary message to the hard drive using the file name ("message." + middlePart + ".txt")
         //
      String str = "";
      for(int i = 0; i < message.length(); i++) {
         str += search(root, "" + message.charAt(i), "");    
      }
         
         // write to a file - https://www.w3schools.com/java/java_files_create.asp
      System.out.println(str);
      FileWriter myWriter = new FileWriter("message." + middlePart +".txt");
      myWriter.write(str);
      myWriter.close();
                  
         //System.out.println the scheme from the tree--needs a recursive helper method    	
         //Write the scheme to the hard drive using the file name ("scheme." + middlePart + ".txt")   
         // 
      String str2 = "";
      str2 = schemeMaker(root, "");
         
      FileWriter myWriter2 = new FileWriter("scheme." + middlePart +".txt");
      myWriter2.write(str2);
      myWriter2.close();
      
      System.out.println(str2);
      
      double compressRatio = ((double)str2.length()) / ((double)message.length() * 8);
      compressRatio *= 100;
      System.out.println(compressRatio + "% compressed");
   }
   
   public static String schemeMaker(HuffmanTreeNode start, String path) {
      if(start == null) {
         return path;
      }
   
      if(start.getLeft() == null && start.getRight() == null) {
         return start.getLetter() + path + "\n";
         
      }
      return schemeMaker(start.getLeft(), path + "0") + schemeMaker(start.getRight(), path + "1");
   }
   
   
   // start is root
   // m is letter
   // path is empty
   public static String search(HuffmanTreeNode start, String m, String path) {
      if(start == null) {
         return path;
      }
   
      if((start.getLeft() == null) && (start.getRight() == null)) {
         if(start.getLetter().equals(m)) {
            return path;
         }
         else {
            return "";
         }
      }
      else {
         return search(start.getLeft(), m, path + "0") + search(start.getRight(), m, path + "1");
      }
   // if start is leaf:
   /*
   if(leaf has letter we are looking for)
   {
   return path
   else
   return empty string
   }
   else
   {
      return search(start.getLeft(), m, path + "0") + search(start.getRight(),m,path+"1");
   }
   */
      
   }
   
   public static HuffmanTreeNode makeHuffmanTree(PriorityQueue<HuffmanTreeNode> q) {
      HuffmanTreeNode temp;
      HuffmanTreeNode temp2;
      HuffmanTreeNode full = new HuffmanTreeNode(null, 0, null, null);
      
      int size = q.size();
      while(!q.isEmpty()) {
         if(q.size() == 1) {
            break;
         }
         temp = q.poll();
         temp2 = q.poll();
         
         HuffmanTreeNode newNode = new HuffmanTreeNode("*", (temp.getFreq() + temp2.getFreq()), temp, temp2);
         q.add(newNode);
         full = q.peek();
      
      }
      
      return full;
   }
   
   
// helper method for changing a string into an arraylist of ints
// pre: string with size > -1
// post: arraylist of integers
   public static ArrayList<Integer> toIntArr(String str) {
      ArrayList<Integer> list = new ArrayList<Integer>();
      for(int i = 0; i < str.length(); i++) {
         String b = str.substring(i,i+1);
         list.add(Integer.valueOf(b));
      }
      return list;
   }
// helper method for changing a string into an arraylist of strs for each character
// pre: string with size > -1
// post: arraylist of strs for each character
   public static ArrayList<String> toStrArr(String str) {
      ArrayList<String> list = new ArrayList<String>();
      for(int i = 0; i < str.length(); i++) {
         String b = str.substring(i,i+1);
         list.add(b);
      }
      return list;
   }
   
   //helper method for finding frequencies of each character in a message
   // pre: mess is the message trying to be compressed
   // post: returns a Map of the individual characters as keys, and frequencies as values
   public static Map freqTable(String mess) {
      Map<String, Integer> freqMap = new HashMap<String, Integer>(); // String key, int value
      ArrayList<String> m = toStrArr(mess);
      
      for(int i = 0; i < m.size(); i++) {
         if(!freqMap.containsKey(m.get(i))) { // if the Map DOES NOT HAVE the key of an indiv character 
            freqMap.put(m.get(i), 1); // starts at 1
         }
         else { // if the map HAS the key ALREADY of an indiv character
            int val = freqMap.get(m.get(i)); // gets the value of the current key
            val++; // increments it
            freqMap.put(m.get(i), freqMap.get(m.get(i)) + 1);; // sets it
         }  
      }
   
      
      return freqMap;
   }
   
}

/** This tree node stores two values: a letter and its frequency, as well as pointers to their left and right subtrees
 *  The compareTo method must ensure that the lowest frequency has the highest priority.
 */
class HuffmanTreeNode implements Comparable<HuffmanTreeNode>
{
 
  
  /** Data field to store the value in thenode */
   private String letter;
   private int freq; 
   /** Data fields for the pointers to a left and right subtree from the current node */
   private HuffmanTreeNode left, right;
   
   
 /** 4-arg constructor to create a node given the value and what we want the left and right pointers to point to 
  * @param let  the letter we want to store in the node
  * @param initFreq  the freq we want to store in the node
  * @param initLeft  what we want the left-pointer to point to
  * @param initRight  what we want the right-pointer to point to     
  */
   public HuffmanTreeNode(String let, int initFreq, HuffmanTreeNode initLeft, HuffmanTreeNode initRight)
   { 
      letter = let;
      freq = initFreq;
      left = initLeft; 
      right = initRight; 
   }
   
   
  /** Accessor method to return the letter of the node
  * @return  the letter stored in the node
  */
   public String getLetter()
   { 
      return letter; 
   }
   
 /** Accessor method to return the freq of the node
  * @return  the freq stored in the node
  */
   public int getFreq()
   { 
      return freq; 
   }
   
 /** Accessor method to return a pointer to the left-subtree of the current node
  * @return  a reference to the left-child of this node
  */
   public HuffmanTreeNode getLeft() 
   { 
      return left; 
   }
  
 /** Accessor method to return a pointer to the right-subtree of the current node
  * @return  a reference to the right-child of this node
  */   
   public HuffmanTreeNode getRight() 
   { 
      return right; 
   }
   
   /** Mutator method to change the letter stored in the node
  * @param newLetter  what we want to store as a letter in the node
  */
   public void setFreq(String newLetter) 
   { 
      letter = newLetter; 
   }

   
 /** Mutator method to change the freq stored in the node
  * @param newFreq  what we want to store as a freq in the node
  */
   public void setFreq(int newFreq) 
   { 
      freq = newFreq; 
   }
   
 /** Mutator method to change the left-subtree to point to a different node
  * @param theNewLeft  a reference to the node that we want the left-pointer to point to
  */
   public void setLeft(HuffmanTreeNode theNewLeft) 
   { 
      left = theNewLeft;
   }
   
 /** Mutator method to change the right-subtree to point to a different node
  * @param theNewRight  a reference to the node that we want the right-pointer to point to
  */  
   public void setRight(HuffmanTreeNode theNewRight)
   { 
      right = theNewRight;
   }
   
 /** Return the value as a string
  * @return the vlue as a string
  */
   @Override
   public String toString()
   {
      return letter + " " + String.valueOf(freq);
   }   

/** Compare Huffman Tree Nodes by their frequencies
  * @param that  another node that we want to compare this against by frequency
  * @return a negative number if that has a higher-freq than this, a positive number if this has a higher-freq than that, zero if the freq are the same
  */

   @Override
   public int compareTo(HuffmanTreeNode that)
   {
      return freq - that.getFreq();
   }
         
}

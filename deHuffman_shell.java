// Name: Daniel Kim             
// Partner (if applicable):
// Date: 4/4
import java.util.*;
import java.io.*;
/** A program to uncompress and reval a message that has been compressed by Huffman compression. */
public class deHuffman_shell // left off on dehuff, i think you just traverse through tree based on the code passed through
{
    /** a main function to run 
   *  @param args  'cause, you know, we need this
   *  @exception IOException  in case we can't find a file we are looking for 
   */
   public static void main(String[] args) throws IOException
   {
     //Prompt for one string as the middlePart to link to two files: the message and scheme
      Scanner keyboard = new Scanner(System.in);
      System.out.println("What binary message (middle part)? ");        //"maips" to build "message.maips.txt"
      String middlePart = keyboard.next();                              //and "scheme.maips.txt"
      Scanner sc = new Scanner (new File("message."+middlePart+".txt")); 
      String binaryCode = sc.next();
      Scanner huffLines = new Scanner (new File("scheme."+middlePart+".txt")); 
      	
      TreeNode root = huffmanTree(huffLines);
      String message = dehuff(binaryCode, root);
      System.out.println(message);
      	
      sc.close();
      huffLines.close();
   }
   
   /** Builds a Huffman Tree given a Scanner that reads in a Huffman scheme
    *  @param huffLines  a Scanner initialized to read in a valid Huffman scheme from a file
    *  @return  the root of a completed Huffman tree given the scheme read in from the Scanner
    */
   public static TreeNode huffmanTree(Scanner huffLines)
   {
      TreeNode root = new TreeNode(null);
      TreeNode curr = root;
      //Complete this method by building the tree pointed to by root:
      while(huffLines.hasNext()) {
         curr = root;
         String part = huffLines.nextLine();
         String sym = part.substring(0,1);
         ArrayList<Integer> list = new ArrayList<Integer>();
         list = toArr(part.substring(1,part.length()));
         // System.out.print(sym + " ");
      //          System.out.println(list);
         for(int i = 0; i < list.size(); i++) {
            if(list.get(i) == 0) { // go left
            
               if(curr.getLeft() == null) { // if the left child hasnt been made yet
                  TreeNode x = new TreeNode(null);
                  curr.setLeft(x);
               }
               curr = curr.getLeft();
            }
            else if(list.get(i) == 1) { // go right
            
               if(curr.getRight() == null) { // if the right child hasnt been made yet
                  TreeNode x = new TreeNode(null);
                  curr.setRight(x);
               }
               curr = curr.getRight();
            
            }
         }
         curr.setValue(sym);
      }
      
      return root;
   }

// helper method for changing a string into an arraylist of ints
// pre: string with size > -1
// post: arraylist of integers
// used in huffmanTree and dehuff?
   public static ArrayList<Integer> toArr(String str) {
      ArrayList<Integer> list = new ArrayList<Integer>();
      for(int i = 0; i < str.length(); i++) {
         String b = str.substring(i,i+1);
         list.add(Integer.valueOf(b));
      }
      return list;
   }
   
   /** Finds the uncompressed message given a String of compressed binary and the root of a Huffman tree
    *  @param text  a String comprised of bits storing a compressed message; pre-condition: text is not null and non-empty
    *  @param root  the root of a completed Huffman tree (as built by the method huffmanTree
    *  @return  a String of the decoded compressed message
    */
   public static String dehuff(String text, TreeNode root)
   {
      String message = ""; 
      ArrayList<Integer> nums = new ArrayList<Integer>();
      nums = toArr(text); // just get the numbers in int form
     
      TreeNode curr = root; // current position of traversal
     
     //Complete this method by populating message:
      for(int i = 0; i < nums.size(); i++) {
         if(nums.get(i) == 0) { // go left
            curr = curr.getLeft();
            if(curr.getValue() != null) {
               message += curr.getValue();
               curr = root;
            }
         }
         else if(nums.get(i) == 1) { // go right
            curr = curr.getRight();
            if(curr.getValue() != null) {
               message += curr.getValue();
               curr = root;
            }
         
         }
      }
     
     
      return message;   
   }
}

/** This tree node stores a value as well as pointers to their left and right subtrees
 *  These will be used to build a Huffman tree where leaf-nodes may contain letters as values
 *  and the left-right paths to get to a letter-leaf will represent the 0 and 1 bits in the compressed message.
 */
class TreeNode
{
   /** Data field to store the value in thenode */
   private Object value; 
   /** Data fields for the pointers to a left and right subtree from the current node */
   private TreeNode left, right;
   
 /** 1-arg constructor to create a node with a given value, setting the left and right pointers to null
  * @param initValue  the value we want to store in the node
  */
   public TreeNode(Object initValue)
   { 
      value = initValue; 
      left = null; 
      right = null; 
   }
   
 /** 3-arg constructor to create a node given the value and what we want the left and right pointers to point to 
  * @param initValue  the value we want to store in the node
  * @param initLeft  what we want the left-pointer to point to
  * @param initRight  what we want the right-pointer to point to     
  */
   public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
   { 
      value = initValue; 
      left = initLeft; 
      right = initRight; 
   }
   
 /** Accessor method to return the value of the node
  * @return  the value stored in the node
  */
   public Object getValue()
   { 
      return value; 
   }
   
 /** Accessor method to return a pointer to the left-subtree of the current node
  * @return  a reference to the left-child of this node
  */
   public TreeNode getLeft() 
   { 
      return left; 
   }
  
 /** Accessor method to return a pointer to the right-subtree of the current node
  * @return  a reference to the right-child of this node
  */   
   public TreeNode getRight() 
   { 
      return right; 
   }
   
 /** Mutator method to change the value stored in the node
  * @param theNewValue  what we want to store as a value in the node
  */
   public void setValue(Object theNewValue) 
   { 
      value = theNewValue; 
   }
   
 /** Mutator method to change the left-subtree to point to a different node
  * @param theNewLeft  a reference to the node that we want the left-pointer to point to
  */
   public void setLeft(TreeNode theNewLeft) 
   { 
      left = theNewLeft;
   }
   
 /** Mutator method to change the right-subtree to point to a different node
  * @param theNewRight  a reference to the node that we want the right-pointer to point to
  */  
   public void setRight(TreeNode theNewRight)
   { 
      right = theNewRight;
   }
   
 /** Return the value as a string
  * @return the vlue as a string
  */
   @Override
   public String toString()
   {
      return value.toString();
   }   
}

# Huffman Encoder/Decoder
A Java program that implements Huffman encoding and decoding for text compression and decompression.

Author: Daniel Kim<br>
Date: April 2024

## Description:
This program provides both encoding and decoding functionality for Huffman compression:

Encoding: Compresses text messages into binary using Huffman coding algorithm
Decoding: Decompresses Huffman-encoded binary messages back into readable text using the encoding scheme
A copy of the Declaration of Independence is provided for copy and pasting.

## Files Required:
### Decoding:
The program expects two input files with a specific naming pattern:

message.[middlePart].txt - Contains the binary-encoded message
scheme.[middlePart].txt - Contains the Huffman encoding scheme

### Encoding:
Input text file or direct text input (depending on implementation)
Generates the encoding scheme and compressed binary output

Where [middlePart] is the identifier you provide when running the program.


## How to Run:
### Input: <br>
When prompted, enter the required information:

### For encoding: <br>
Provide the text to encode or filename (the Declaration of Independence is included as an example)
### For decoding: <br>
Enter the middle part of your filename (e.g., "maips" for "message.maips.txt")


## Example Usage:

### Encoding:
Enter text to encode: 
Hello World
[Binary encoded message and scheme will be generated]

### Decoding:
What binary message (middle part)? 
HelloWorld
[Decoded message will be displayed here]


## Features:
### Text Compression: <br>
Encode text into binary using optimal Huffman codes
### Text Decompression: <br>
Decode Huffman-encoded binary back to original text
### File I/O:<br>
Read from and write to text files
### Huffman Tree: <br>
Builds and utilizes Huffman tree structure for encoding/decoding
### Frequency Analysis:<br>
Analyzes character frequencies for optimal encoding

## File Format:<br>
Message File - Contains the binary string to be decoded (e.g., "110100101...")<br>
Scheme File - Contains the Huffman encoding scheme in the format expected by the huffmanTree() method.


## Requirements:<br>
Java 8 or higher<br>
Input files must be in the same directory as the program<br>
Valid Huffman encoding scheme file


## Troubleshooting:
File not found error: Ensure your files are in the correct directory and named properly<br>
Current working directory: The program looks for files in the directory where you run the Java command<br>
File naming: Double-check that your files follow the exact naming pattern: message.[input].txt and scheme.[input].txt


## Dependencies:
Standard Java libraries only (Scanner, File, IOException)<br>
Custom methods: huffmanTree(), dehuff(), huffmanize()<br>
TreeNode class for Huffman tree structure



/**
 * @author AJWuu
 */

package trie;

public class Trie {
	
	/*
	 * Trie is a play on words "retrieve" and "tree"
	 * Tries store strings in a tree with one character per node
	 * Trie is designed to help group together values that share a common prefix
	 * Differs from a BST in that
	 *	(1) values with the same prefix could be stored in completely different part of the tree
	 * 	(2) each node in a trie can contain many child nodes
	 * Each child reference in a trie should be labelled with a part of the prefix
	 * 
	 * Trie mainly deals with:
	 * Set of strings
	 * Quickly look up if word is part of the vocabulary
	 * auto-complete: find all words with a certain prefix
	 * 
	 * Complexity: insert, search and delete: O(l) -> l for the length of the string
	 * Number of Nodes: count the root in, but if there is an extra terminal node, don't count the terminal in
	 *     eg. 7 for meme, mean and me： ⚪ --m--> ⚪ --e--> ⨂ --m--> ⚪ --e--> ⨂ (this is not an extra node, but the one with "e")
	 *                                                         \--a--> ⚪ --n--> ⨂
	 */

	static final int ALPHABET_SIZE = 26;

	static class TrieNode {
		TrieNode[] children = new TrieNode[ALPHABET_SIZE];
		boolean isEndOfWord;

		TrieNode(){
			isEndOfWord = false;
			for (int i=0; i<ALPHABET_SIZE; i++) {
				children[i] = null;
			}
		}
	};

	static TrieNode root;

	//If not present, insert key into trie
	//If the key is prefix of trie node, mark as leaf node
	static void insert(String key) {
		int length = key.length(), index;
		TrieNode trie = root;
		
		for (int level=0; level<length; level++) {
			index = key.charAt(level) - 'a';
			if (trie.children[index] == null) {
				trie.children[index] = new TrieNode();
			}
			trie = trie.children[index];
		}

		trie.isEndOfWord = true;
	}

	//Traverse through the trie according to the target string
	//After found all characters, we need to go one step further to check if the next level can be the end of a word
	static boolean search(String key) {
		int length = key.length(), index;
		TrieNode trie = root;

		for (int level=0; level<length; level++) {
			index = key.charAt(level) - 'a';

			if (trie.children[index] == null) {
				return false;
			}

			trie = trie.children[index];
		}

		return (trie.isEndOfWord);
	}

	public static void main(String args[]) {
		String keys[] = {"the", "a", "there", "answer", "any", "by", "bye", "their"};
		String output[] = {"Not present in trie", "Present in trie"};
		root = new TrieNode();

		for (int i=0; i<keys.length; i++) {
			insert(keys[i]);
		}

		if(search("the") == true) {
			System.out.println("the --- " + output[1]);
		}
		else {
			System.out.println("the --- " + output[0]);
		}

		if(search("these") == true) {
			System.out.println("these --- " + output[1]);
		}
		else {
			System.out.println("these --- " + output[0]);
		}

		if(search("their") == true) {
			System.out.println("their --- " + output[1]);
		}
		else {
			System.out.println("their --- " + output[0]);
		}

		if(search("thaw") == true) {
			System.out.println("thaw --- " + output[1]);
		}
		else {
			System.out.println("thaw --- " + output[0]);
		}

	}
}

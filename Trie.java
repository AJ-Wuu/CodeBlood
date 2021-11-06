/**
 * @author AJWuu
 */

package trie;

public class Trie {
	
	/*
	 * Tries store strings in a tree with one character per node
	 * 
	 * Trie mainly deals with:
	 * Set of strings
	 * Quickly look up if word is part of the vocabulary
	 * auto-complete: find all words with a certain prefix
	 * 
	 * insert, search and delete: O(l) -> l for the length of the string
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

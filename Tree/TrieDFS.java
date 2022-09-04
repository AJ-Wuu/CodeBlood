/*
 * The distance between 2 binary strings is the sum of their lengths after removing the common prefix.
 * For example: the common prefix of 1011000 and 1011110 is 1011 so the distance is len("000") + len("110") = 3 + 3 = 6.
 * Given a list of binary strings, pick a pair that gives you maximum distance among all possible pair and return that distance.
 */

import java.util.*;

class Trie {
    boolean isEnd;
    Trie child[] = new Trie[2]; //0 or 1
}

public class trieDFS {

    static int maxdistance;

    public static int solve(String s[]) {
        maxdistance = 0;
        Trie root = new Trie();
        for (String word : s) {
            insert(root, word);
        }
        query(root);
        return maxdistance;
    }

    public static void insert(Trie root, String s) {
        Trie cur = root;
        for (char c : s.toCharArray()) {
            int i = c - '0';
            if (cur.child[i] == null) {
                cur.child[i] = new Trie();
            }
            cur = cur.child[i];
        }
        cur.isEnd = true;
    }

    public static int query(Trie root) { //DFS
        if (root == null) {
            return 0;
        }
        int left = query(root.child[0]);
        int right = query(root.child[1]);
        if ((left > 0 && right > 0) || root.isEnd) {
            maxdistance = Math.max(maxdistance, left + right);
        }
        return 1 + Math.max(left, right);
    }

    public static void main(String[] args) {
        String[] s = new String[]{"1011000", "1011110"};
        System.out.println(solve(s));
        s = new String[]{"101", "10"};
        System.out.println(solve(s));
    }

}

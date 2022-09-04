import java.util.*;

class MyStack {
    Node list[];
    int top;
    MyStack(int size) {
        this.list = new Node[size];
        this.top = -1;
    }
}
class Node {
    String query;
    int idx;
    String w;
    Node(String query, String y){
        this.query = query;
        this.w = y;
    }
    Node(String query, int index){
        this.query = query;
        this.idx = index;
    }
}

public class textEditor {

    public static String[] editor(String[] operations) {
        String[] results = new String[operations.length];
        String str = "";
        int n = operations.length;
        MyStack stack = new MyStack(n);

        for (int i=0; i<n; ++i) {
            String details[] = operations[i].split(" ");
            String query = details[0];
            if (query.equals("INSERT")) {
                Node newNode = new Node(query, str.length());
                stack.top++;
                stack.list[stack.top] = newNode;
                str += details[1];
            }
            else if (query.equals("BACKSPACE")) {
                Node newNode;
                if (str.length() != 0) {
                    newNode = new Node(query, str.substring(str.length() - 1));
                    str = str.substring(0,str.length()-1);
                }
                else {
                    newNode = new Node(query, "");
                    str = "";
                }
                stack.top++;
                stack.list[stack.top] = newNode;
            }
            else if (query.equals("UNDO")) {
                Node newNode = stack.list[stack.top];
                stack.top--;
                if (newNode.query.equals("INSERT")) {
                    str = str.substring(0,newNode.idx);
                }
                else if (newNode.query.equals("BACKSPACE")) {
                    str += newNode.w;
                }
            }
            results[i] = str;
        }

        return results;
    }

    public static void main(String[] args) {
        String[] operations = new String[]{"INSERT a",
                "BACKSPACE",
                "BACKSPACE",
                "UNDO",
                "UNDO",
                "INSERT b",
                "UNDO",
                "BACKSPACE",
                "UNDO"};
        String[] solution = editor(operations);
        for (int i=0; i<solution.length; i++) {
            System.out.println(solution[i]);
        }
    }
}

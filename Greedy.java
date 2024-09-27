/**
 * Dynamic Programming vs Greedy Approach.
 * Both Greedy and DP construct an optimal solution of a subproblem based on optimal solutions of smaller subproblems.
 * Both require that an optimal solution of current subproblem is based on optimal solutions of dependent subproblems referred to as optimal substructure property.
 * However, the main difference is that Greedy have a local choice of the subproblem that will lead to the optimal answer.
 * On the other hand, DP would solve all dependent subproblems and then select one that would lead to optimal solution.
 * 
 * DP can not be applied over the structures following cyclic property, whereas, greedy can be applied over cyclic structures.
 * DP is slower on average, but it ensures the optimality of the answer.
 * Greedy is hard in proving that it is optimal, but it has much simpler implementation.
 */

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #729 - My Calendar I
// Use doubly-linked list with node
class MyCalendar {
    class Node {
        int start;
        int end;
        Node left, right;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    Node root;

    public MyCalendar() {
        root = null;
    }

    public boolean book(int start, int end) {
        if (root == null) {
            root = new Node(start, end);
            return true;
        }
        Node curr = root;
        while (curr != null) {
            if (end <= curr.start) {
                if (curr.left == null) {
                    curr.left = new Node(start, end);
                    return true;
                }
                curr = curr.left;
            } else if (start >= curr.end) {
                if (curr.right == null) {
                    curr.right = new Node(start, end);
                    return true;
                }
                curr = curr.right;
            } else
                return false;
        }
        return false;
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #731 - My Calendar II
// Method 1: TreeMap
// Analysis: 1. this method can be extended to avoid any interval being booked N times
//           2. TreeMap is sorted by default, with insert costing O(nlogn)
//           3. saves space complexity, costing O(n)
class MyCalendarTwo {
    private TreeMap<Integer, Integer> map;

    public MyCalendarTwo() {
        map = new TreeMap<>(); 
    }

    public boolean book(int start, int end) {
        map.put(start, map.getOrDefault(start, 0) + 1); // start is 1
        map.put(end, map.getOrDefault(end, 0) - 1);     // end is -1
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // when any existing interval is done, count will be offset by the +1 and -1
            // for example: with [10, 50) and [20, 40), wanna book [30, 35)
            //       key -> 10  20  30  35  40  50
            //     value -> 1   1   1   -1  -1  -1
            //     count -> 1   2   3 (❌, triple booked)
            count += entry.getValue();
            if (count > 2) {
                map.put(start, map.get(start) - 1);
                if (map.get(start) == 0) {
                    map.remove(start);
                }
                map.put(end, map.get(end) + 1);
                if (map.get(end) == 0) {
                    map.remove(end);
                }
                return false;
            }
        }
        return true;
    }
}

// Method 2: Two Lists
// Analysis: 1. insert takes O(n) only
//           2. space complexity takes O(n), but doubled than Method 1 as two lists are kept
class MyCalendarTwo {
    private List<int[]> bookings;
    private List<int[]> overlapBookings;

    // return true if the booking [start1, end1) & [start2, end2) overlaps
    private boolean doesOverlap(int start1, int end1, int start2, int end2) {
        return Math.max(start1, start2) < Math.min(end1, end2);
    }

    // return overlapping booking between [start1, end1) & [start2, end2)
    private int[] getOverlapped(int start1, int end1, int start2, int end2) {
        return new int[] { Math.max(start1, start2), Math.min(end1, end2) };
    }

    public MyCalendarTwo() {
        bookings = new ArrayList<>();
        overlapBookings = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        // return false if the new booking has an overlap with the existing double-booked bookings
        for (int[] booking : overlapBookings) {
            if (doesOverlap(booking[0], booking[1], start, end)) {
                return false;
            }
        }

        // add the double overlapping bookings if any with the new booking
        for (int[] booking : bookings) {
            if (doesOverlap(booking[0], booking[1], start, end)) {
                overlapBookings.add(
                        getOverlapped(booking[0], booking[1], start, end));
            }
        }

        // add the new booking to the list of bookings
        bookings.add(new int[] { start, end });
        return true;
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #253 - Meeting Rooms II
// Key: arrival time will always be earlier than departure time, we only need to get the peek number of people in the room
// Note: treat the "==" on double carefully due to precision differences (could be avoided with java.math.BigDecimal)
public static int countChairs(int[] arrive, int[] depart) {
    double[] process = new double[arrive.length + depart.length];
    for (int i=0; i<arrive.length; i++) {
        process[i] = arrive[i] + 0.1; // to distinguish arrive or depart
    }
    for (int i=0; i<depart.length; i++) {
        process[i+arrive.length] = depart[i] + 0.2;
    }

    int count = 0;
    int[] chairs = new int[arrive.length + depart.length];
    Arrays.sort(process);
    for (int i=0; i<process.length; i++) {
        if (process[i] % 1 < 0.15) { // precision requirement
            count++;
        }
        else {
            count--;
        }
        chairs[i] = count;
    }
    Arrays.sort(chairs);
    return chairs[arrive.length + depart.length - 1];
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #68 - Text Justification -> See Projects/Text Justification/Greedy.java

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #503 - Next Greater Element II
// Notion here: get E1, stack empty, store E1
//              if E1 > E2, store E2; else, assign the return value for E1 (let's concern the situation when E1 > E2 now) and then store E2
//              if E2 > E3, store E3; else, E2 has its return value, so assign it and compare E1 with E3
//                                          if E1 < E3, return the value for E1; else, store E3
//              ...
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    for (int i=0; i<n; i++) {
        result[i] = -1;
    }
    Stack<Integer> key = new Stack<Integer>();
    Stack<Integer> index = new Stack<Integer>();
    for (int i=0; i<2*n-1; i++) {
        if (i < n) {
            while (!key.isEmpty() && key.peek() < nums[i]) {
                key.pop();
                result[index.pop()] = nums[i];
            }
            key.push(nums[i]);
            index.push(i);
        }
        else {
            int j = i - n;
            while (!key.isEmpty() && key.peek() < nums[j]) {
                key.pop();
                result[index.pop()] = nums[j];
            }
        }
    }
    return result;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// 556 - Next Greater Element III
// Steps after separating the number into digits:
// 1. From back to front, find the first pair that makes numList.get(j) < numList.get(i) (i < j)
// 2. Keep the digits before i as it is, numList.get(j) should be in position i, and numList.get(i) should be in position j (indices be like {x,x,x,j,y,y,y,i,z,z,z})
// 3. All elements AFTER current index = i should be sorted from small to large (directly using Arrays.sort(currArray) could do)
// 4. Check if the new integer overflow in 32-bit
//    4.1 this could be done by checking each step (i.e. if (Integer.MAX_VALUE - result > currDigit) { return -1; }), both addition and multiplication
//    4.2 or, we could use a long variable (resultInLong) to store it, and check if (resultInLong > Integer.MAX_VALUE) { return -1; } else { return (int)resultInLong; } 

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #435 - Non-overlapping Intervals
class myComparator implements Comparator<Interval> {
    public int compare(Interval a, Interval b) {
        return a.end - b.end;
    }
}

public int eraseOverlapIntervals(Interval[] intervals) {
    if (intervals.length == 0)  {
        return 0;
    }

    Arrays.sort(intervals, new myComparator());
    int end = intervals[0].end;
    int count = 1;        
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i].start >= end) {
            end = intervals[i].end;
            count++;
        }
    }
    return intervals.length - count;
}

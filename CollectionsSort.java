//List - accept index, accept duplicate
List<Integer> sortedList = new ArrayList<>();
Collections.sort(sortedList, newComparator);

public class OverrideListSort {
    static class Employee{
        int id;
        String name;
        int salary;

        public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
            id = temp++;
        }
    }

    public void getEmployeesAlphabetical(List<Employee> list) {
        //Sort the list by employee name
        Collections.sort(list, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.name.compareTo(o2.name);
            }
        });
    }

    public void getEmployeeWithHighestSalary(List<Employee> list) {
        //Sort the list as per employee name
        Collections.sort(list, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o2.salary-o1.salary;
            }
        });
    }

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Carl", 10000));
        employeeList.add(new Employee("Andy", 20000));
        employeeList.add(new Employee("Bob", 30000));
        employeeList.add(new Employee("Drake", 5000));

        OverrideListSort o = new OverrideListSort(); //Important!!!
        o.getEmployeesAlphabetical(employeeList);
        o.getEmployeeWithHighestSalary(employeeList);
    }
}

//Map - index == key, no duplicate
class CustomizedHashMap implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return -o1.getValue().compareTo(o2.getValue());
    }
}

public class OrderedLinkedHashMap {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        
        map.put("a", 11);
        map.put("B", 12);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);
        map.put("f", 6);
        map.put("g", 7);
        map.put("h", 8);
        map.put("i", 9);
        map.put("j", 3);
        map.put("k", 2);
        map.put("l", 1);

        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entries,new CustomizedHashMap());

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
            System.out.print( sortedMap.put(entry.getKey(), entry.getValue())+" ");
        }
    }
}

//Set - no index, no duplicate
Comparator<Employee> newComparator = new Comparator<Employee>() {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o2.salary-o1.salary;
    }
};
SortedSet<Employee> employeeSet = new TreeSet<Employee>(newComparator);

//Queue - no index, accept duplicate
PriorityQueue<Employee> employeeQueue = new PriorityQueue<Employee>(newComparator);

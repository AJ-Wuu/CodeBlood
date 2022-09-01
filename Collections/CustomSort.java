//List - accept index, accept duplicate
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

    public void getEmployeeWithHighestSalary(List<Employee> list){
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

//Set - no index, no duplicate
Comparator<Employee> newComparator = new Comparator<Employee>() {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o2.salary-o1.salary;
    }
};
SortedSet<Employee> employeeSet = new TreeSet<Employee>();

//Queue - no index, accept duplicate
PriorityQueue<Employee> employeeQueue = new PriorityQueue<Employee>();

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/3/7 1:55
 */
public class StreamDemo {


    void test() {
        List<Employee> employees = Arrays.asList(
                new Employee("学生1", 12, "HZAU"),
                new Employee("学生2", 17, "HZAUU"),
                new Employee("学生3", 13, "HZAUU"),
                new Employee("学生4", 10, "HZAU"));
        //统计每个学校学生的平均年龄
        employees.stream().filter(employee -> employee.getAge() > 11).
                collect(Collectors.groupingBy(Employee::getSchool, Collectors.averagingInt(Employee::getAge)))
                .forEach((k, v) -> System.out.println(String.format("学校：%s, 平均年龄:%s", k, v)));

        Map<String, Double> collect = employees.stream().filter(employee -> employee.getAge() > 11).
                collect(Collectors.groupingBy(Employee::getSchool, Collectors.averagingInt(Employee::getAge)));

        //统计每个学校年龄大于11岁的人数
        employees.stream()
                .filter(employee -> employee.getAge() > 11)
                .collect(Collectors.groupingBy(Employee::getSchool, Collectors.counting()))
                .forEach((k, v) -> System.out.println(String.format("学校：%s\t统计人数：%d", k, v)));


        employees.stream().filter(employee -> employee.getAge() > 11).
                collect(Collectors.groupingBy(Employee::getSchool))
                .forEach((k, v) -> System.out.println(String.format("学校:%s\t年龄大于11岁学生的人数：%d", k, v.size())));


        //采集
        // 1. list
        // 2. map
        // 3. group by
        // 4. 数组
        // 5. 求最大值
        // 6. 求任意值
        System.out.println("----------采集----------");
        employees.stream().collect(Collectors.toMap(Employee::getSchool, Employee::getName, (e1, e2) -> e1))
                .forEach((k, v) -> System.out.println(String.format("学校：%s\t姓名：%s", k, v)));

        Stream<Employee> stream1 = employees.stream().filter(e -> e.getName().equals("学生1"));
        Stream<Employee> stream2 = stream1.filter(e -> e.getAge() > 11);
        Stream<Employee> stream3 = stream2.filter(e -> e.getAge() > 11);


    }

    public static void main(String[] args) {
        new StreamDemo().test();
    }

}

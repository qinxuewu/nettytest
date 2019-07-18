package thrift.generated;

import org.apache.thrift.TException;

/**
 *  服务调用类
 * @author qxw
 * @version 1.00
 * @time  18/7/2019 下午 1:20
 */
public class PersonServiceImpl implements  PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("查询方法调用： "+username);
        Person person=new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);
        return  person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("添加方法调用： ");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
    }
}

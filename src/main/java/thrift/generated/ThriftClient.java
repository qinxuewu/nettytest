package thrift.generated;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 客服端
 * @author qxw
 * @version 1.00
 * @time  18/7/2019 下午 1:30
 */
public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport=new TFramedTransport(new TSocket("localhost",8899),600);
        TProtocol protocol=new TCompactProtocol(transport);
        PersonService.Client client=new PersonService.Client(protocol);

        try {
            transport.open();
            Person person=client.getPersonByUsername("qx");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());

            Person person1=new Person();
            person1.setUsername("dddddd");
            person1.setAge(111);
            person.setMarried(true);
            client.savePerson(person1);

        }catch (Exception e){
            transport.close();
        }
    }
}

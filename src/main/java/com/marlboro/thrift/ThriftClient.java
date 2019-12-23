package com.marlboro.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class ThriftClient {

    public static void main(String[] args) {
        TTransport tTransport = new TFramedTransport(new TSocket("localhost",8899),600);
        TProtocol protocol = new TCompactProtocol(tTransport);

        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();

            Person person = client.getPersonByUserName("张三");

            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("----------");
            Person person2 = new Person();
            person2.setUsername("李四");
            person2.setAge(30);
            person2.setMarried(true);

            client.savePerson(person2);

        }catch (Exception ex){
            throw  new RuntimeException(ex.getMessage(),ex);
        }finally {
            tTransport.close();
        }
    }
}

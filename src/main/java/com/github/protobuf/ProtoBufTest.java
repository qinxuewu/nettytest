package com.github.protobuf;

public class ProtoBufTest {
    public static void main(String[] args) throws  Exception{
        // 构造对象 初始化数据
      DataInfo.Person person=  DataInfo.Person.newBuilder()
              .setName("张三").setAge(20).setAddress("广州").build();
        // 转换成字节数组
        byte[] personByteArray=person.toByteArray();

        //
        DataInfo.Person person1=DataInfo.Person.parseFrom(personByteArray);
        System.out.println(person1);
        System.out.println(person1.getName());
        System.out.println(person1.getAge());
        System.out.println(person1.getAddress());
    }
}

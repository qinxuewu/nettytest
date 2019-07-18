namespace java thrift.generated
// go的包定义
namespace go  thrift.generated

// 定义类型
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person {
    1:optional String username,
    2:optional int age,
    3:optional boolean married
}

exception DataException {
    1:optional String meaages,
    2:optional String callStack,
    3:optional String date
}

service PersonService {
    //  根据名称查询  抛出自定义异常
    Person getPersonByUsername(1:required String username) throws (1:DataException dataEception),
    // 添加
    void savePerson(1:required Person person) throws (1:DataException dataEception)
}
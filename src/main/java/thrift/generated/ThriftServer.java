package thrift.generated;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * 服务端
 * @author qxw
 * @version 1.00
 * @time  18/7/2019 下午 1:29
 */
public class ThriftServer {
    public static void main(String[] args) throws Exception {
        // 异步非阻塞
        TNonblockingServerSocket socket=new TNonblockingServerSocket(8899);
        // 半同步半异步
        THsHaServer.Args arg=new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor=new PersonService.Processor<>(new PersonServiceImpl());
        arg.protocolFactory(new TCompactProtocol.Factory()); //压缩格式
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));


       // 半同步半异步的服务模型
        TServer server=new THsHaServer(arg);

        System.out.println("server start....");

        // 启动  死循环 永远不会退出
        server.serve();

    }
}

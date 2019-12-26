
# 目录说明
- 【com.github.text1】Netty入门实现简单的http  
- 【com.github.text2】Netty入门scoker编程
- 【com.github.text3】Netty入门多客户端连接与通信
- 【com.github.text4】Netty入门读写监测机制与长连接要素
- 【com.github.text4】Netty入门netty对WebSocket的支援
- 【com.github.protobuf】protobuf集成Netty与多协议消息传递

# NIO 和 IO 的对比
- IO 基于流(Stream oriented), 而 NIO 基于 Buffer (Buffer oriented)
- IO 操作是阻塞的, 而 NIO 操作是非阻塞的
- IO 没有 selector 概念, 而 NIO 有 selector 概念.
- 我们可以在同一个 Channel 中执行读和写操作, 然而同一个 Stream 仅仅支持读或写.
- Channel 可以异步地读写, 而 Stream 是阻塞的同步读写.
- Channel 总是从 Buffer 中读取数据, 或将数据写入到 Buffer 中.

# Channel几种类型
- FileChannel, 文件操作
- DatagramChannel, UDP 操作
- SocketChannel, TCP 操作
- ServerSocketChannel, TCP 操作, 使用在服务器端.

# Buffer几种类型
- 一个 Buffer 其实就是一块内存区域, 我们可以在这个内存区域中进行数据的读写.
- Buffer 类型有:
- ByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer
- NIO Buffer 的步骤如下: 将数据写入到 Buffer,调用 Buffer.flip()方法, 将 NIO Buffer 转换为读模式.从 Buffer 中读取数据,调用 Buffer.clear() 或 Buffer.compact()方法, 将 Buffer 转换为写模式

#  Buffer方法概述
- Capacity：一个内存块会有一个固定的大小, 即容量(capacity), 我们最多写入capacity 个单位的数据到 Buffer 中
- Position: 当从一个 Buffer 中写入数据时, 我们是从 Buffer 的一个确定的位置(position)开始写入的. 在最初的状态时, position 的值是0. 每当我们写入了一个单位的数据后, position 就会递增一.
- 当我们从 Buffer 中读取数据时, 我们也是从某个特定的位置开始读取的. 当我们调用了 filp()方法将 Buffer 从写模式转换到读模式时, position 的值会自动被设置为0, 每当我们读取一个单位的数据, position 的值递增1,position 表示了读写操作的位置指针.
- limit:  limit - position 表示此时还可以写入/读取多少单位的数据;例如在写模式, 如果此时 limit 是10, position 是2, 则表示已经写入了2个单位的数据, 还可以写入 10 - 2 = 8 个单位的数据
- Buffer.rewind()方法可以重置 position 的值为0, 因此我们可以重新读取/写入 Buffer 了.
- Buffer.mark()将当前的 position 的值保存起来, 随后可以通过调用 Buffer.reset()方法将 position 的值回复回来.
- Buffer 的读/写模式共用一个 position 和 limit 变量;当从写模式变为读模式时, 原先的 写 position 就变成了读模式的 limit.
- clear方法 将 positin 设置为0, 将 limit 设置为 capacity;在一个已经写满数据的 buffer 中, 调用 clear, 可以从头读取 buffer 的数据.
- 可以通过 equals() 或 compareTo() 方法比较两个 Buffer(相同类型的,剩余的数据个数是相同的,剩余的数据都是相同的);比较两个 Buffer 时, 并不是 Buffer 中的每个元素都进行比较, 而是比较 Buffer 中剩余的元素


# Netty的Selector
- selector 是 NIO 中才有的概念, 它是 Java NIO 之所以可以非阻塞地进行 IO 操作的关键
- 通过 Selector, 一个线程可以监听多个 Channel 的 IO 事件，当我们向一个 Selector 中注册了 Channel 后, Selector 内部的机制就可以自动地为我们不断地查询(select) 这些注册的 Channel 是否有已就绪的 IO 事件
- 通过 Selector.open()方法, 我们可以创建一个选择器;通过channel.register将 Channel 注册到选择器中
- 如果一个 Channel 要注册到 Selector 中, 那么这个 Channel 必须是非阻塞的, 即channel.configureBlocking(false); 因此 FileChannel 是不能够使用选择器的, 因为 FileChannel 都是阻塞的.

# Channel.register()注册时对应的事件类型
- Connect, 即连接事件(TCP 连接), 对应于SelectionKey.OP_CONNECT
- Accept, 即确认事件, 对应于SelectionKey.OP_ACCEPT
- Read, 即读事件, 对应于SelectionKey.OP_READ, 表示 buffer 可读.
- Write, 即写事件, 对应于SelectionKey.OP_WRITE, 表示 buffer 可写.
- 一个 Channel 仅仅可以被注册到一个 Selector 一次, 如果将 Channel 注册到 Selector 多次, 那么其实就是相当于更新 SelectionKey 的

# Selector 的基本使用流程
- 通过 Selector.open() 打开一个 Selector.
- 将 Channel 注册到 Selector 中, 并设置需要监听的事件(interest set)
- 不断重复:调用 select() 方法; 调用 selector.selectedKeys() 获取 selected keys; 迭代每个 selected key:
- select()方法介绍: 在刚初始化的Selector对象中，这三个集合都是空的。 通过Selector的select（）方法可以选择已经准备就绪的通道 
- 从 selected key 中获取 对应的 Channel 和附加信息(如果有的话)
- 判断是哪些 IO 事件已经就绪了, 然后处理它们. 如果是 OP_ACCEPT 事件 则调用 "SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept()" 获取 SocketChannel, 并将它设置为 非阻塞的, 然后将这个 Channel 注册到 Selector 中.
- 根据需要更改 selected key 的监听事件
- 将已经处理过的 key 从 selected keys 集合中删除
- 当调用了 Selector.close()方法时, 我们其实是关闭了 Selector 本身并且将所有的 SelectionKey 失效, 但是并不会关闭 Channel.

## SelectionKey介绍
- 一个SelectionKey键表示了一个特定的通道对象和一个特定的选择器对象之间的注册关系。

```
key.attachment(); //返回SelectionKey的attachment，attachment可以在注册channel的时候指定。
key.channel(); // 返回该SelectionKey对应的channel。
key.selector(); // 返回该SelectionKey对应的Selector。
key.interestOps(); //返回代表需要Selector监控的IO操作的bit mask
key.readyOps(); // 返回一个bit mask，代表在相应channel上可以进行的IO操作。
```
## select()方法介绍：
- 在刚初始化的Selector对象中，这三个集合都是空的。 通过Selector的select（）方法可以选择已经准备就绪的通道;些通道包含你感兴趣的的事件
- 比如你对读就绪的通道感兴趣，那么select（）方法就会返回读事件已经就绪的那些通道

``
int select()：阻塞到至少有一个通道在你注册的事件上就绪了。
int select(long timeout)：和select()一样，但最长阻塞时间为timeout毫秒。
int selectNow()：非阻塞，只要有通道就绪就立刻返回。
``

# NIO零拷贝及用户空间与内核空间切换
## 什么是零拷贝
- 零拷贝描述的是CPU不执行拷贝数据从一个存储区域到另一个存储区域的任务，这通常用于通过网络传输一个文件时以减少CPU周期和内存带宽。

## 零拷贝给我们带来的好处
- 减少甚至完全避免不要的CPU拷贝，让CPU解脱出来去执行其他的任
- 减少内存带宽的占用
- 通常零拷贝技术还能够减少用户空间和操作系统内核空间的上下文切换

## 零拷贝的实现
- 实际的实现并没有真正的标准，取决于操作系统如何实现这一点，零拷贝本质完全依赖操作系统，操作系统支持就有，不支持就没有，不依赖Java本身
- HeapByteBuffer,DirectByteBuffer;NIO只是解决部分拷贝过程、
- 零拷贝实现了内存的数据重用性 减少拷贝过程次数来提高性能

# ChannelInboundHandlerAdapter和SimpleChannelInboundHandler的区别
- SimpleChannelInboundHandler是有泛型参数的。可以指定一个具体的类型参数，通过decoder配合使用，非常方便。
- ChannelInboundHandlerAdapter则是直接操作byte数组的
- SimpleChannelInboundHandler是继承ChannelInboundHandlerAdapter的，也拥有ChannelInboundHandlerAdapter的方法。
- SimpleChannelInboundHandler的channelRead相比ChannelInboundHandlerAdapter而言，主要做了类型匹配以及用完之后释放指向保存该消息的 ByteBuf 的内存引用
- SimpleChannelInboundHandler的好处是可以处理不同的类型对象，并且可以做释放。ChannelInboundHandlerAdapter 的好处则是更自由，在异步的场景下更适合
 
  
                                                  
# Netty ChannelOption参数详解
- `ChannelOption.SO_BACKLOG`对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
- `ChannelOption.SO_REUSEADDR`对应于套接字选项中的SO_REUSEADDR，这个参数表示允许重复使用本地地址和端口， 比如，某个服务器进程占用了TCP的80端口进行监听，此时再次监听该端口就会返回错误，使用该参数就可以解决问题，该参数允许共用该端口，这个在服务器程序中比较常使用;  比如某个进程非正常退出，该程序占用的端口可能要被占用一段时间才能允许其他进程使用，而且程序死掉以后，内核一需要一定的时间才能够释放此端口，不设置SO_REUSEADDR就无法正常使用该端口。
- `Channeloption.SO_KEEPALIVE`参数对应于套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
- `ChannelOption.SO_SNDBUF`参数对应于套接字选项中的SO_SNDBUF，ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区的大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。
- `ChannelOption.SO_LINGER`参数对应于套接字选项中的SO_LINGER,Linux内核默认的处理方式是当用户调用close（）方法的时候，函数返回，在可能的情况下，尽量发送数据，不一定保证会发生剩余的数据，造成了数据的不确定性，使用SO_LINGER可以阻塞close()的调用时间，直到数据完全发送
- `ChannelOption.TCP_NODELAY`参数对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关,Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，虽然该方式有效提高网络的有效负载，但是却造成了延时，而该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输。
- `IP_TOS`:IP参数，设置IP头部的Type-of-Service字段，用于描述IP包的优先级和QoS选项
- `ALLOW_HALF_CLOSURE`Netty参数，一个连接的远端关闭时本地端是否关闭，默认值为False。值为False时，连接自动关闭；为True时，触发ChannelInboundHandler的userEventTriggered()方法，事件为ChannelInputShutdownEvent

# Netty提供的多个解码器
- LineBasedFrameDecoder （回车换行分包）
- DelimiterBasedFrameDecoder（特殊分隔符分包）
- FixedLengthFrameDecoder（固定长度报文来分包）
- LengthFieldBasedFrameDecoder（自定义长度来分包）

> LengthFieldBasedFrameDecoder组合参数如下:

```
lengthFieldOffset = 0；//长度字段的偏差
lengthFieldLength = 2；//长度字段占的字节数
lengthAdjustment = 0；//添加到长度字段的补偿值
initialBytesToStrip = 0。//从解码帧中第一次去除的字节数

```

# Netty的ByteBuf如何在工作？
- 写入数据到ByteBuf后，写入索引是增加的字节数量。开始读字节后，读取索引增加。你可以读取字节，直到写入索引和读取索引处理相同的位置，次数若继续读取，则会抛出IndexOutOfBoundsException。
- 调用ByteBuf的任何方法开始读/写都会单独维护读索引和写索引。ByteBuf的默认最大容量限制是Integer.MAX_VALUE，写入时若超出这个值将会导致一个异常。
- ByteBuf类似于一个字节数组，最大的区别是读和写的索引可以用来控制对缓冲区数据的访问。

# Netty时会遇到3种不同类型的ByteBuf
- Heap Buffer(堆缓冲区)：最常用的类型是ByteBuf将数据存储在JVM的堆空间，这是通过将数据存储在数组的实现。堆缓冲区可以快速分配，当不使用时也可以快速释放。它还提供了直接访问数组的方法，通过ByteBuf.array()来获取byte[]数据。
- Direct Buffer(直接缓冲区)：直接缓冲区，在堆之外直接分配内存。直接缓冲区不会占用堆空间容量，使用时应该考虑到应用程序要使用的最大内存容量以及如何限制它。直接缓冲区在使用Socket传递数据时性能很好，因为若使用间接缓冲区，JVM会先将数据复制到直接缓冲区再进行传递；但是直接缓冲区的缺点是在分配内存空间和释放内存时比堆缓冲区更复杂，而Netty使用内存池来解决这样的问题，这也是Netty使用内存池的原因之一。直接缓冲区不支持数组访问数据，但是我们可以间接的访问数据数组
# 参考
- netty高并发-张龙
- https://segmentfault.com/a/1190000006824091
- https://www.cnblogs.com/snailclimb/p/9086334.html
- https://blog.csdn.net/u010853261/article/details/55803933
- http://cxytiandi.com/blog/detail/17641
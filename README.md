
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

# 参考
- netty高并发-张龙
- https://segmentfault.com/a/1190000006824091
- https://www.cnblogs.com/snailclimb/p/9086334.html
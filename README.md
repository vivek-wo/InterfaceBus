# InterfaceBus
**接口事件总线**

思路和部分代码实现参考于EventBus,地址：https://github.com/greenrobot/EventBus

**修改**

为了更简单使用和更适合项目上使用，修改了EventBus代码，以便能更了解EventBus的实现机制。
1. 去除注解部分，修改为抽象类实现。
2. 新增以Event作为发布和订阅的主题，抽象类作为订阅实现。更加简单，更易于使用，但局限性变大了。

**使用**
* 创建订阅者
```
    val baseSubscribtionInterface = object : BaseSubscribtionInterface(){
        override fun onSubscribed(publish: Publish?) {
            println("MainActivity received : ${publish?.event} , ${publish?.`object`}")
        }
    }
```
* 添加订阅者监听
`InterfaceBus.getDefault().register(baseSubscribtionInterface, event)`
* 取消订阅者监听
`InterfaceBus.getDefault().unregister(baseSubscribtionInterface)`
* 发布事件
`InterfaceBus.getDefault().post(Publish(event, "onXmlClick"))`
<br />
* 优先级
优先级默认为0 ， 数值越大优先级越高 ， 否则按照订阅顺序往下传递事件
`InterfaceBus.getDefault().register(baseSubscribtionInterface, event, 10)`
<br />
* 取消继续往下传递事件
在 BaseSubscribtionInterface.onSubscribed(Publish) 内部使用 ，取消事件继续往下传递
```
    val baseSubscribtionInterface = object : BaseSubscribtionInterface() {
        override fun onSubscribed(publish: Publish?) {
            InterfaceBus.getDefault().cancelEventDelivery(event)//停止往下发布消息
            println("OtherActivity 1 received : ${publish?.event} , ${publish?.`object`}")
        }
    }
```
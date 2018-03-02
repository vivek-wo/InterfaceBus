# InterfaceBus
**接口事件总线**

思路和部分代码实现参考于EventBus,地址：https://github.com/greenrobot/EventBus

**修改**

为了更简单使用和更适合项目上使用，修改了EventBus代码，以便能更了解EventBus的实现机制。
1. 去除注解部分，修改为抽象类实现。
2. 新增以Event作为发布和订阅的主题，抽象类作为订阅实现。更加简单，更易于使用，但局限性变大了。

# android-tiny-bus
a tiny event bus for android

### Solution
1. we offer a simple event handler solution like EventBus.
2. easy to use, and reduce useless functions;

### Function
1. as a register and as a poster;
2. at anywhere we can add @Subscriber to receive the event;
3. (20190106) we can assign @Subscriber with ThreadMode(Main or Background);

### Technology
1. Desing Pattern
    1. single pattern
    2. static
2. Skill Point
    1. reflection
    2. ExecutorService
    3. synchronized

### Usage
1. register
TinyBus.getInstance().register(this); this should contains @Subscriber
2. post
TinyBus.getInstance().post(new TestEvent());
3. release
TinyBus.getInstance().release(this);

### TODO
1. ~~(done in 20190106) different thread switch between recevier and poster;~~


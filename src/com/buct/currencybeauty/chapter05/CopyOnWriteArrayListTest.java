package com.buct.currencybeauty.chapter05;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther: fb
 * @Date: 2019/4/26 15:03
 * @Description: 测试copyOnWriteArrayList的迭代器的弱一致性，即迭代器是数组的快照，保证在遍历过程中其他线程对数组的修改对迭代器来说是不可见的
 */
public class CopyOnWriteArrayListTest {

    private static CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        arrayList.add("hello");
        arrayList.add("world");
        arrayList.add("java");
        arrayList.add("python");
        Iterator<String> iterator = arrayList.iterator();

        new Thread(()->{
            arrayList.set(0, "hello111");
            arrayList.remove(2);
            arrayList.remove(1);
        }).start();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());

        }

    }
}

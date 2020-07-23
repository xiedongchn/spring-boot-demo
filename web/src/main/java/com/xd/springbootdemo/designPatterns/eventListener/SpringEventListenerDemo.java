package com.xd.springbootdemo.designPatterns.eventListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Description
 *
 * @author xd
 * Created on 2020/6/4 23:43
 */
public class SpringEventListenerDemo {

    public static void main(String[] args) {
        //Annotation（注解）驱动的上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //1、注册监听器
        context.addApplicationListener((ApplicationListener<MyApplicationEvent>) event -> System.out.println(event.getSource()));

        context.addApplicationListener(new ApplicationListener<MyApplicationEvent1>() {
            @Override
            public void onApplicationEvent(MyApplicationEvent1 event) {
                System.out.println("name:" + event.getContext().getId());
                ApplicationContext context1 = event.getContext();

            }
        });

        context.refresh();

        //2、发布事件
        context.publishEvent(new MyApplicationEvent("test"));
        context.publishEvent(new MyApplicationEvent1(context, "test"));
        //3、监听器得到事件
    }

    private static class MyApplicationEvent extends ApplicationEvent {

        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }

    private static class MyApplicationEvent1 extends ApplicationEvent {
        private final ApplicationContext context;

        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public MyApplicationEvent1(ApplicationContext context, Object source) {
            super(source);
            this.context = context;
        }

        public ApplicationContext getContext() {
            return context;
        }
    }


}

package com.sun.health.mongodb;

public class Txt2 {
    public static void main(String []args)
    {
        Master master=new Master();
        master.feed(new Dog(),new Bone());
    }
}
class Food
{
    String name;
    public void showName()
    {

    }
}
class Fish extends Food
{
    public void showName()
    {
        System.out.println("鱼");
    }
}
class Bone extends Food
{
    public void showName()
    {
        System.out.println("骨头");
    }
}

class Master
{
    public void feed(Animal an,Food f)
    {
        an.eat();
        f.showName();
    }
}
class Animal
{
    public int age;
    public String name;
    public void eat()
    {

    }
}
class Cat extends Animal
{
    public void  eat()
    {
        System.out.println("猫吃吃鱼！");
    }

}
class Dog extends Animal
{
    public void eat()
    {
        System.out.println("狗爱吃骨头！");
    }
}


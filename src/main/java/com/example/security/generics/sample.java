//dlansdn
package com.example.security.generics;
import java.awt.*;
import java.util.*;
import java.util.List;

class myclass{}
class C{
    protected static void swap(int a,int b){
        int temp = a;
        a=b;
        b=temp;
    }
}
 interface p{
  public String p1(String s);
}
interface  q{
    public String p1(int a);
}
class pq implements p,q{

    @Override
    public String p1(String s) {
        return "first";
    }

    @Override
    public String p1(int a) {
        return "second";
    }
}
class Synch {
    public synchronized void m1() {
    }
    public synchronized void m2() {
        m1();
    }
    public static void main(String[] args) {
        Synch s = new Synch();
        s.m2();
        System.out.println("Done");
    }
}
class parent {
    public parent(){};
    public void p1() {
        System.out.println("Base");
    }
}
public class sample extends parent{
    public void myBuf (StringBuffer s, StringBuffer s1) {
        s.append ("B");
        s = s1;
    }
    public void p1(){
        super.p1();
        System.out.println("Nirmal");
    }
    public static void main(String[] args) {
         sample s = new sample();
        s.p1();
    }
}

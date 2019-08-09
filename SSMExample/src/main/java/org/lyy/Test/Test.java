package org.lyy.Test;

public class Test {
    public static void main(String[] args) {
        int a=0;
        int temp=test(a);
        System.out.println(temp);

    }
    public static int test(int a){

        for(int i=0;i<5;i++)
        {
            a++;

        }
        return a;


    }

}

package com.ibm.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Lab62 {

    public static void main(String[] args) {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("inmindia.xml");
        CustomerDAO cdao = (CustomerDAO)ctx.getBean("cdao");

        //1. add customer

        CustomerTO cto = new CustomerTO(483,"srini","s@ibm.com",7768686,"Bangalore");
        cdao.addCustomer(cto);


        //2. updateCustomer

        CustomerTO cto1 = new CustomerTO(484,"vyankat","v@ibm.com",87767676,"Bangalore");
        cdao.addCustomer(cto1);

        //3. delete Customer
        cdao.deleteCustomer(477);
        System.out.println("Check Your Database");

        //4. grtCustomerByCid
        System.out.println("getCustomerByCid");
        cto = cdao.getCustomerByCid(481);
        System.out.println(cto);





    }
}

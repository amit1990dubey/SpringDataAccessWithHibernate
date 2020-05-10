package com.ibm.spring;


import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.util.ArrayList;
import java.util.List;

public class HibernateCustomerDAO implements  CustomerDAO {
    @Autowired
    HibernateTemplate hibernateTemplate;

    @Autowired
    HibernateTransactionManager transactionManager = null;

    public void addCustomer(CustomerTO cto)
    {
        Customer cust = new Customer(cto.getCname(),cto.getEmail(),cto.getPhone(),cto.getCity());

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        hibernateTemplate.save(cust);
        transactionManager.commit(transactionStatus);

    }

    public void deleteCustomer(int cid)
    {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        Customer c = (Customer)hibernateTemplate.load(Customer.class,cid);
        hibernateTemplate.delete(c, LockMode.NONE);
        transactionManager.commit(transactionStatus);
    }


    public List<CustomerTO> getAllCustomers()
    {
        List<CustomerTO> ctoList = new ArrayList<CustomerTO>();
        String hql = "from customer c";
        List<Customer> list = (List<Customer>)hibernateTemplate.find(hql);

        for(Customer c : list)
        {
            CustomerTO cto = new CustomerTO(c.getCid(),c.getCname(),c.getEmail(),c.getPhone(),c.getCity());
            ctoList.add(cto);
        }

        return  ctoList;

    }

    public void updateCustomer(CustomerTO cto)
    {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        Customer c = (Customer) hibernateTemplate.load(Customer.class,cto.getCid());
        c.setCid(cto.getCid());
        c.setEmail(cto.getEmail());
        c.setPhone(cto.getPhone());
        c.setCity(cto.getCity());
        hibernateTemplate.update(c, LockMode.NONE);
        transactionManager.commit(transactionStatus);

    }


    public CustomerTO getCustomerByCid(int cid)
    {
        Customer c = (Customer)hibernateTemplate.load(Customer.class,cid);
        CustomerTO cto = new CustomerTO(c.getCid(),c.getCname(),c.getEmail(),c.getPhone(),c.getCity());
        return cto;
    }


}

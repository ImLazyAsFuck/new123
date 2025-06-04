package com.bt.repo;

import com.bt.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepoImpl implements CustomerRepo{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> findAll(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Customer", Customer.class)
                    .getResultList();
        }
    }

    @Override
    public Customer findById(long id){
        try(Session session = sessionFactory.openSession()){
            return session.get(Customer.class, id);
        }
    }

    @Override
    public void save(Customer customer){
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.save(customer);
            tx.commit();
        }catch(Exception ex){
            if(tx != null){
                tx.rollback();
            }
        }
    }

    @Override
    public void delete(Customer customer){
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.delete(customer);
            tx.commit();
        }catch(Exception ex){
            if(tx != null){
                tx.rollback();
            }
        }
    }

    @Override
    public void update(Customer customer){
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.update(customer);
            tx.commit();
        }catch(Exception ex){
            if(tx != null){
                tx.rollback();
            }
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Customer WHERE lastName LIKE :name OR firstName LIKE :name ORDER BY firstName ASC, lastName ASC";
            return session.createQuery(hql, Customer.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }

}

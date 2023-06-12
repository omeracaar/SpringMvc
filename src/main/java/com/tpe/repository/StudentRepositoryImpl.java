package com.tpe.repository;

import com.tpe.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class StudentRepositoryImpl implements StudentRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Student student) {

        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        session.saveOrUpdate(student);//db de böyle bir kayit varsa gunceller yoksa olusturur

        tx.commit();
        session.close();
        sessionFactory.close();

    }

    @Override
    public List<Student> findAll() {
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();

        List<Student> studentList=session.createQuery("FROM Student", Student.class).getResultList();

        tx.commit();
        session.close();

        return studentList;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();

        Student student=session.get(Student.class,id);//olmayan id de student=null->NullPointerException
        Optional<Student> optional=Optional.ofNullable(student);//student null ise bos bir optional obj doner

        tx.commit();
        session.close();
        return optional;
    }

    @Override
    public void delete(Long id) {
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();

        Student student=session.load(Student.class,id);
        session.delete(student);

        tx.commit();
        session.close();

    }
}

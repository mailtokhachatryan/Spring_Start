package am.myOffice.shopJDBC.repository.product.impl;

import am.myOffice.shopJDBC.model.Product;
import am.myOffice.shopJDBC.repository.product.ProductRepository;
import am.myOffice.shopJDBC.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class ProductRepositoryJpaImpl implements ProductRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void create(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            session.save(product);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Product product, Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            session.update(product);
        transaction.commit();
        session.close();
    }

    @Override
    public Product get(Long id) {
        Session session = sessionFactory.openSession();
        Product product = session.get(Product.class, id);
        session.close();
        return product;
    }

    @Override
    public List<String> getColumns() {
        Session session = sessionFactory.openSession();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'products' ORDER BY ordinal_position");
        List resultList = nativeQuery.getResultList();
        List<String> list = null;
        for (Object o : resultList) {
            list.add(o.toString());
        }
        session.close();
        return list;
    }

    @Override
    public List<Product> getAll() {
        Session session = sessionFactory.openSession();
        Query namedQuery = session.createNativeQuery("SELECT * FROM products", Product.class);
        List resultList = namedQuery.getResultList();
        session.close();
        return resultList;
    }

    @Override
    public List<Product> findProductsByName(String name) {
        Session session = sessionFactory.openSession();
        NativeQuery<Product> nativeQuery = session.createNativeQuery("SELECT * FROM products WHERE name = :name", Product.class);
        List<Product> products = nativeQuery.setParameter("name", name).getResultList();
        session.close();
        return products;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            session.delete(id);
        transaction.commit();
        session.close();
    }
}

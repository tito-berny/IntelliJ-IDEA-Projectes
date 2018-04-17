package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


    public class DAOHelper<T> {
        private EntityManagerFactory emf;
        final Class<T> parameterClass;

        public DAOHelper(EntityManagerFactory emf, Class<T> parameterClass) {
            this.emf = emf;
            this.parameterClass = parameterClass;
        }

        public void insert(T t) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            try {
                em.persist(t);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }

        public void update(T t) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            try {
                em.merge(t);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }

        public void delete(Integer id) {
            EntityManager em = emf.createEntityManager();
            T t = em.find(parameterClass, id);

            try {
                if (t != null) {
                    em.getTransaction().begin();
                    em.remove(t);
                    em.getTransaction().commit();
                }
            } catch (Exception e) {
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }

        public T read(Integer id) {
            EntityManager em = emf.createEntityManager();
            try {
                return em.find(parameterClass, id);
            } finally {
                em.close();
            }
        }

        public List<T> getAll() {
            CriteriaBuilder cb = this.emf.getCriteriaBuilder();
            EntityManager manager = this.emf.createEntityManager();

            CriteriaQuery<T> cbQuery = cb.createQuery(parameterClass);
            Root<T> c = cbQuery.from(parameterClass);
            cbQuery.select(c);

            Query query = manager.createQuery(cbQuery);

            return query.getResultList();
        }

        public List<T> getFilteredList(String columna, Integer valor) {
            // RNB. Equivalent a SELECT * FROM prodcutes WHERE nom = :nom
            CriteriaBuilder cb = this.emf.getCriteriaBuilder();
            EntityManager manager = this.emf.createEntityManager();

            CriteriaQuery<T> cbQuery = cb.createQuery(parameterClass);
            Root<T> c = cbQuery.from(parameterClass);
            cbQuery.select(c);
            cbQuery.where(cb.equal(c.get(columna), valor));

            Query query = manager.createQuery(cbQuery);

            return   query.getResultList();
        }

        public List<T> getFilteredList(String columna, String valor) {
            // RNB. Equivalent a SELECT * FROM prodcutes WHERE nom = :nom
            CriteriaBuilder cb = this.emf.getCriteriaBuilder();
            EntityManager manager = this.emf.createEntityManager();

            CriteriaQuery<T> cbQuery = cb.createQuery(parameterClass);
            Root<T> c = cbQuery.from(parameterClass);
            cbQuery.select(c);
            cbQuery.where(cb.equal(c.get(columna), valor));

            Query query = manager.createQuery(cbQuery);

            return   query.getResultList();
        }

        public void printAll() {
            List<T> list = getAll();
            list.forEach(System.out::println);
        }
    }


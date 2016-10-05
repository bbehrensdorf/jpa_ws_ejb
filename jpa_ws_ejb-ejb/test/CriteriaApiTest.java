/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wbs.lotto.domain.Lottoschein;

/**
 *
 * @author Bernd
 */

public class CriteriaApiTest {
    
    protected static EntityManagerFactory emf;

    protected EntityManager em;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("jpa_ws_ejb-ejbPU");
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        emf.close();
    }
    
    
    

    public CriteriaApiTest() {
    }

    
    @Before
    public void setUp() {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        
          if (em.isOpen()) {
            em.close();
        }
    }

    
    @Test
    public void selectTest() {
        
        // CriteriaBuilder erstellen
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // CriteriaQuery erstellen - Klasse für der CriteriaBuilder wird angegeben
        CriteriaQuery<Lottoschein> query = cb.createQuery(Lottoschein.class);
        
        // Root Objekt erstellen und angeben, von welcher Entity wir selektieren wollen
        Root<Lottoschein> lottoscheinRoot =query.from(Lottoschein.class);
        
        // select Methode erfordert eine Selection Object von dem selectiert werden soll
        query.select(lottoscheinRoot).where(cb.equal(lottoscheinRoot.get("lottoscheinId"),5));
        
        TypedQuery <Lottoschein> qry = em.createQuery(query);
        
        List<Lottoschein> lottoscheine = qry.getResultList();
        
        for (Lottoschein lottoschein : lottoscheine) {
            System.out.println(lottoschein.getLosnummer());
            System.out.println(lottoschein.getKundeId().getName());
        }
        
        
        
        

    }
}

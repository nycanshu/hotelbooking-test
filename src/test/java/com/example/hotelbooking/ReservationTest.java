package com.example.hotelbooking;

import com.example.hotelbooking.entity.Reservation;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Reservation reservation;

    @BeforeAll
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hotelBookingPU");
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        reservation = new Reservation("John Doe", new Date(), new Date(System.currentTimeMillis() + 86400000), 2); // 1 day later
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @AfterAll
    public static void tearDownClass() {
        entityManagerFactory.close();
    }

    @Test
    public void testCreateReservation() {
        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();

        assertNotNull(reservation.getId());
    }

    @Test
    public void testReadReservation() {
        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();

        Reservation foundReservation = entityManager.find(Reservation.class, reservation.getId());
        assertEquals(reservation.getGuestName(), foundReservation.getGuestName());
    }

    @Test
    public void testUpdateReservation() {
        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        reservation.setGuestName("Jane Doe");
        entityManager.merge(reservation);
        entityManager.getTransaction().commit();

        Reservation updatedReservation = entityManager.find(Reservation.class, reservation.getId());
        assertEquals("Jane Doe", updatedReservation.getGuestName());
    }

    @Test
    public void testDeleteReservation() {
        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.remove(reservation);
        entityManager.getTransaction().commit();

        Reservation deletedReservation = entityManager.find(Reservation.class, reservation.getId());
        assertNull(deletedReservation);
    }
}
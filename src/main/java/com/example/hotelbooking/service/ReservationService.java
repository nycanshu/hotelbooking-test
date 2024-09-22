package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Reservation;
import com.example.hotelbooking.util.JPAUtil;

import javax.persistence.EntityManager;

public class ReservationService {

    public ReservationService(EntityManager em) {
    }

    public void createReservation(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback on error
            throw e; // Rethrow the exception or handle it
        } finally {
            em.close(); // Ensure the EntityManager is closed
        }
    }

    public Reservation findReservation(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Reservation.class, id);
        } finally {
            em.close();
        }
    }

    public void updateReservation(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reservation);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback on error
            throw e; // Rethrow the exception or handle it
        } finally {
            em.close();
        }
    }

    public void deleteReservation(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation != null) {
                em.remove(reservation);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback on error
            throw e; // Rethrow the exception or handle it
        } finally {
            em.close();
        }
    }
}

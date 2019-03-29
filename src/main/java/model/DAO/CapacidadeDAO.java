/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import controller.Connection.ConnectionFactory;
import java.util.List;
import model.Entity.Capacidade;

/**
 *
 * @author oem
 */
public class CapacidadeDAO extends DAO {

    //Lista tudo sobre um classe
    public List<Capacidade> findAll() {
        manager = new ConnectionFactory().getEntityManager();
        List<Capacidade> list = null;
        
        list = manager.createQuery("FROM Capacidade ca").getResultList();
        return list;
    }

    //Lista tudo sobre um classe
    public List<Capacidade> findAllCurso(long id) {
        manager = new ConnectionFactory().getEntityManager();
        List<Capacidade> list = null;
        list = manager.createQuery("FROM Capacidade ca WHERE ca.curso.id = :id").setParameter("id", id).getResultList();
        return list;
    }

    public Capacidade findOne(String desc) {
        manager = new ConnectionFactory().getEntityManager();
        Capacidade cap = null;
        cap = (Capacidade) manager.createQuery("FROM Capacidade ca WHERE ca.descricao = :descricao")
                .setParameter("descricao", desc).getSingleResult();
        return cap;
    }
    
}

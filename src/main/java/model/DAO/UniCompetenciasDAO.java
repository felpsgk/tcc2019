/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import controller.Connection.ConnectionFactory;
import java.util.List;
import model.Entity.UniCopetencias;

/**
 *
 * @author oem
 */
public class UniCompetenciasDAO extends DAO{
    public List<UniCopetencias> findAll() {
        manager = new ConnectionFactory().getEntityManager();
        List<UniCopetencias> list = null;

        list = manager.createQuery("FROM UniCopetencias u").getResultList();
        return list;
    }

    public List<UniCopetencias> findAllCurso(long id) {
        manager = new ConnectionFactory().getEntityManager();
        List<UniCopetencias> list = null;
        list = manager.createQuery("FROM UniCopetencias u WHERE u.curso.id = :id").setParameter("id", id).getResultList();
        return list;
    }
}

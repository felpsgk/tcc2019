/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import controller.Connection.ConnectionFactory;
import java.util.List;
import model.Entity.EleCompetencias;

/**
 *
 * @author oem
 */
public class EleCompetenciasDAO extends DAO{
    public List<EleCompetencias> findAll() {
        manager = new ConnectionFactory().getEntityManager();
        List<EleCompetencias> list = null;

        list = manager.createQuery("FROM EleCompetencias e").getResultList();
        return list;
    }

    public List<EleCompetencias> findAllUni(long id) {
        manager = new ConnectionFactory().getEntityManager();
        List<EleCompetencias> list = null;
        list = manager.createQuery("FROM EleCompetencias e WHERE e.uniCopetencias.id = :id")
                .setParameter("id", id).getResultList();
        return list;
    }
    
    public EleCompetencias findOne(String desc) {
        manager = new ConnectionFactory().getEntityManager();
        EleCompetencias uc = null;
        uc = (EleCompetencias) manager.createQuery("FROM EleCompetencias e WHERE e.descricao = :descricao")
                .setParameter("descricao", desc).getSingleResult();
        return uc;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import controller.Connection.ConnectionFactory;
import java.util.List;
import model.Entity.ConFinal;

/**
 *
 * @author oem
 */
public class ConFinalDAO extends DAO {

    //Lista tudo sobre um classe
    public List<ConFinal> findAll() {
        manager = new ConnectionFactory().getEntityManager();
        List<ConFinal> list = null;

        list = manager.createQuery("FROM ConFinal c "
                + "WHERE c.objConhecimento.id = c.objConhecimento.curso.id"
                + " AND c.uni.curso.id = c.curso.id"
                + " AND c.comp.uniCopetencias.curso.id = c.comp.uniCopetencias.id").getResultList();
        return list;
    }

}

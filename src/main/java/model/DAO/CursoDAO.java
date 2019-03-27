/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import controller.Connection.ConnectionFactory;
import java.util.List;
import model.Entity.Capacidade;
import model.Entity.Curso;

/**
 *
 * @author oem
 */
public class CursoDAO extends DAO {

    //Lista tudo sobre um classe
    public List<Curso> findAll() {
        manager = new ConnectionFactory().getEntityManager();
        List<Curso> list = null;

        list = manager.createQuery("FROM Curso c").getResultList();
        return list;
    }
    
    
}

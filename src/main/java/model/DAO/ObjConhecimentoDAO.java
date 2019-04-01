/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import controller.Connection.ConnectionFactory;
import java.util.List;
import model.Entity.ObjConhecimento;

/**
 *
 * @author oem
 */
public class ObjConhecimentoDAO extends DAO {

    public List<ObjConhecimento> findAll() {
        manager = new ConnectionFactory().getEntityManager();
        List<ObjConhecimento> list = null;

        list = manager.createQuery("FROM ObjConhecimento o").getResultList();
        return list;
    }
}

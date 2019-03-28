/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.InternalFrameController;

import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;

/**
 *
 * @author luizh
 */
public class InternalController {

    public JInternalFrame controllerInternal(JInternalFrame internal, Integer index) throws PropertyVetoException {

        internal.setClosable(true);
        internal.setSelected(true);
        internal.setMaximizable(true);
        internal.setVisible(true);

        switch (index) {
            case 1:
                internal.setTitle("Cadastro de cursos");
                internal.setSize(370, 370);
                break;
            case 2:
                internal.setTitle("Cadastro das capacidades");
                internal.setSize(370, 380);
                break;
            case 3:
                internal.setTitle("Cadastro dos objetos do conhecimento");
                internal.setSize(400, 280);
                break;
            case 4:
                internal.setTitle("Cadastro das unidades de competências");
                internal.setSize(370, 350);
                break;
            case 5:
                internal.setTitle("Cadastro dos elementos de competencias");
                internal.setSize(500, 350);
                break;
            case 6:
                internal.setTitle("Cadastro dos objetos de conhecimento");
                internal.setSize(560, 470);
                break;
        }

        return internal;
    }

}

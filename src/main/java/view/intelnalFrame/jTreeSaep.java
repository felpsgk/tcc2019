/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.intelnalFrame;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.DAO.CapacidadeDAO;
import model.DAO.ConFinalDAO;
import model.DAO.EleCompetenciasDAO;
import model.DAO.ObjConhecimentoDAO;
import model.DAO.UniCompetenciasDAO;
import model.Entity.Capacidade;
import model.Entity.ConFinal;
import model.Entity.Curso;
import model.Entity.EleCompetencias;
import model.Entity.ObjConhecimento;
import model.Entity.UniCopetencias;
import table.TabelaPrincipal;

/**
 *
 * @author oem
 */
public class jTreeSaep extends javax.swing.JInternalFrame {

    private TabelaPrincipal model = new TabelaPrincipal();

    /**
     * Creates new form jTreeSaep
     */
    //cria os modelos
    DefaultTreeModel dft;
    DefaultMutableTreeNode root;
    DefaultMutableTreeNode categoriaCap;
    DefaultMutableTreeNode UniCompete;
    DefaultMutableTreeNode EleCompete;
    DefaultMutableTreeNode basico;
    DefaultMutableTreeNode tecnico;
    DefaultMutableTreeNode gestao;
    DefaultMutableTreeNode capacidade;
    DefaultListModel modelo;
    UniCompetenciasDAO daoUni;
    EleCompetenciasDAO daoEle;
    CapacidadeDAO daoCap;
    Curso cs;
    UniCopetencias uc;
    EleCompetencias ec;
    Capacidade cap;

    public jTreeSaep(Curso c) {
        initComponents();
        cs = c;
        tblDados.setModel(model);
        model.readJTable(cs);

        //METODO QUE ADICIONA OS CURSOS E CAPACIDADES BASICA, TECNICA E GESTAO EM CADA UM
        addCompetencias(c);
        //POPULA OS COMBOBOX's
        popCboxs();
        //SETA O MODELO ATE AQUI CRIADO
        dft = new DefaultTreeModel(root);
        //DA PARA A JTREE O MODELO SETADO
        treeTeste.setModel(dft);

        //EVENTO DE CLIQUE NA TREE APENAS NO NÍVEL DESEJADO
        treeTeste.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                daoUni = new UniCompetenciasDAO();
                daoUni.findAllCurso(c.getId());

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeTeste.getLastSelectedPathComponent();

                if (selectedNode.getLevel() == 0) {
                    //ESCREVE NO LABEL A DESCRIÇÃO DO CURSO
                    lblDesc.setText(c.getDescricao());

                } else if (selectedNode.getLevel() == 1) {
                    //ESCREVE NO LABEL A DESCRIÇÃO DA UNIDADE DE COMPETENCIA SELECIONADA E SALVA
                    //O OBJETO GLOBALMENTE
                    uc = daoUni.findOne(treeTeste.getSelectionPath().getLastPathComponent().toString());
                    lblDesc.setText(uc.getDescricao());

                } else if (selectedNode.getLevel() == 2) {
                    //ESCREVE NO LABEL A DESCRIÇÃO DO ELEMENTO DE COMPETENCIA SELECIONADO E SALVA
                    //O OBJETO GLOBALMENTE
                    ec = daoEle.findOne(treeTeste.getSelectionPath().getLastPathComponent().toString());
                    lblDesc.setText(ec.getDescricao());

                } else if (selectedNode.getLevel() == 3) {
                    //ESCREVE NO LABEL A CATEGORIADE CAPACIDADE SELECIONADA
                    if (treeTeste.getSelectionPath().getLastPathComponent().toString() == "Básico") {
                        lblDesc.setText("Capacidades básicas de " + c.getNome());
                    } else if (treeTeste.getSelectionPath().getLastPathComponent().toString() == "Técnico") {
                        lblDesc.setText("Capacidades técnicas de " + c.getNome());
                    } else if (treeTeste.getSelectionPath().getLastPathComponent().toString() == "Gestão") {
                        lblDesc.setText("Capacidades de gestão de " + c.getNome());
                    }

                } else if (selectedNode.getLevel() == 4) {
                    //AÇÃO DAS CAPACIDADES
                    daoCap = new CapacidadeDAO();

                    cap = daoCap.findOne(treeTeste.getSelectionPath().getLastPathComponent().toString());

                    lblDesc.setText("" + cap);

                }

            }
        });

    }

    private void addCompetencias(Curso c) {
        //CRIA PASTA ROOT DO CURSO
        root = new DefaultMutableTreeNode(c.getNome());

        daoUni = new UniCompetenciasDAO();
        daoEle = new EleCompetenciasDAO();
        //CRIA PASTAS PARA CADA UNIDADE DE COMPETENCIA
        daoUni.findAllCurso(c.getId()).forEach((uc) -> {

            UniCompete = new DefaultMutableTreeNode(uc);

            root.add(UniCompete);
            //CRIA PASTAS PARA CADA ELEMENTO DE COMPETENCIA
            daoEle.findAllUni(uc.getId()).forEach((ec) -> {

                EleCompete = new DefaultMutableTreeNode(ec);

                UniCompete.add(EleCompete);

                basico = new DefaultMutableTreeNode("Básico");
                EleCompete.add(basico);
                tecnico = new DefaultMutableTreeNode("Técnico");
                EleCompete.add(tecnico);
                gestao = new DefaultMutableTreeNode("Gestão");
                EleCompete.add(gestao);

                addCapacidade(c.getId());

            });

        });
    }

    //ADICIONA AS CAPACIDADES EM SUAS CATEGORIAS
    private void addCapacidade(long cursoId) {

        CapacidadeDAO dao = new CapacidadeDAO();

        dao.findAllCurso(cursoId).forEach((ca) -> {

            switch (ca.getTipo()) {
                case 0:
                    categoriaCap = new DefaultMutableTreeNode(ca);
                    basico.add(categoriaCap);
                    break;
                case 1:
                    categoriaCap = new DefaultMutableTreeNode(ca);
                    tecnico.add(categoriaCap);
                    break;
                case 2:
                    categoriaCap = new DefaultMutableTreeNode(ca);
                    gestao.add(categoriaCap);
                    break;
                default:
                    break;
            }
        });
    }

    //PREENCHE OS COMBOBOX COM DADOS DO BANCO
    private void popCboxs() {
        EleCompetenciasDAO daoEle = new EleCompetenciasDAO();

        daoEle.findAll().forEach((ec) -> {
            cboxEleCompetencias.addItem(ec);
        });
        ObjConhecimentoDAO daoObj = new ObjConhecimentoDAO();

        daoObj.findAll().forEach((oc) -> {
            cboxObjConhecimento.addItem(oc);
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeTeste = new javax.swing.JTree();
        lblEleCompetencias = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        cboxEleCompetencias = new javax.swing.JComboBox<>();
        lblEleCompetencias1 = new javax.swing.JLabel();
        cboxObjConhecimento = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listObjCon = new javax.swing.JList<>();
        lblDesc = new javax.swing.JLabel();
        btnAddObjCon = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDados = new javax.swing.JTable();
        btnRemover = new javax.swing.JButton();

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeTeste.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeTeste);

        lblEleCompetencias.setText("Elemento de competência");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblEleCompetencias1.setText("Objeto de conhecimento");

        jScrollPane2.setViewportView(listObjCon);

        lblDesc.setText("Descrição");

        btnAddObjCon.setText("Adicionar");
        btnAddObjCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddObjConMouseClicked(evt);
            }
        });
        btnAddObjCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddObjConActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalvarMouseClicked(evt);
            }
        });

        tblDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblDados);

        btnRemover.setText("Remover");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDesc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboxObjConhecimento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboxEleCompetencias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEleCompetencias)
                                    .addComponent(lblEleCompetencias1))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddObjCon, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEleCompetencias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboxEleCompetencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEleCompetencias1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboxObjConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddObjCon)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar)
                                .addGap(12, 12, 12)
                                .addComponent(btnRemover)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDesc))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //METODO QUE ADICIONA OBJ DE CONHECIMENTO NA JLIST
    private void btnAddObjConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddObjConMouseClicked

        modelo = new DefaultListModel();

        ObjConhecimento objc = new ObjConhecimento();

        objc = (ObjConhecimento) cboxObjConhecimento.getSelectedItem();

        for (int i = 0; i < listObjCon.getModel().getSize(); i++) {
            modelo.addElement(listObjCon.getModel().getElementAt(i));
        }
        if (modelo.contains(objc)) {
            JOptionPane.showMessageDialog(null, "Já adicionado!");
        } else {

            modelo.addElement(objc);
            listObjCon.setModel(modelo);
        }
    }//GEN-LAST:event_btnAddObjConMouseClicked

//METODO QUE IRÁ INSERIR DADOS NA TABELA CONFINAL E ATUALIZAR A TABELA DO PROGRAMA
    private void btnSalvarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseClicked
        UniCopetencias unic = new UniCopetencias();
        EleCompetencias ec = new EleCompetencias();
        Capacidade capa = new Capacidade();
        ObjConhecimento oc = new ObjConhecimento();

        UniCompetenciasDAO udao = new UniCompetenciasDAO();
        EleCompetenciasDAO edao = new EleCompetenciasDAO();
        CapacidadeDAO cdao = new CapacidadeDAO();
        ObjConhecimentoDAO odao = new ObjConhecimentoDAO();
        ConFinalDAO cfdao = new ConFinalDAO();

        //pega unidade de competecia
        unic = uc;
        //pega elemento competencia
        ec = (EleCompetencias) cboxEleCompetencias.getSelectedItem();
        //pega capacidade
        capa = cap;
        //pega objeto de conhecimento
        for (int i = 0; i < listObjCon.getModel().getSize(); i++) {
            oc = (ObjConhecimento) listObjCon.getModel().getElementAt(i);
            ConFinal cf = new ConFinal();

            //salva tudo
            cf.setCurso(cs);
            cf.setUni(unic);
            cf.setComp(ec);
            cf.setCapacidade(capa);
            cf.setObjConhecimento(oc);
            cfdao.save(cf);
        }
        tblDados.setModel(model);
        model.readJTable(cs);
    }//GEN-LAST:event_btnSalvarMouseClicked

    private void btnAddObjConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObjConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddObjConActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddObjCon;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Object> cboxEleCompetencias;
    private javax.swing.JComboBox<Object> cboxObjConhecimento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblEleCompetencias;
    private javax.swing.JLabel lblEleCompetencias1;
    private javax.swing.JList<Object> listObjCon;
    private javax.swing.JTable tblDados;
    private javax.swing.JTree treeTeste;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.DAO.ConFinalDAO;
import model.Entity.Capacidade;
import model.Entity.ConFinal;
import model.Entity.Curso;
import model.Entity.EleCompetencias;
import model.Entity.ObjConhecimento;
import model.Entity.UniCopetencias;

/**
 *
 * @author oem
 */
public class TabelaPrincipal extends AbstractTableModel {

    List<ConFinal> dados = new ArrayList<>();
    private final String[] colunas = {"Unidade de Competencia", "Elementos de Competencia", "Capacidade", "Obj do conhecimento"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).getUni();
            case 1:
                return dados.get(linha).getComp();
            case 2:
                return dados.get(linha).getCapacidade();
            case 3:
                return dados.get(linha).getObjConhecimento();
            default:
                return this.dados.get(linha);
        }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {

        switch (coluna) {
            case 0:
                dados.get(linha).setUni((UniCopetencias) valor);
                break;
            case 1:
                dados.get(linha).setComp((EleCompetencias) valor);
                break;
            case 2:
                dados.get(linha).setCapacidade((Capacidade) valor);
                break;
            case 3:
                dados.get(linha).setObjConhecimento((ObjConhecimento) valor);
                break;

        }
        this.fireTableRowsUpdated(linha, linha);
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }

    public void addRow(ConFinal c) {
        this.dados.add(c);
        this.fireTableDataChanged();
    }

    public void removeRow(int linha) {
        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public void readJTable(Curso c) {
        ConFinalDAO dao = new ConFinalDAO();
        dados.clear();
        dao.findAll(c.getId()).forEach((con) -> {
            dados.add(con);
        });
    }

}

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author oem
 */
@Entity
public class ConFinal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Basic(optional = true)
    private String listObj;

    @ManyToOne
    private UniCopetencias uni = new UniCopetencias();

    @ManyToOne
    private EleCompetencias comp = new EleCompetencias();

    @ManyToOne
    private ObjConhecimento objConhecimento = new ObjConhecimento();

    @ManyToOne
    private Curso curso = new Curso();

    @ManyToOne
    private Capacidade capacidade = new Capacidade();

    public Capacidade getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Capacidade capacidade) {
        this.capacidade = capacidade;
    }
    
    public ObjConhecimento getObjConhecimento() {
        return objConhecimento;
    }

    public void setObjConhecimento(ObjConhecimento objConhecimento) {
        this.objConhecimento = objConhecimento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getListObj() {
        return listObj;
    }

    public void setListObj(String listObj) {
        this.listObj = listObj;
    }

    public UniCopetencias getUni() {
        return uni;
    }

    public void setUni(UniCopetencias uni) {
        this.uni = uni;
    }

    public EleCompetencias getComp() {
        return comp;
    }

    public void setComp(EleCompetencias comp) {
        this.comp = comp;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConFinal other = (ConFinal) obj;
        return this.id == other.id;
    }
}

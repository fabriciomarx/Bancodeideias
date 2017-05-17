package br.com.bancodeideias.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "analise_ideia")
public class AnaliseIdeia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAnalise")
    private Integer idAnalise;
    @Size(max = 250)
    @Column(name = "comentarios")
    private String comentarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataAnalise")
    @Temporal(TemporalType.DATE)
    private Date dataAnalise;
    @JoinColumn(name = "academicoAnalista", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario academicoAnalista;
    @JoinColumn(name = "ideia", referencedColumnName = "idIdeia")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ideia ideia;

    public AnaliseIdeia() {
    }

    public AnaliseIdeia(Integer idAnalise) {
        this.idAnalise = idAnalise;
    }

    public AnaliseIdeia(Integer idAnalise, Date dataAnalise) {
        this.idAnalise = idAnalise;
        this.dataAnalise = dataAnalise;
    }

    public Integer getIdAnalise() {
        return idAnalise;
    }

    public void setIdAnalise(Integer idAnalise) {
        this.idAnalise = idAnalise;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(Date dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public Usuario getAcademicoAnalista() {
        return academicoAnalista;
    }

    public void setAcademicoAnalista(Usuario academicoAnalista) {
        this.academicoAnalista = academicoAnalista;
    }

    public Ideia getIdeia() {
        return ideia;
    }

    public void setIdeia(Ideia ideia) {
        this.ideia = ideia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnalise != null ? idAnalise.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnaliseIdeia)) {
            return false;
        }
        AnaliseIdeia other = (AnaliseIdeia) object;
        if ((this.idAnalise == null && other.idAnalise != null) || (this.idAnalise != null && !this.idAnalise.equals(other.idAnalise))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.AnaliseIdeia[ idAnalise=" + idAnalise + " ]";
    }

}

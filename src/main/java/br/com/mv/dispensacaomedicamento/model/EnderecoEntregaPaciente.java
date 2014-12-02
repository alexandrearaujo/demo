/**
 * 
 */
package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author joao.franco
 *
 */


@Entity
@Table(name="ENDERECO_ENTREGA_PACIENTE")
public class EnderecoEntregaPaciente implements Serializable
{
    
    private static final long serialVersionUID = 6167163192530750604L;

    @Id
    @SequenceGenerator(name="SEQ_ENDERECO_ENTREGA_PACIENTE", sequenceName = "SEQ_ENDERECO_ENTREGA_PACIENTE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_ENDERECO_ENTREGA_PACIENTE" )
    @Column(name="CD_ENDERECO_ENTREGA_PACIENTE",length = 8, nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "CD_UF")
    private Uf uf;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_MUNICIPIO", referencedColumnName = "ID_MUNIC")
    private Municipio municipio;
    
    @ManyToOne
    @JoinColumn(name = "CD_CIDADAO")
    private Cidadao paciente;

    @ManyToOne
    @JoinColumn(name = "CD_TIPO_LOGRADOURO")
    private TipoLogradouro tipoLogradouro;
    
    @Column (name="DS_BAIRRO")
    private String bairro;
    
    @Column(name = "DS_LOGRADOURO")
    private String logradouro;
    
    @Column(name = "DS_COMPLEMENTO")
    private String complementoEndereco;
    
    @Column (name = "NM_RESPONSAVEL")
    private String nomeResponsavel;
    
    @Column (name  ="NR_CEP", length='8')
    private String cep;
    
    @Column(name ="NR_NUMERO")
    private Long numeroResidencia;
    
    @Column(name ="NR_TELEFONE_RESPONSAVEL")
    private String telefoneResponsavel;
    
    @Column (name ="SN_UTILIZA_ENDERECO_PACIENTE")
    private Long utilizaEnderecoPaciente;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Uf getUf()
    {
        return uf;
    }

    public void setUf(Uf uf)
    {
        this.uf = uf;
    }

    public Municipio getMunicipio()
    {
        return municipio;
    }

    public void setMunicipio(Municipio municipio)
    {
        this.municipio = municipio;
    }

    public Cidadao getPaciente()
    {
        return paciente;
    }

    public void setPaciente(Cidadao paciente)
    {
        this.paciente = paciente;
    }

    public TipoLogradouro getTipoLogradouro()
    {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(TipoLogradouro tipoLogradouro)
    {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getBairro()
    {
        return bairro;
    }

    public void setBairro(String bairro)
    {
        this.bairro = bairro;
    }

    public String getLogradouro()
    {
        return logradouro;
    }

    public void setLogradouro(String logradouro)
    {
        this.logradouro = logradouro;
    }

    public String getComplementoEndereco()
    {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco)
    {
        this.complementoEndereco = complementoEndereco;
    }

    public String getNomeResponsavel()
    {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel)
    {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getCep()
    {
        return cep;
    }

    public void setCep(String cep)
    {
        this.cep = cep;
    }

    public Long getNumeroResidencia()
    {
        return numeroResidencia;
    }

    public void setNumeroResidencia(Long numeroResidencia)
    {
        this.numeroResidencia = numeroResidencia;
    }

    public String getTelefoneResponsavel()
    {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel)
    {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public Long getUtilizaEnderecoPaciente()
    {
        return utilizaEnderecoPaciente;
    }

    public void setUtilizaEnderecoPaciente(Long utilizaEnderecoPaciente)
    {
        this.utilizaEnderecoPaciente = utilizaEnderecoPaciente;
    }


}

/**
 * 
 */
package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

/**
 * @author joao.franco
 *
 */
public class EnderecoEntregaPacienteDTO implements Serializable
{
    
    
    private static final long serialVersionUID = 6881242230060557578L;
    
    private Long id;
    private String nomePaciente;
    private Long cdUf;
    private String uf;
    private Long cdMunicipio;
    private String nomeMunicipio;
    private Long cdCidadao;
    private Long cdLogradouro;
    private String nomeLogradouro;
    private String nomeBairro;
    private String complemento;
    private String nomeResponsavel;
    private String cep;
    private Long numeroResidencia;
    private String telefoneResponsavel;
    private Long utilizaEndereco;
   
    
    
    
public Long getId()
{
    return id;
}
public void setId(Long id)
{
    this.id = id;
}
public String getNomePaciente()
{
    return nomePaciente;
}
public void setNomePaciente(String nomePaciente)
{
    this.nomePaciente = nomePaciente;
}

public Long getCdUf()
{
    return cdUf;
}
public void setCdUf(Long cdUf)
{
    this.cdUf = cdUf;
}
public String getUf()
{
    return uf;
}
public void setUf(String uf)
{
    this.uf = uf;
}
public Long getCdMunicipio()
{
    return cdMunicipio;
}
public void setCdMunicipio(Long cdMunicipio)
{
    this.cdMunicipio = cdMunicipio;
}
public String getNomeMunicipio()
{
    return nomeMunicipio;
}
public void setNomeMunicipio(String nomeMunicipio)
{
    this.nomeMunicipio = nomeMunicipio;
}
public Long getCdCidadao()
{
    return cdCidadao;
}
public void setCdCidadao(Long cdCidadao)
{
    this.cdCidadao = cdCidadao;
}
public Long getCdLogradouro()
{
    return cdLogradouro;
}
public void setCdLogradouro(Long cdLogradouro)
{
    this.cdLogradouro = cdLogradouro;
}

public String getNomeLogradouro()
{
    return nomeLogradouro;
}
public void setNomeLogradouro(String nomeLogradouro)
{
    this.nomeLogradouro = nomeLogradouro;
}
public String getNomeBairro()
{
    return nomeBairro;
}
public void setNomeBairro(String nomeBairro)
{
    this.nomeBairro = nomeBairro;
}
public String getComplemento()
{
    return complemento;
}
public void setComplemento(String complemento)
{
    this.complemento = complemento;
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
public Long getUtilizaEndereco()
{
    return utilizaEndereco;
}
public void setUtilizaEndereco(Long utilizaEndereco)
{
    this.utilizaEndereco = utilizaEndereco;
}




 
 
 
 
 
 
 
}

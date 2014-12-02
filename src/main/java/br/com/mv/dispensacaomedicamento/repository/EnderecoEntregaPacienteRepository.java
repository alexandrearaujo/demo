/**
 * 
 */
package br.com.mv.dispensacaomedicamento.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.Cidadao;
import br.com.mv.dispensacaomedicamento.model.EnderecoEntregaPaciente;
import br.com.mv.dispensacaomedicamento.model.EnderecoEntregaPacienteDTO;

@Repository
public interface EnderecoEntregaPacienteRepository extends CrudRepository<EnderecoEntregaPaciente, Long>
{
	
	@Query(value=
			   "select "+
		       "	eep.cd_endereco_entrega_paciente, "+
		       "	eep.cd_cidadao, " +
		       "	ci.nm_cidadao," +
		       "	eep.cd_uf," +
		       "	u.nm_uf," +
		       "	eep.cd_municipio, " +
		       "	m.descr_munic, " +
		       "	eep.cd_tipo_logradouro, "+
		       "	eep.ds_logradouro, "+
		       "	eep.ds_bairro, "+
		       "	eep.ds_complemento, "+
		       "	eep.nm_responsavel, "+
		       "	eep.nr_cep, "+
		       "	eep.nr_numero, "+
		       "	eep.nr_telefone_responsavel, "+
		       "	eep.sn_utiliza_endereco_paciente "+
		     "from "+
		       "	dbamvfor.endereco_entrega_paciente eep, "+
		       "	dbamvfor.tb_cidadao ci,  "+
		       "	dbamvfor.uf u, "+
		       "	dbamvfor.municipio m "+
		     "where ci.cd_cidadao = :idCidadao "+
		       "	and eep.cd_cidadao = ci.cd_cidadao "+
		       "	and u.cd_uf = eep.cd_uf "+
		       "	and m.id_munic = eep.cd_municipio", nativeQuery=true)
	public EnderecoEntregaPaciente buscarEnderecoEntrega(@Param("idCidadao") Long idCidadao);

	@Query(value=
			 "select " + 
		     "		ci.nr_cep," +
		     "		u.nm_uf," +
		     "		ci.cd_uf," +
		     "		ci.cd_cidadao," +
		     "		ci.cd_municipio_resid," +
		     "		m.descr_munic, " +
		     "		ci.ds_bairro, " +
		     "		ci.cd_tipo_logradouro, " + 
		     "		ci.ds_logradouro, " +
		     "		ci.nr_numero, " +
		     "		ci.nm_complemento_endereco " +
		     "from " + 
		     " 		dbamvfor.tb_cidadao ci " +
		     "		inner join dbamvfor.uf u on u.cd_uf = ci.cd_uf " +
		     "		inner join dbamvfor.municipio m on m.id_munic = ci.cd_municipio_resid " +
		     "where ci.cd_cidadao =  :id ", nativeQuery=true)    
    public EnderecoEntregaPacienteDTO preencherEnderecoByCidadao(Cidadao cidadao);

}

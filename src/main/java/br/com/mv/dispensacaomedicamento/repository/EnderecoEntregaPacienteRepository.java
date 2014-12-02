/**
 * 
 */
package br.com.mv.dispensacaomedicamento.repository;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.Cidadao;
import br.com.mv.dispensacaomedicamento.model.EnderecoEntregaPaciente;
import br.com.mv.dispensacaomedicamento.model.EnderecoEntregaPacienteDTO;

@Repository
@Configuration
public interface EnderecoEntregaPacienteRepository extends CrudRepository<EnderecoEntregaPaciente, Long>
{
	
	default public EnderecoEntregaPacienteDTO buscarEnderecoEntrega(Cidadao cidadao)
	{

		StringBuilder sql = new StringBuilder();		
		
		 sql.append("select ");
	     sql.append("  eep.cd_endereco_entrega_paciente AS \"id\",");
	     sql.append("  eep.cd_cidadao AS \"cdCidadao\",");
	     sql.append("  ci.nm_cidadao AS \"nomePaciente\",");
	     sql.append("  eep.cd_uf AS \"cdUf\",");
	     sql.append("  u.nm_uf AS \"uf\",");
	     sql.append("  eep.cd_municipio AS \"cdMunicipio\",");
	     sql.append("  m.descr_munic AS \"nomeMunicipio\",");
	     sql.append("  eep.cd_tipo_logradouro AS \"cdLogradouro\",");
	     sql.append("  eep.ds_logradouro AS \"nomeLogradouro\",");
	     sql.append("  eep.ds_bairro AS \"nomeBairro\",");
	     sql.append("  eep.ds_complemento AS \"complemento\",");
	     sql.append("  eep.nm_responsavel AS \"nomeResponsavel\",");
	     sql.append("  eep.nr_cep AS \"cep\",");
	     sql.append("  eep.nr_numero AS \"numeroResidencia\",");
	     sql.append("  eep.nr_telefone_responsavel AS \"telefoneResponsavel\",");
	     sql.append("  eep.sn_utiliza_endereco_paciente AS \"utilizaEndereco\" ");
	     sql.append("from ");
	     sql.append("  dbamvfor.endereco_entrega_paciente eep, ");
	     sql.append("  dbamvfor.tb_cidadao ci, "); 
	     sql.append("  dbamvfor.uf u, ");
	     sql.append("  dbamvfor.municipio m ");
	     sql.append("where ci.cd_cidadao = :id ");
	     sql.append("  and eep.cd_cidadao = ci.cd_cidadao ");
	     sql.append("  and u.cd_uf = eep.cd_uf ");
	     sql.append("  and m.id_munic = eep.cd_municipio ");
	     
	     SQLQuery query = getSession().createSQLQuery(sql.toString());
	     query.setResultTransformer(new AliasToBeanResultTransformer(EnderecoEntregaPacienteDTO.class));
	     query.setParameter("id", cidadao.getId());
	     return  (EnderecoEntregaPacienteDTO) query.uniqueResult();	     
	};
	    
	public EnderecoEntregaPacienteDTO findByPaciente(Cidadao cidadao);

}

package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import br.com.mv.dispensacaomedicamento.model.ViaAdministracaoMedicamento;

public interface ViaAdministracaoMedicamentoRepository extends CrudRepository<ViaAdministracaoMedicamento,Long>
{

//    public Collection<ViaAdministracaoMedicamento> listarViaAdministracaoMedicamentoAtivo()
//    {
//        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
//        
//        arrayList.add(new ProjectionFilter("id"));
//        arrayList.add(new ProjectionFilter("descricao"));
//        arrayList.add(new ProjectionFilter("ativo", true));
//        
//        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
//        arrayList.toArray(projectionFilters);
//        
//        return get(0, projectionFilters);
//        
//    }
	public Collection<ViaAdministracaoMedicamento> listarViaAdministracaoMedicamentoAtivo();
}

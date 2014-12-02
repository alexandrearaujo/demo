package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.ViaAdministracaoMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.ViaAdministracaoMedicamento;

@Named("viaAdministracaoMedicamentoManager")
public class ViaAdministracaoMedicamentoBusiness extends GenericManagerImpl<ViaAdministracaoMedicamento, ViaAdministracaoMedicamentoRepository> implements ViaAdministracaoMedicamentoManager
{

    @Inject
    @Override
    public void setDao(ViaAdministracaoMedicamentoRepository dao)
    {
        super.setDao(dao);
    }
    
    @Override
    public Collection<ViaAdministracaoMedicamento> listarViaAdministracaoMedicamentoAtivo()
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricao"));
        arrayList.add(new ProjectionFilter("ativo", true));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(0, projectionFilters);
        
    }
    
}

package br.com.mv.dispensacaomedicamento.business;


import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import flex.messaging.io.ArrayCollection;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.MedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.dao.SituacaoSolicitacaoMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
@Named("situacaoSolicitacaoMedicamentoManager")
public class SituacaoSolicitacaoMedicamentoBusiness extends GenericManagerImpl<SituacaoSolicitacaoMedicamento, SituacaoSolicitacaoMedicamentoRepository> implements SituacaoSolicitacaoMedicamentoManager
{

    @Inject
    @Override
    public void setDao(SituacaoSolicitacaoMedicamentoRepository dao)
    {
        super.setDao(dao);
    }
    
    
    
    @Override
    public SituacaoSolicitacaoMedicamento getPorDescricao(String descricao)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricaoSituacaoMedicamento",descricao ));
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return getUnique(projectionFilters);
    }

}

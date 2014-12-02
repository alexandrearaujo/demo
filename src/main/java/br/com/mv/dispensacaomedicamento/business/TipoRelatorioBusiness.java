package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.criterion.Order;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.TipoRelatorioDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.TipoRelatorio;


@Named("tipoRelatorioManager")
public class TipoRelatorioBusiness extends GenericManagerImpl<TipoRelatorio, TipoRelatorioRepository> implements TipoRelatorioManager
{

    
    @Inject
    @Override
    public void setDao(TipoRelatorioRepository dao)
    {
        super.setDao(dao);
    }

    @Override
    public Collection<TipoRelatorio> listarTipoRelatorio(String nomeTipoRelatorio)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("nomeTipoRelatorio", nomeTipoRelatorio));
        arrayList.add(new ProjectionFilter("descricaoTipoRelatorio"));
        arrayList.add(new ProjectionFilter(Order.asc("nomeTipoRelatorio")));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        return get(0, arrayList.toArray(projectionFilters));
    }

    
    
}

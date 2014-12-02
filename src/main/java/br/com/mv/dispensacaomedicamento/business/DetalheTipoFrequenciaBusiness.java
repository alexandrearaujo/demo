/**
 * 
 */
package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.sql.JoinFragment;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.DetalheTipoFrequenciaDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.DetalheTipoFrequencia;
import br.com.mv.regulacao.dispensacaomedicamento.model.TipoFrequencia;

/**
 * @author joao.franco
 *
 */

@Named("detalheTipoFrequenciaManager")
public class DetalheTipoFrequenciaBusiness extends GenericManagerImpl<DetalheTipoFrequencia, DetalheTipoFrequenciaRepository> implements DetalheTipoFrequenciaManager
{
    @Inject
    @Override
    public void setDao(DetalheTipoFrequenciaRepository dao)
    {
        super.setDao(dao);
    }
    
    public Collection<DetalheTipoFrequencia> listaHorariosMedicamentos (String descricaoMedicamento)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricaoMedicamento", descricaoMedicamento ));
        arrayList.add(new ProjectionFilter("tipoFrequencia"));
        arrayList.add(new ProjectionFilter("horaMedicacao"));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        return get(arrayList.toArray(projectionFilters));
    }

    @Override
    public void salvar(DetalheTipoFrequencia detalheTipoFrequencia)  throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        save(detalheTipoFrequencia);
    }

    @Override
    public Collection<DetalheTipoFrequencia> buscarDetalheTipoFrequencia(TipoFrequencia tipoFrequencia) throws Exception
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricaoMedicamento"));
        arrayList.add(new ProjectionFilter("horaMedicacao"));
        arrayList.add(new ProjectionFilter("tipoFrequencia.id", tipoFrequencia.getId()));
        arrayList.add(new ProjectionFilter("tipoFrequencia.id", JoinFragment.INNER_JOIN));
        arrayList.add(new ProjectionFilter("tipoFrequencia.descricaoFrequencia"));
        arrayList.add(new ProjectionFilter("tipoFrequencia.impressaoReceita"));
        arrayList.add(new ProjectionFilter("tipoFrequencia.periodicidade"));
        arrayList.add(new ProjectionFilter("tipoFrequencia.horarioInicial"));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter [arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(projectionFilters);
    }
    
}

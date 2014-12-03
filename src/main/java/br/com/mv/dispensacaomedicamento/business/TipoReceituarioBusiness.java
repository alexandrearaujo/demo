package br.com.mv.dispensacaomedicamento.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.dispensacaomedicamento.model.TipoReceituario;
import br.com.mv.dispensacaomedicamento.repository.TipoReceituarioRepository;

@Service
@Transactional
public class TipoReceituarioBusiness
{

	@Autowired
    private TipoReceituarioRepository tipoReceituarioRepository;
	
    public Collection<TipoReceituario> listarPaginado(int initialPos, int maxResults, TipoReceituario tipoReceituario)
    {
//        return fetch(initialPos, maxResults, criarProjection(tipoReceituario));
    	
//    	private ProjectionFilter[] criarProjection(TipoReceituario tipoReceituario)
//        {
//
//            ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
//            
//            if(tipoReceituario.getId() != null)
//                listaProjection.add(new ProjectionFilter("id", tipoReceituario.getId()));
//            
//            listaProjection.add(new ProjectionFilter("id"));
//            if(tipoReceituario.getDescricao() != null && !tipoReceituario.getDescricao().isEmpty())
//                listaProjection.add(new ProjectionFilter("descricao", tipoReceituario.getDescricao()));
//            else
//                listaProjection.add(new ProjectionFilter("descricao"));
//            
//            if(tipoReceituario.getNumeroDiasValidadeReceita() != null && tipoReceituario.getNumeroDiasValidadeReceita() != 0L)
//                listaProjection.add(new ProjectionFilter("numeroDiasValidadeReceita", tipoReceituario.getNumeroDiasValidadeReceita()));
//            else
//                listaProjection.add(new ProjectionFilter("numeroDiasValidadeReceita"));
//            
//            if(tipoReceituario.getQuantidadeViaReceita() != null)
//                listaProjection.add(new ProjectionFilter("quantidadeViaReceita", tipoReceituario.getQuantidadeViaReceita()));
//            else
//                listaProjection.add(new ProjectionFilter("quantidadeViaReceita"));
//
//            if(tipoReceituario.getCor() != null && tipoReceituario.getCor() != -1)
//                listaProjection.add(new ProjectionFilter("cor", tipoReceituario.getCor()));
//            else
//                listaProjection.add(new ProjectionFilter("cor"));
//            
//            listaProjection.add(new ProjectionFilter(Order.asc("descricao")));
//            
//            ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];
//
//            listaProjection.toArray(projectionFilters);
//            
//            return projectionFilters;
//        }
    	
    	return null;
    }
    
    public TipoReceituario salvar(TipoReceituario tipoReceituario)
    {
        return tipoReceituarioRepository.save(tipoReceituario);
    }

    public void excluir(TipoReceituario tipoReceituario)
    {
    	tipoReceituarioRepository.delete(tipoReceituario);
    }
    
}

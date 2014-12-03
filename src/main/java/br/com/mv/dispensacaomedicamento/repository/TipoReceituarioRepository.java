package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import br.com.mv.dispensacaomedicamento.model.TipoReceituario;

public interface TipoReceituarioRepository extends CrudRepository<TipoReceituario,Long>
{
//	    public Collection<TipoReceituario> list(TipoReceituario tipoReceituario)
//	    {
//	    	return tipoReceituarioRepository.findAll(tipoReceituario);
//	        if(tipoReceituario == null){
//	            tipoReceituario = new TipoReceituario();
//	        }
//	        return get(0, criarProjection(tipoReceituario));
//	    }
	    public Collection<TipoReceituario> list(TipoReceituario tipoReceituario);
	    
//	    public TipoReceituario getTipoReceituario(TipoReceituario tipoReceituario)
//	    {
//	        return getUnique(criarProjection(tipoReceituario));
//	    }
	    public TipoReceituario getTipoReceituario(TipoReceituario tipoReceituario);
}

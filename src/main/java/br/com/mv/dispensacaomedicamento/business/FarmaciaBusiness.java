/**
 * 
 */
package br.com.mv.dispensacaomedicamento.business;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.regulacao.dispensacaomedicamento.dao.FarmaciaDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.Farmacia;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimento;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimentoDTO;
import br.com.mv.regulacao.model.geral.Estabelecimento;
import br.com.mv.sigas.manager.geral.VinculoProfissionalSigasManager;
import br.com.mv.sigas.model.geral.VinculoProfissionalSigas;

/**
 * @author joao.franco
 *
 */
@Named("farmaciaManager")
public class FarmaciaBusiness extends GenericManagerImpl<Farmacia, FarmaciaDao> implements FarmaciaManager
{
    
    private final String[] OCUPACAO_DESCRICAO =
            {
                    "%farmace%", "%farmacê%"
            };



    @Inject
    @Override
    public void setDao(FarmaciaDao dao)
    {
        super.setDao(dao);
    }

    @Inject
    VinculoProfissionalSigasManager vinculoProfissionalSigasManager;

    @Inject
    FarmaciaEstabelecimentoManager farmaciaEstabelecimentoManager;
    


    @Override
    public Long count(Farmacia farmacia)
    {
        return null;
    }

    @Override
    public Collection<Farmacia> list(int initialPos, int maxResults, Farmacia farmacia)
    {
        return null;
    }

    @Override
    public Farmacia salvar(Farmacia farmacia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        farmacia.setDataCadastro(new Date());
        return getDao().save(farmacia, true);
    }

    @Override
    public void atualizar(Farmacia farmacia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
         update(farmacia, true);
    }


    @Override
    public Farmacia salvar(Farmacia farmacia, ArrayList<Estabelecimento> listaFarmaciaEstabelecimento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, IntrospectionException
    {
        farmacia.setDataCadastro(new Date());
        save(farmacia, true);
        FarmaciaEstabelecimento farmaciaEstabelecimento;
        try
        {
            for (Estabelecimento estabelecimento : listaFarmaciaEstabelecimento)
            {
                farmaciaEstabelecimento = new FarmaciaEstabelecimento();
                farmaciaEstabelecimento.setFarmacia(farmacia);
                farmaciaEstabelecimento.setUser(farmacia.getUsuario());
                farmaciaEstabelecimento.setEstabelecimento(estabelecimento);
                farmaciaEstabelecimentoManager.salvar(farmaciaEstabelecimento);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return farmacia;
    }

    @Override
    public void excluir(FarmaciaEstabelecimentoDTO farmaciaEstabelecimentoDTO) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        delete(farmaciaEstabelecimentoDTO, true);
    }

    @Override
    public void excluirFarmacia(Farmacia farmacia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {

    }
    
    @Override                                 
    public Collection<VinculoProfissionalSigas> listarFarmaceutico(String nomeProfissional, Estabelecimento estabelecimento)
    {
        return vinculoProfissionalSigasManager.listProfissionalPorEstabelecimentoOcupacaoPorDescricao(nomeProfissional, estabelecimento, OCUPACAO_DESCRICAO);
    }


}

package br.com.mv.dispensacaomedicamento.business;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.regulacao.dispensacaomedicamento.dao.ItemSolicitacaoMedicamentoApacDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamentoApac;


@Named("itemSolicitacaoMedicamentoApacManager")
public class ItemSolicitacaoMedicamentoApacBusiness extends GenericManagerImpl<ItemSolicitacaoMedicamentoApac, ItemSolicitacaoMedicamentoApacDao> implements ItemSolicitacaoMedicamentoApacManager
{

    @Inject
    @Override
    public void setDao(ItemSolicitacaoMedicamentoApacDao dao)
    {
        super.setDao(dao);
    }    

    @Override
    public ItemSolicitacaoMedicamentoApac salvar(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        return save(itemSolicitacaoMedicamentoApac, true);
    }

    @Override
    public void atualizar(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        update(itemSolicitacaoMedicamentoApac, true);
    }

    @Override
    public void excluir(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        delete(itemSolicitacaoMedicamentoApac, true);
    }
    
}

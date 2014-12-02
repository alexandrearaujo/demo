package br.com.mv.dispensacaomedicamento.repository;

import java.math.BigDecimal;

import org.hibernate.SQLQuery;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.model.MarcaMedicamento;

/**
 * @author Francisco Vernek
 *
 */

public interface MarcaMedicamentoRepository extends GenericDao<MarcaMedicamento>
{

    @Override
    public boolean existeMarcaMedicamentoCadastrada(MarcaMedicamento marcaMedicamento) throws DispensacaoMedicamentoException,
            DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ");
        sql.append("count(mm.cd_marca_medicamento) ");
        sql.append("from marca_medicamento mm ");
        sql.append("where mm.ds_marca_medicamento = :descricao ");

        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setParameter("descricao", marcaMedicamento.getDescricao());
        
        BigDecimal count = (BigDecimal) query.uniqueResult();
        
        if(count.intValue() == 0 )
            return false;
        else
            return true;
    }

}

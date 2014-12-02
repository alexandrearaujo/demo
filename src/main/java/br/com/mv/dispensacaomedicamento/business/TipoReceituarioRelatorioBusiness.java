package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.JoinFragment;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.util.SpringBeans;
import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.TipoReceituarioRelatorioDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.TipoReceituarioException;
import br.com.mv.regulacao.dispensacaomedicamento.exception.TipoReceituarioRelatorioException;
import br.com.mv.regulacao.dispensacaomedicamento.exception.TipoRelatorioException;
import br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituario;
import br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituarioRelatorio;


@Named("TipoReceituarioRelatorioManager")
public class TipoReceituarioRelatorioBusiness extends GenericManagerImpl<TipoReceituarioRelatorio, TipoReceituarioRelatorioDao> implements TipoReceituarioRelatorioManager
{
    final String CNT_TIPO_RECEITUARIO_PK = "DBAMVFOR.CNT_TIPO_RECEITUARIO_PK";
    final String CNT_TIPO_RECEITUARIO_1_CK = "DBAMVFOR.CNT_TIPO_RECEITUARIO_1_CK";
    final String CNT_TIPO_RECEITUARIO_1_UK = "DBAMVFOR.CNT_TIPO_RECEITUARIO_1_UK";
    final String CNT_TIPO_RELATORIO_PK = "DBAMVFOR.CNT_TIPO_RELATORIO_PK";
    final String CNT_TIPO_RELATORIO_1_UK = "DBAMVFOR.CNT_TIPO_RELATORIO_1_UK";
    final String CNT_TIPO_RECEITUARIO_REL_1_UK = "DBAMVFOR.CNT_TIPO_RECEITUARIO_REL_1_UK";
    
    @Inject
    @Override
    public void setDao(TipoReceituarioRelatorioDao dao)
    {
        super.setDao(dao);
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.TipoReceituarioRelatorioManager#buscarTipoReceituarioPorTipoRelatorio(br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituario)
     */
    @Override
    public Collection<TipoReceituarioRelatorio> buscarTipoReceituarioPorTipoRelatorio(TipoReceituario tipoReceituario)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("tipoReceituario.id", tipoReceituario.getId()));
        arrayList.add(new ProjectionFilter("tipoReceituario.id", JoinFragment.INNER_JOIN));
        arrayList.add(new ProjectionFilter("tipoReceituario.descricaTipoReceituario"));
        arrayList.add(new ProjectionFilter("tipoReceituario.tipoCorReceituario"));
        arrayList.add(new ProjectionFilter("tipoReceituario.numeroDiasValidadeReceita"));
        arrayList.add(new ProjectionFilter("tipoReceituario.quantidadeViaReceita"));
        arrayList.add(new ProjectionFilter("tipoRelatorio.id", JoinFragment.INNER_JOIN));
        arrayList.add(new ProjectionFilter("tipoRelatorio.nomeTipoRelatorio"));
        arrayList.add(new ProjectionFilter("tipoRelatorio.descricaoTipoRelatorio"));
        arrayList.add(new ProjectionFilter("ativo"));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter [arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(projectionFilters);
    }

    @Override
    public void salvarTipoReceituarioRelatorio(Collection<TipoReceituarioRelatorio> listTipoReceituarioRelatorios) throws NotUniqueIdException,
            InstantiationException, IllegalAccessException, ClassNotFoundException, TipoReceituarioException
    {
        
        
        TipoReceituarioManager tipoReceituarioManager = SpringBeans.getManager(TipoReceituarioManager.class);
        TipoReceituario tipoReceituario = null;
        boolean tipoRelatorioSalvo = false;
        
        if(contemRelatorioAtivo(listTipoReceituarioRelatorios))
        {
            try
            {
                for (TipoReceituarioRelatorio tipoReceituarioRelatorio : listTipoReceituarioRelatorios)
                {
                    if(!tipoRelatorioSalvo)
                    {
                        tipoReceituario = tipoReceituarioManager.salvar(tipoReceituarioRelatorio.getTipoReceituario());
                        tipoReceituarioRelatorio.setTipoReceituario(tipoReceituario);
                        salvar(tipoReceituarioRelatorio);
                        tipoRelatorioSalvo =true;
                    }
                    else
                    {   
                        tipoReceituarioRelatorio.setTipoReceituario(tipoReceituario);
                        salvar(tipoReceituarioRelatorio);
                    }
                }
                
            }
            catch (DataIntegrityViolationException e)
            {
                if(e.getCause() instanceof ConstraintViolationException)
                {
                    String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
                    
                    if (constraint.equals(CNT_TIPO_RECEITUARIO_PK))
                        throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_PK);
                    if (constraint.equals(CNT_TIPO_RECEITUARIO_1_CK))
                        throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_TP_COR_RECEITUARIO);
                    if (constraint.equals(CNT_TIPO_RECEITUARIO_1_UK))
                        throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_UNIQUE_CONSTRAINT);
                    
                    if (constraint.equals(CNT_TIPO_RELATORIO_PK))
                        throw new TipoReceituarioException(TipoRelatorioException.CNT_TIPO_RELATORIO_PK);
                    if (constraint.equals(CNT_TIPO_RELATORIO_1_UK))
                        throw new TipoReceituarioException(TipoRelatorioException.CNT_TIPO_RELATORIO_1_UK);
                    
                    if(constraint.equals(CNT_TIPO_RECEITUARIO_REL_1_UK))
                        throw new TipoReceituarioException(TipoReceituarioRelatorioException.CNT_TIPO_RECEITUARIO_REL_1_UK);
                }
            }
        }
        else
            throw new TipoReceituarioException(TipoReceituarioRelatorioException.TIPO_RECEITUARIO_MSG_RELATORIO_ATIVO);
        
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.TipoReceituarioRelatorioManager#atualizarTipoReceituarioRelatorio(java.util.Collection)
     */
    @Override
    public void atualizarTipoReceituarioRelatorio(Collection<TipoReceituarioRelatorio> listTipoReceituarioRelatorios) throws NotUniqueIdException,
            InstantiationException, IllegalAccessException, ClassNotFoundException, TipoReceituarioException
    {
        TipoReceituarioManager tipoReceituarioManager = SpringBeans.getManager(TipoReceituarioManager.class);
        TipoReceituario tipoReceituario = null;
        boolean tipoRelatorioAtualizado = false;
        
        if(contemRelatorioAtivo(listTipoReceituarioRelatorios))
        {
            try
            {
                for (TipoReceituarioRelatorio tipoReceituarioRelatorio : listTipoReceituarioRelatorios)
                {
                    if(!tipoRelatorioAtualizado)
                    {
                        tipoReceituario = tipoReceituarioRelatorio.getTipoReceituario();
                        tipoReceituarioManager.atualizar(tipoReceituario);
                        tipoReceituarioRelatorio.setTipoReceituario(tipoReceituario);
                        atualizar(tipoReceituarioRelatorio);
                        tipoRelatorioAtualizado = true;
                    }
                    else
                    {
                        if(tipoReceituario != null)
                        {
                            
                            if(tipoReceituarioRelatorio.getId() != null)
                            {
                                tipoReceituarioRelatorio.setTipoReceituario(tipoReceituario);
                                atualizar(tipoReceituarioRelatorio);
                            }
                            else
                            {
                                tipoReceituarioRelatorio.setTipoReceituario(tipoReceituario);
                                salvar(tipoReceituarioRelatorio);
                            }
                        }
                    }
                }
            }
            catch (DataIntegrityViolationException e)
            {
                if(e.getCause() instanceof ConstraintViolationException)
                {
                    String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
                    
                    if (constraint.equals("DBAMVFOR.CNT_TIPO_RECEITUARIO_PK"))
                        throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_PK);
                    if (constraint.equals("DBAMVFOR.CNT_TIPO_RECEITUARIO_1_CK"))
                        throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_TP_COR_RECEITUARIO);
                    if (constraint.equals("DBAMVFOR.CNT_TIPO_RECEITUARIO_1_UK"))
                        throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_UNIQUE_CONSTRAINT);
                    
                    if (constraint.equals(CNT_TIPO_RELATORIO_PK))
                        throw new TipoReceituarioException(TipoRelatorioException.CNT_TIPO_RELATORIO_PK);
                    if (constraint.equals("DBAMVFOR.CNT_TIPO_RELATORIO_1_UK"))
                        throw new TipoReceituarioException(TipoRelatorioException.CNT_TIPO_RELATORIO_1_UK);
                    
                    if(constraint.equals("DBAMVFOR.CNT_TIPO_RECEITUARIO_REL_1_UK"))
                        throw new TipoReceituarioException(TipoReceituarioRelatorioException.CNT_TIPO_RECEITUARIO_REL_1_UK);
                }
            }  
        }
        else
            throw new TipoReceituarioException(TipoReceituarioRelatorioException.TIPO_RECEITUARIO_MSG_RELATORIO_ATIVO);
        
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.TipoReceituarioRelatorioManager#excluirTipoReceituarioRelatorio(br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituario)
     */
    @Override
    public void excluirTipoReceituarioRelatorio(TipoReceituario tipoReceituario) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        TipoReceituarioManager tipoReceituarioManager = SpringBeans.getManager(TipoReceituarioManager.class);
        try
        {
            Collection<TipoReceituarioRelatorio> listaReceitaReceituarioRelatorios = buscarTipoReceituarioPorTipoRelatorio(tipoReceituario);
            for (TipoReceituarioRelatorio tipoReceituarioRelatorio : listaReceitaReceituarioRelatorios)
            {
                tipoReceituario = tipoReceituarioRelatorio.getTipoReceituario();
                break;
            }
            
            for (TipoReceituarioRelatorio tipoReceituarioRelatorio : listaReceitaReceituarioRelatorios)
            {
                excluir(tipoReceituarioRelatorio);
            }
            
            tipoReceituarioManager.excluir(tipoReceituario);
        }
        catch (DataIntegrityViolationException e)
        {
            String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
            if (constraint.equals("DBAMVFOR.CNT_MEDICAMENTO_TP_RCTRIO_FK"))
                throw new TipoReceituarioException(TipoReceituarioException.CNT_MEDICAMENTO_TP_RCTRIO_FK);
        }
        
    }
    
    /**
     * Método que valida se existem pelomenos uma relatório cadastrado e ativo
     * 
     * @mv.uc - UC005 - Manter tipo de receituário.
     * @mv.fe - Pelomenos uma relatório deve está ativo.
     * 
     * @author Carlos Roberto
     * @version 1 Date: 22/08/2014 09:00
     * 
     * @param TipoReceituario
     * @return Long
     * 
     */
    private Boolean contemRelatorioAtivo(Collection<TipoReceituarioRelatorio> listTipoReceituarioRelatorios)
    {
        Boolean isRelatorioAtivo = false;
        for (TipoReceituarioRelatorio tipoReceituarioRelatorio : listTipoReceituarioRelatorios)
        {
            if(tipoReceituarioRelatorio.isAtivo())
            {
                isRelatorioAtivo = true;
                break;
            }
        }
        return isRelatorioAtivo;
    }
    
    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.TipoReceituarioRelatorioManager#salvar(br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituarioRelatorio)
     */
    @Override
    public TipoReceituarioRelatorio salvar(TipoReceituarioRelatorio tipioReceituarioRelatorio) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        return save(tipioReceituarioRelatorio);
    }
    
    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.TipoReceituarioRelatorioManager#atualizar(br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituarioRelatorio)
     */
    @Override
    public void atualizar(TipoReceituarioRelatorio tipoReceituarioRelatorio) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
    ClassNotFoundException
    {
        update(tipoReceituarioRelatorio, true);
    }
    
    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.TipoReceituarioRelatorioManager#excluir(br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituarioRelatorio)
     */
    @Override
    public void excluir(TipoReceituarioRelatorio tipoReceituarioRelatorio) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
    ClassNotFoundException
    {
        delete(tipoReceituarioRelatorio, true);
    }
}

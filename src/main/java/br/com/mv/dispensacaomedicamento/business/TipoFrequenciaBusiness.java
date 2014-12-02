package br.com.mv.dispensacaomedicamento.business;
    
    
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.util.SpringBeans;
import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.TipoFrequenciaDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.TipoFrequenciaException;
import br.com.mv.regulacao.dispensacaomedicamento.exception.TipoReceituarioException;
import br.com.mv.regulacao.dispensacaomedicamento.model.DetalheTipoFrequencia;
import br.com.mv.regulacao.dispensacaomedicamento.model.TipoFrequencia;
    
    /**
     * @author joao.franco
     *
     */
    @Named("tipoFrequenciaManager")
    public class TipoFrequenciaBusiness extends GenericManagerImpl<TipoFrequencia,TipoFrequenciaRepository> implements TipoFrequenciaManager
    {
        
        @Inject
        @Override
        public void setDao (TipoFrequenciaRepository dao){
            super.setDao(dao);
        }
        
        @Override
        public Long count(TipoFrequencia tipoFrequencia){
            return count(criarProjectionTipoFrequencia(tipoFrequencia));
        }
        
        @Override
        public Collection<TipoFrequencia> list(int initialPos, int maxResults, TipoFrequencia tipoFrequencia)
        {
            return fetch(initialPos, maxResults, criarProjectionTipoFrequencia(tipoFrequencia));
        }
    
    private ProjectionFilter[] criarProjectionTipoFrequencia(TipoFrequencia tipoFrequencia)
    {

        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();

        if (tipoFrequencia.getId() != null)
            listaProjection.add(new ProjectionFilter("id", tipoFrequencia.getId()));
        else
            listaProjection.add(new ProjectionFilter("id"));

        if (tipoFrequencia.getDescricaoFrequencia() != null && !tipoFrequencia.getDescricaoFrequencia().isEmpty())
            listaProjection.add(new ProjectionFilter("descricaoFrequencia", tipoFrequencia.getDescricaoFrequencia()));
        else
            listaProjection.add(new ProjectionFilter("descricaoFrequencia"));

        if (tipoFrequencia.getPeriodicidade() != null && tipoFrequencia.getPeriodicidade() > 0L)
            listaProjection.add(new ProjectionFilter("periodicidade", tipoFrequencia.getPeriodicidade()));
        else
            listaProjection.add(new ProjectionFilter("periodicidade"));

        if (tipoFrequencia.getImpressaoReceita() != null && !tipoFrequencia.getImpressaoReceita().isEmpty())
            listaProjection.add(new ProjectionFilter("impressaoReceita", tipoFrequencia.getImpressaoReceita()));
        else
            listaProjection.add(new ProjectionFilter("impressaoReceita"));

        if (tipoFrequencia.getHorarioInicial() != null)
            listaProjection.add(new ProjectionFilter("horarioInicial", tipoFrequencia.getHorarioInicial()));
        else
            listaProjection.add(new ProjectionFilter("horarioInicial"));

        listaProjection.add(new ProjectionFilter(Order.asc("descricaoFrequencia")));

        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);

        return projectionFilters;
    }

       
       @Override
       public void salvarDetalheTipoFrequencia(Collection<DetalheTipoFrequencia> listDetalheTipoFrequencia) throws DataIntegrityViolationException, NotUniqueIdException,
               InstantiationException, IllegalAccessException, ClassNotFoundException,TipoFrequenciaException
       {
           
           DetalheTipoFrequenciaManager detalheTipoFrequenciaManager = SpringBeans.getManager(DetalheTipoFrequenciaManager.class);
           
           TipoFrequencia tipoFrequencia = null;
           int count = 0;
           try
           {
               for (DetalheTipoFrequencia detalheTipoFrequencia : listDetalheTipoFrequencia)
               {
                   if(count < 1)
                   {
                       tipoFrequencia =  detalheTipoFrequencia.getTipoFrequencia();
                       tipoFrequencia = save(tipoFrequencia);
                       detalheTipoFrequencia.setTipoFrequencia(tipoFrequencia);
                       detalheTipoFrequenciaManager.salvar(detalheTipoFrequencia);
                       count++;
                   }
                   else
                   {
                       if(tipoFrequencia != null)
                       {
                           detalheTipoFrequencia.setTipoFrequencia(tipoFrequencia);
                           detalheTipoFrequenciaManager.salvar(detalheTipoFrequencia);
                       }
                   }
               }
               flush();
               clear();
               
           }
           
           catch (DataIntegrityViolationException e)
           {
               if(e.getCause() instanceof ConstraintViolationException)
               {
                   String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
                   if (constraint.equals("DBAMVFOR.CNT_TIPO_FREQUENCIA_PK"))
                       throw new TipoFrequenciaException(TipoFrequenciaException.EXCEPTION_PK);
                   if (constraint.equals("DBAMVFOR.CNT_TIPO_FREQUENCIA_1_UK"))
                       throw new TipoFrequenciaException(TipoFrequenciaException.EXCEPTION_UNIQUE_CONSTRAINT);
               }
           }
       }
       
    
    @Override
    public Collection<TipoFrequencia> listaTipoFrequencia(String descricaoTipoFrequencia)
    {
        TipoFrequencia tipoFrequencia = new TipoFrequencia();
        tipoFrequencia.setDescricaoFrequencia(descricaoTipoFrequencia);
        return get(criarProjectionTipoFrequencia(tipoFrequencia));
    }

    @Override
    public TipoFrequencia salvar(TipoFrequencia tipoFrequencia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
       return save(tipoFrequencia);
    }

    @Override
    public void atualizar(TipoFrequencia tipoFrequencia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
      update(tipoFrequencia);  
    }

    @Override
    public void excluir(TipoFrequencia tipoFrequencia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
       delete(tipoFrequencia); 
    }

    @Override
    public void atualizarDetalheTipoFrequencia(Collection<DetalheTipoFrequencia> listaDetalheTipoFrequencia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException, TipoFrequenciaException
    {
        DetalheTipoFrequenciaManager detalheTipoFrequenciaManager = SpringBeans.getManager(DetalheTipoFrequenciaManager.class);
        TipoFrequencia tipoFrequencia = null;
        int count = 0;

        try
        {
            if (listaDetalheTipoFrequencia != null)
            {
                for (DetalheTipoFrequencia detalheTipoFrequencia : listaDetalheTipoFrequencia)
                {
                    if (count < 1)
                    {
                        tipoFrequencia = detalheTipoFrequencia.getTipoFrequencia();
                        update(tipoFrequencia);
                        detalheTipoFrequencia.setTipoFrequencia(tipoFrequencia);
                        detalheTipoFrequenciaManager.update(detalheTipoFrequencia);
                        count++;
                    }
                    else
                    {
                        if (tipoFrequencia != null)
                        {
                            if (detalheTipoFrequencia.getId() != null)
                            {
                                detalheTipoFrequencia.setTipoFrequencia(tipoFrequencia);
                                detalheTipoFrequenciaManager.update(detalheTipoFrequencia);
                            }
                            else
                            {
                                detalheTipoFrequencia.setTipoFrequencia(tipoFrequencia);
                                detalheTipoFrequenciaManager.save(detalheTipoFrequencia);
                            }
                        }

                    }
                }

                flush();
                clear();

            }
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getCause() instanceof ConstraintViolationException)
            {
                String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();

                if (constraint.equals("DBAMVFOR.CNT_TIPO_FREQUENCIA_PK"))
                    throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_PK);
                if (constraint.equals("DBAMVFOR.CNT_CNT_TIPO_FREQUENCIA_1_UK"))
                    throw new TipoReceituarioException(TipoReceituarioException.EXCEPTION_UNIQUE_CONSTRAINT);

            }
        }
    }
    
    public void excluirTipoFrequencia(TipoFrequencia tipoFrequencia) throws Exception
    {
        
        DetalheTipoFrequenciaManager detalheTipoFrequenciaManager = SpringBeans.getManager(DetalheTipoFrequenciaManager.class);
        try
        {
            Collection<DetalheTipoFrequencia> listaDetalheTipoFrequencias = detalheTipoFrequenciaManager.buscarDetalheTipoFrequencia(tipoFrequencia);
            for (DetalheTipoFrequencia detalheTipoFrequencia : listaDetalheTipoFrequencias)
            {
                tipoFrequencia = detalheTipoFrequencia.getTipoFrequencia(); 
                break;
            }
            
            for (DetalheTipoFrequencia detalheTipoFrequencia : listaDetalheTipoFrequencias)
            {
                delete(detalheTipoFrequencia);
            }
            
            delete(tipoFrequencia);
            
            flush();
            clear();
            
        }
        catch (DataIntegrityViolationException e)
        {
            String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
            if (constraint.equals("DBAMVFOR.CNT_TIPO_FREQUENCIA_1_UK"))
                throw new TipoFrequenciaException(TipoFrequenciaException.EXCEPTION_UNIQUE_CONSTRAINT);
        }
        
    }
    
     @SuppressWarnings({
                "unused", "deprecation"
        })
    public Collection<DetalheTipoFrequencia> calcularHorarioMedicacao(DetalheTipoFrequencia detalheTipoFrequencia) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
    IllegalAccessException, ClassNotFoundException, TipoFrequenciaException{
        
        Collection<DetalheTipoFrequencia> listaDetalheTipoFrequencia = new ArrayList<DetalheTipoFrequencia>();
        
        
        
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Long periodicidade = detalheTipoFrequencia.getTipoFrequencia().getPeriodicidade();
        Date horaInicial = detalheTipoFrequencia.getHoraMedicacao(); 
        Long horariosRemedio=(24/periodicidade)-1;
        DetalheTipoFrequencia detalheTipoFrequenciainicial = new DetalheTipoFrequencia();
        detalheTipoFrequenciainicial.setHoraMedicacao(horaInicial);
        listaDetalheTipoFrequencia.add(detalheTipoFrequenciainicial);
        

        Time time = new Time(horaInicial.getHours(), horaInicial.getMinutes(), horaInicial.getSeconds());
        gc.setTimeInMillis(time.getTime());
        
        for(int i = 0 ; i < horariosRemedio ; i++)
        {
            DetalheTipoFrequencia detalheTipoFrequenciaAux = new DetalheTipoFrequencia();
            gc.add(Calendar.HOUR_OF_DAY, periodicidade.intValue());
            detalheTipoFrequenciaAux.setHoraMedicacao(gc.getTime());
            listaDetalheTipoFrequencia.add(detalheTipoFrequenciaAux);
            gc.setTime(gc.getTime());
            
        }
        
        return  listaDetalheTipoFrequencia;
}
    }       
    

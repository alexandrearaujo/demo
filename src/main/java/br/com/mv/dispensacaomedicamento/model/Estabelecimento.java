package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Juracy Lima
 * @date Tue Jan 27 11:53:06 GMT-03:00 2009
 */
@NamedQueries(
    {
            @NamedQuery(name = "Estabelecimento.listEstabelecimentoPorProximidadeBairro", query = "select latlong " + "  from Estabelecimento as latlong " + " where (6378.7 * "
                    + "           acos(sin(:latitudeBairro / 57.2958) * sin(latlong.numeroLatitude / 57.2958) + "
                    + "           cos(:latitudeBairro2 / 57.2958) * cos(latlong.numeroLatitude / 57.2958) * "
                    + "           cos(:longitudeBairro / 57.2958 - latlong.numeroLongitude / 57.2958)))*1000 <= :distancia " + " order by 4"),

            @NamedQuery(name = "Estabelecimento.getUserLoginReguladorPorId", query = "select "
                    + "new Estabelecimento(e.id, e.nomeFantasia, e.cnes, e.municipioGestor.descricaoMunicipio, e.municipioGestor.municipioId, cr.id, cr.descricao, cr.flagCentralMaster) from Estabelecimento e "
                    + "inner join e.municipioGestor.centralRegulacaoLeito cr " + "join e.usuarios u " + "where u.id = :idUsuario " + "order by e.nomeFantasia ")
    })
@Entity
@Table(name = "TB_ESTABELECIMENTO")
public class Estabelecimento implements Serializable
{

    @Id
    @SequenceGenerator(name = "SQ_TB_ESTABELECIMENTO", sequenceName = "SQ_TB_ESTABELECIMENTO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TB_ESTABELECIMENTO")
    @Column(name = "CD_ESTABELECIMENTO", nullable = false, length = 7)
    private Long id;
    
    @Transient
    private transient String estabelecimentoCNES;

    @Column(name = "NM_FANTASIA", nullable = false, length = 60)
    private String nomeFantasia;

    @Column(name = "TP_NIVEL_DEP", nullable = true, length = 1)
    private Long nivelDependencia;
    
    @Transient
    private transient String nivelDependenciaCNES;

    @Column(name = "NR_CNES", nullable = true, length = 7)
    private String cnes;

    @Column(name = "NR_CNPJ_MANT", nullable = true, length = 14)
    private String cnpjMantenedora;

    @Column(name = "TP_PFPJ_IND", nullable = true, length = 1)
    private Long tipoPessoa;
    
    @Transient
    private transient String tipoPessoaCNES;

    @Column(name = "NM_RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "NM_LOGRADOURO", nullable = false, length = 60)
    private String logradouro;

    @Column(name = "NR_NUMERO", nullable = true, length = 10)
    private String numeroLougradouro;

    @Column(name = "DS_COMPLEMENT", nullable = true, length = 60)
    private String complementoEndereco;

    @Column(name = "NM_BAIRRO", nullable = true, length = 60)
    private String bairro;

    @Column(name = "NR_COD_CEP", nullable = true, length = 8)
    private String cep;
//
//    @Column(name = "SN_TERCEIRO_SIH", nullable = true, length = 1)
//    private Long terceiro;
//    
//    @Transient
//    @Element(name = "SN_TERCEIRO_SIH", required = false)
//    private transient String terceiroCNES;
//
//    @Transient
//    @Element(name = "CD_TIPO_UNIDADE_CNES", required = false)
//    private transient String tipoUnidadeCNES;
//    
//    @Column(name = "NR_SENHA")
//    private Long senha;
//
//    @Column(name = "NR_TELEFONE", nullable = true, length = 40)
//    @Element(name = "NR_TELEFONE", required = false)
//    private String numeroTelefone;
//
//    @Column(name = "NR_FAX", nullable = true, length = 60)
//    @Element(name = "NR_FAX", required = false)
//    private String numeroFax;
//
//    @Column(name = "DS_EMAIL", nullable = true, length = 60)
//    @Element(name = "DS_EMAIL", required = false)
//    private String email;
//
//    @Column(name = "NR_CPF", nullable = true, length = 11)
//    @Element(name = "NR_CPF", required = false)
//    private String cpf;
//
//    @Column(name = "NR_CNPJ", nullable = true, length = 14)
//    @Element(name = "NR_CNPJ", required = false)
//    private String cnpj;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_TIPO_UNIDADE", referencedColumnName = "CD_TIPO_UNIDADE")
//    private TipoUnidade tipoUnidade;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_ATIVIDADE_ENSINO", referencedColumnName = "CD_ATIVIDADE_ENSINO")
//    private AtividadeEnsino atividadeEnsino;
//    
//    @Transient
//    @Element(name = "CD_ATIVIDADE_ENSINO_CNES", required = false)
//    private transient String atividadeEnsinoCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_RETENCAO_TRIBUTO", referencedColumnName = "CD_RETENCAO_TRIBUTO")
//    private RetencaoTributo retencaoTributo;
//
//    @Transient
//    @Element(name = "CD_RETENCAO_TRIBUTO_CNES", required = false)
//    private transient String retencaoTributoCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_ESFERA_ADMINISTRATIVA", referencedColumnName = "CD_ESFERA_ADMINISTRATIVA")
//    private EsferaAdministrativa esferaAdministrativa;
//    
//    @Transient
//    @Element(name = "CD_ESFERA_ADMINISTRATIVA_CNES", required = false)
//    private transient String esferaAdministrativaCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_FLUXO_CLIENTELA", referencedColumnName = "CD_FLUXO_CLIENTELA")
//    private FluxoClientela fluxoClientela;
//    
//    @Transient
//    @Element(name = "CD_FLUXO_CLIENTELA_CNES", required = false)
//    private transient String fluxoClientelaCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_NATUREZA_ORGANIZACAO", referencedColumnName = "CD_NATUREZA_ORGANIZACAO")
//    private NaturezaOrganizacao naturezaOrganizacao;
//    
//    @Transient
//    @Element(name = "CD_NATUREZA_ORGANIZACAO_CNES", required = false)
//    private transient String naturezaOrganizacaoCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_UF")
//    private Uf uf;
//
//    @Transient
//    @Element(name = "CD_UF_CNES", required = false)
//    private transient String ufCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_MUNICIPIO_GESTOR")
//    private Municipio municipioGestor;
//    
//    @Transient
//    @Element(name = "CD_MUNICIPIO_GESTOR_CNES", required = false)
//    private transient String municipioGestorCNES;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_SEGMENTO_TERRITORIAL")
//    private SegmentoTerritorial segmentoTerritorial;
//
//    @Transient
//    @Element(name = "CD_SEGMENTO_TERRITORIAL_CNES", required = false)
//    private transient String segmentoTerritorialCNES;
//    
//    /*
//     * So servirá para a regulação de leitos solicitação feita pela SESA-ES
//     */
//    @Column(name = "SN_PACTUADO", nullable = true, length = 1)
//    private Long pactuado;
//
//    @ManyToMany(targetEntity = User.class, cascade = CascadeType.REFRESH)
//    @JoinTable(name = "tb_usuario_estabelecimento", joinColumns =
//        {
//            @JoinColumn(name = "cd_estabelecimento", insertable = false, updatable = false)
//        }, inverseJoinColumns =
//        {
//            @JoinColumn(name = "cd_usuario", insertable = false, updatable = false)
//        })
//    private List<User> usuarios;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CD_NIVEL_HIERARQUIA", referencedColumnName = "CD_NIVEL_HIERARQUIA")
//    private NivelHierarquia nivelHierarquia;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CD_ESTABELECIMENTO", referencedColumnName = "CD_CENTRAL", updatable = false, insertable = false)
//    private CentralRegulacaoLeito centralRegulacaoLeito;
//    
//    @Transient
//    @Element(name = "CD_NIVEL_HIERARQUIA_CNES",required = false)
//    private transient String nivelHierarquiaCNES;
//    /*
//     * Esses campos sao usados somente nas tabelas do MS.
//     * 
//     * cd_comp_inicial varchar2(6) y cd_comp_final varchar2(6) y dt_validacao
//     * date y tp_adesao_filantrop varchar2(1) y cmpt_vigente varchar2(6) y
//     */
//    @Column(name = "NR_LATITUDE", columnDefinition = "number(11,8) default 0")
//    private BigDecimal numeroLatitude;
//
//    @Column(name = "NR_LONGITUDE", columnDefinition = "number(11,8) default 0")
//    private BigDecimal numeroLongitude;
//
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = FoneEstabelecimento.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @NotAudited
//    private List<FoneEstabelecimento> foneEstabelecimentos;
//
//    @Column(name = "NR_SUS", length = 31)
//    @Element(name = "NR_SUS", required = false)
//    private String numeroSUS;
//
//    @Column(name = "DT_AVALIACAO_PNASS")
//    @Element(name = "DT_AVALIACAO_PNASS", required = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dataAvaliacaoPnass;
//
//    @Column(name = "SN_AVALIACAO_PNASS")
//    private Boolean flagAvaliacaoPnass;
//
//    @Transient
//    @Element(name = "SN_AVALIACAO_PNASS", required = false)
//    private transient String flagAvaliacaoPnassCNES;
//    
//    @Column(name = "SN_IND_AVALIA", nullable = true, length = 1)
//    @Element(name = "SN_IND_AVALIA", required = false)
//    private String Avaliado;
//
//    @Column(name = "CD_REGIONAL", length = 4)
//    private Long regional;
//
//    @Transient
//    @Element(name = "CD_REGIONAL", required = false)
//    private transient String regionalCNES;
//    
//    @Column(name = "CD_MICROREGIONAL", length = 6)
//    @Element(name = "CD_MICROREGIONAL", required = false)
//    private String microRegional;
//
//    @Column(name = "CD_DIST_SANIT", length = 4)
//    @Element(name = "CD_DIST_SANIT", required = false)
//    private String distritoSanitario;
//
//    @Column(name = "CD_DIST_ADMIN", length = 4)
//    @Element(name = "CD_DIST_ADMIN", required = false)
//    private String distritoAdministrador;
//
//    @Column(name = "NR_ALVARA", length = 1)
//    @Element(name = "NR_ALVARA", required = false)
//    private String numeroAlvara;
//
//    @Column(name = "DT_EXPED", length = 10)
//    @Element(name = "DT_EXPED", required = false)
//    private Date dataExpedicaoAlvara;
//
//    @Column(name = "NM_IND_ORGEXP", length = 2)
//    @Element(name = "NM_IND_ORGEXP", required = false)
//    private String orgaoExpedidor;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_TURNO_ATENDIMENTO", referencedColumnName = "CD_TURNO_ATENDIMENTO")
//    private TurnoAtendimento turnoAtendimento;
//
//    @Transient
//    @Element(name = "CD_TURNO_ATENDIMENTO_CNES", required = false)
//    private transient String turnoAtendimentoCNES;
//    
//    @Column(name = "NM_SIGLA_ESTADO", length = 2)
//    @Element(name = "NM_SIGLA_ESTADO", required = false)
//    private String siglaEstado;
//
//    /*
//     * Status do Estabelecimento de Saúde (1 - Não Aprovado 2 - Consistido 3 -
//     * Exportado B - Registro Bloqueado U - Registro em Uso ). private String
//     */
//    @Column(name = "TP_STATUSMOV", length = 1)
//    @Element(name = "TP_STATUSMOV", required = false)
//    private String statusEstabelecimento;
//
//    @Column(name = "TP_PRESTADOR", length = 2)
//    @Element(name = "TP_PRESTADOR", required = false)
//    private String tipoPrestador;
//
//    @Column(name = "DT_ULTIMA_ATUALIZACAO", length = 2)
//    @Temporal(TemporalType.DATE)
//    @Element(name = "DT_ULTIMA_ATUALIZACAO", required = false)
//    private Date dataUltimaAtualizacao;
//
//    @Column(name = "NR_USUARIO_ATUALIZOU", length = 12)
//    @Element(name = "NR_USUARIO_ATUALIZOU", required = false)
//    private String usuarioAtualizou;
//
//    @Column(name = "NR_CPFDIRETORCLINICO", length = 14)
//    @Element(name = "NR_CPFDIRETORCLINICO", required = false)
//    private String cpfDiretor;
//
//    @Column(name = "NR_REGDIRETORCLINICO", length = 60)
//    @Element(name = "NR_REGDIRETORCLINICO", required = false)
//    private String registroConselhoDiretor;
//
//    @Column(name = "TP_CLASSIF_AVALIACAO", nullable = true, length = 1)
//    @Element(name = "TP_CLASSIF_AVALIACAO", required = false)
//    private String classificacaoAvaliacao;
//
//    @Column(name = "DT_ACREDITACAO", nullable = true)
//    @Temporal(TemporalType.DATE)
//    @Element(name = "DT_ACREDITACAO", required = false)
//    private Date dataAcreditacao;
//
//    @ManyToOne
//    @JoinColumn(name = "CD_MOTIVO_DESATIVACAO", referencedColumnName = "CD_MOTIVO_DESATIVACAO")
//    private MotivoDesativacao motivoDesativacao;
//
//    @Transient
//    @Element(name = "CD_MOTIVO_DESABILITACAO_CNES", required = false)
//    private transient String motivoDesativacaoCNES;
//    
//    @Transient
//    private transient String convenioCNES;
//    
//    @OneToOne(mappedBy = "estabelecimento", targetEntity = EstabelecimentoParametro.class, fetch = FetchType.LAZY)
//    private EstabelecimentoParametro estabelecimentoParametro;
//
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = EstabelecimentoAtividade.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<EstabelecimentoAtividade> listEstabelecimentoAtividade;
//
//    @OneToOne(mappedBy = "estabelecimento", targetEntity = Dialise.class, fetch = FetchType.LAZY)
//    private Dialise dialise;
//
//    @OneToOne(mappedBy = "estabelecimento", targetEntity = Hemoterapia.class, fetch = FetchType.LAZY)
//    private Hemoterapia hemoterapia;
//
//    @OneToOne(mappedBy = "estabelecimento", targetEntity = QuimioterapiaRadioterapia.class, fetch = FetchType.LAZY)
//    private QuimioterapiaRadioterapia quimioterapiaRadioterapia;
//
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = ServicoReferenciado.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<ServicoReferenciado> listServicoReferenciado;
//
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = EstabelecimentoServicoClassificacao.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<EstabelecimentoServicoClassificacao> listEstabelecimentoServicoClassificacao;
//    
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = EstabelecimentoAtendimentoPrestado.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<EstabelecimentoAtendimentoPrestado> listEstabelecimentoAtendimentoPrestado;
//    
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = InstalacoesFisicasEstabelecimento.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<InstalacoesFisicasEstabelecimento> listInstalacoesFisicasEstabelecimento;
//    
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = ColetaSeletivaRejeitoEstabelecimento.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<ColetaSeletivaRejeitoEstabelecimento> listColetaSeletivaRejeitoEstabelecimento;
//    
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = EstabelecimentoEquipamento.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<EstabelecimentoEquipamento> listEstabelecimentoEquipamento;
//    
//    @OneToMany(mappedBy = "estabelecimento", targetEntity = EstabelecimentoLeitoCNES.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Collection<EstabelecimentoLeitoCNES> listEstabelecimentoLeitoCNES;
//
//    @ManyToMany(targetEntity = SubtipoUnidade.class, cascade = CascadeType.ALL)
//    @JoinTable(name = "ESTABELECIMENTO_SUBTIPO_UNID",  joinColumns =
//        {
//            @JoinColumn(name = "CD_ESTABELECIMENTO")
//        }, inverseJoinColumns =
//        {
//            @JoinColumn(name = "CD_SUBTIPO_UNIDADE")
//        })
//    @Column(name="CD_ESTABELECIMENTO_SUBTP_UNID")
//    @SequenceGenerator(name = "SEQ_ESTABELECIMENTO_SUBTP_UNID", sequenceName = "SEQ_ESTABELECIMENTO_SUBTP_UNID", allocationSize = 0)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTABELECIMENTO_SUBTP_UNID")
//    private Collection<SubtipoUnidade> listSubtipoUnidade;
//    
//    public Estabelecimento()
//    {
//    }
//
//    public Estabelecimento(Long id, String nomeFantasia, String cnes, String descricaoMunicipio, Long municipioId)
//    {
//        this.id = id;
//        this.nomeFantasia = nomeFantasia;
//        this.cnes = cnes;
//        this.municipioGestor = new Municipio();
//        this.municipioGestor.setMunicipioId(municipioId);
//        this.municipioGestor.setDescricaoMunicipio(descricaoMunicipio);
//    }
//
//    public Estabelecimento(Long id, String nomeFantasia, String cnes, String descricaoMunicipio, Long municipioId, Long centralMunicipioId, String descricaoCentral, Boolean flagCentralMaster)
//    {
//        this.id = id;
//        this.nomeFantasia = nomeFantasia;
//        this.cnes = cnes;
//        this.municipioGestor = new Municipio();
//        this.municipioGestor.setMunicipioId(municipioId);
//        this.municipioGestor.setDescricaoMunicipio(descricaoMunicipio);
//        this.municipioGestor.setCentralRegulacaoLeito(new CentralRegulacaoLeito());
//        this.municipioGestor.getCentralRegulacaoLeito().setId(centralMunicipioId);
//        this.municipioGestor.getCentralRegulacaoLeito().setDescricao(descricaoCentral);
//        this.municipioGestor.getCentralRegulacaoLeito().setFlagCentralMaster(flagCentralMaster);
//    }
//
//    public Estabelecimento(Long id, String nomeFantasia)
//    {
//        this.id = id;
//        this.nomeFantasia = nomeFantasia;
//    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstabelecimentoCNES() {
		return estabelecimentoCNES;
	}

	public void setEstabelecimentoCNES(String estabelecimentoCNES) {
		this.estabelecimentoCNES = estabelecimentoCNES;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Long getNivelDependencia() {
		return nivelDependencia;
	}

	public void setNivelDependencia(Long nivelDependencia) {
		this.nivelDependencia = nivelDependencia;
	}

	public String getNivelDependenciaCNES() {
		return nivelDependenciaCNES;
	}

	public void setNivelDependenciaCNES(String nivelDependenciaCNES) {
		this.nivelDependenciaCNES = nivelDependenciaCNES;
	}

	public String getCnes() {
		return cnes;
	}

	public void setCnes(String cnes) {
		this.cnes = cnes;
	}

	public String getCnpjMantenedora() {
		return cnpjMantenedora;
	}

	public void setCnpjMantenedora(String cnpjMantenedora) {
		this.cnpjMantenedora = cnpjMantenedora;
	}

	public Long getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Long tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getTipoPessoaCNES() {
		return tipoPessoaCNES;
	}

	public void setTipoPessoaCNES(String tipoPessoaCNES) {
		this.tipoPessoaCNES = tipoPessoaCNES;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeroLougradouro() {
		return numeroLougradouro;
	}

	public void setNumeroLougradouro(String numeroLougradouro) {
		this.numeroLougradouro = numeroLougradouro;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

    
    
}


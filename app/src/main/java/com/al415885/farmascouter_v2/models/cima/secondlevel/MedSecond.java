package com.al415885.farmascouter_v2.models.cima.secondlevel;

import com.al415885.farmascouter_v2.SerializableCollectionsType;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.Documento;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.Estado;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.FormaFarmaceutica;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.FormaFarmaceuticaSimplificada;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.Foto;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.NoSustituible;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.ViaAdministracion;
import com.al415885.farmascouter_v2.models.cima.thirdlevel.Vtm;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName = "favouriteDrugs_table")
public class MedSecond implements Serializable {

    // Class-specific variables
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String nregistro, nombre, labtitular, cpresc, dosis;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private Estado estado;
    @DatabaseField
    private boolean comerc, receta, generico, conduc, triangulo, huerfano, biosimilar;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private List<NoSustituible> noSustituible;
    @DatabaseField
    private boolean psum, notas, materialesInf, ema;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private List<Documento> docs;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private List<Foto> fotos;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private List<ViaAdministracion> viasAdministracion;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private FormaFarmaceutica formaFarmaceutica;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private FormaFarmaceuticaSimplificada formaFarmaceuticaSimplificada;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private Vtm vtm;
    @DatabaseField(persisterClass =  SerializableCollectionsType.class)
    private String search;

    public MedSecond() { }

    public MedSecond(String nregistro, String nombre, String labtitular, String cpresc,
                     Estado estado, boolean comerc, boolean receta, boolean generico,
                     boolean conduc, boolean triangulo, boolean huerfano, boolean biosimilar,
                     List<NoSustituible> noSustituible, boolean psum, boolean notas,
                     boolean materialesInf, boolean ema, List<Documento> docs, List<Foto> fotos,
                     List<ViaAdministracion> viasAdministracion,
                     FormaFarmaceutica formaFarmaceutica,
                     FormaFarmaceuticaSimplificada formaFarmaceuticaSimplificada,
                     Vtm vtm) {
        this.nregistro = nregistro;
        this.nombre = nombre;
        this.labtitular = labtitular;
        this.cpresc = cpresc;
        this.estado = estado;
        this.comerc = comerc;
        this.receta = receta;
        this.generico = generico;
        this.conduc = conduc;
        this.triangulo = triangulo;
        this.huerfano = huerfano;
        this.biosimilar = biosimilar;
        this.noSustituible = noSustituible;
        this.psum = psum;
        this.notas = notas;
        this.materialesInf = materialesInf;
        this.ema = ema;
        this.docs = docs;
        this.fotos = fotos;
        this.viasAdministracion = viasAdministracion;
        this.formaFarmaceutica = formaFarmaceutica;
        this.formaFarmaceuticaSimplificada = formaFarmaceuticaSimplificada;
        this.vtm = vtm;
        this.id = Integer.parseInt(this.nregistro);
    }

    public String getNregistro() {
        return this.nregistro;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getLabtitular(){
        return this.labtitular;
    }

    public String getCpresc(){
        return this.cpresc;
    }

    public String getDosis(){
        return this.dosis;
    }

    public Estado getEstado(){
        return this.estado;
    }

    public boolean isComerc(){
        return this.comerc;
    }

    public boolean isReceta(){
        return this.receta;
    }

    public boolean isGenerico(){
        return this.generico;
    }

    public boolean isConduc(){
        return this.conduc;
    }

    public boolean isTriangulo(){
        return this.triangulo;
    }

    public boolean isHuerfano(){
        return this.huerfano;
    }

    public boolean isBiosimilar(){
        return this.biosimilar;
    }

    public boolean isPsum(){
        return this.psum;
    }

    public boolean isNotas(){
        return this.notas;
    }

    public boolean isMaterialesInf(){
        return this.materialesInf;
    }

    public boolean isEma(){
        return this.ema;
    }

    public List<Documento> getDocs(){
        return this.docs;
    }

    public List<NoSustituible> getNoSustituible(){
        return this.noSustituible;
    }
    public List<Foto> getFotos(){
        return this.fotos;
    }

    public List<ViaAdministracion> getViasAdministracion(){
        return this.viasAdministracion;
    }

    public FormaFarmaceutica getFormaFarmaceutica(){
        return this.formaFarmaceutica;
    }

    public FormaFarmaceuticaSimplificada getFormaFarmaceuticaSimplificada(){
        return this.formaFarmaceuticaSimplificada;
    }

    public Vtm getVTM(){
        return this.vtm;
    }

    public void setSearch(String search){
        this.search = search;
    }

    public String getSearch(){
        return this.search;
    }
}


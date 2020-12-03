package com.example.julio.inspector;

/**
 * Created by JULIO on 14/11/2017.
 */

public class Anime {

    private String Inspector;
    private String Puesto;
    private String Riesgos;
    private String Desperfectos;
    private String Observaciones;
    private String fecha;
    private String hora;
    private String mes;
    private String aux_adm;
    private String aux_rx;
    private String aux_barreras;
    private String aux_trazas;
    private String aux_tornos;
    private String aux_d_liquidos;
    private String aux_m_zapatos;
    private String photoId;
    private String photoId2;
    private String photoId3;
    private String photoId4;

    public Anime(){}

    public Anime( String photoId) {


        this.photoId = photoId;

    }

    public String getInspector() {
        return Inspector;
    }

    public String getDesperfectos() {
        return Desperfectos;
    }

    public String getRiesgos() {
        return Riesgos;
    }

    public String photoId() {
        return photoId;
    }

    public String getFecha(){return fecha;}

    public String getHora() {
        return hora;
    }

    public String getObservaciones() {
        return Observaciones;
    }
    public String getAux_adm() {
        return aux_adm;
    }

    public String getAux_rx() {
        return aux_rx;
    }

    public String getAux_d_liquidos() {
        return aux_d_liquidos;
    }

    public String getAux_m_zapatos() {
        return aux_m_zapatos;
    }

    public String getAux_barreras() {
        return aux_barreras;
    }

    public String getAux_tornos() {
        return aux_tornos;
    }

    public String getAux_trazas() {
        return aux_trazas;
    }

    public String getPhotoId2() {
        return photoId2;
    }

    public String getPhotoId3() {
        return photoId3;
    }

    public String getPhotoId4() {
        return photoId4;
    }

    public String getPuesto() {
        return Puesto;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getMes() {
        return mes;
    }

    public void setInspector(String inspector) {
        Inspector = inspector;
    }

    public void setDesperfectos(String desperfectos) {
        Desperfectos = desperfectos;
    }

    public void setRiesgos(String riesgos) {
        Riesgos = riesgos;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public void setAux_adm(String aux_adm) {
        this.aux_adm = aux_adm;
    }

    public void setAux_barreras(String aux_barreras) {
        this.aux_barreras = aux_barreras;
    }

    public void setAux_d_liquidos(String aux_d_liquidos) {
        this.aux_d_liquidos = aux_d_liquidos;
    }

    public void setAux_m_zapatos(String aux_m_zapatos) {
        this.aux_m_zapatos = aux_m_zapatos;
    }

    public void setAux_rx(String aux_rx) {
        this.aux_rx = aux_rx;
    }

    public void setAux_tornos(String aux_tornos) {
        this.aux_tornos = aux_tornos;
    }

    public void setAux_trazas(String aux_trazas) {
        this.aux_trazas = aux_trazas;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public void setPhotoId2(String photoId2) {
        this.photoId2 = photoId2;
    }

    public void setPhotoId3(String photoId3) {
        this.photoId3 = photoId3;
    }

    public void setPhotoId4(String photoId4) {
        this.photoId4 = photoId4;
    }

    public void setPuesto(String puesto) {
        Puesto = puesto;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}

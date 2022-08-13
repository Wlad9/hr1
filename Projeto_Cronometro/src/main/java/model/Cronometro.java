/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author wladi
 */
public class Cronometro {
    private int idCrono;
    private int idCavalo;
    private String cronometro;
    private String raia; //filtro
    private Date data;
    private String pareo;
    private float tempo;
    private String tipo_pareo;
    private String corpo_chegada;
    private int colocacao;

    public Cronometro(int idCavalo, String cronometro, String raia, Date data, String pareo, float tempo, String tipo_pareo, String corpo_chegada, int colocacao) {
        this.idCavalo = idCavalo;
        this.cronometro = cronometro;
        this.raia = raia;
        this.data = data;
        this.pareo = pareo;
        this.tempo = tempo;
        this.tipo_pareo = tipo_pareo;
        this.corpo_chegada = corpo_chegada;
        this.colocacao = colocacao;
    }

    public int getIdCavalo() {
        return idCavalo;
    }

    public void setIdCavalo(int idCavalo) {
        this.idCavalo = idCavalo;
    }

    public String getCronometro() {
        return cronometro;
    }

    public void setCronometro(String cronometro) {
        this.cronometro = cronometro;
    }

    public String getRaia() {
        return raia;
    }

    public void setRaia(String raia) {
        this.raia = raia;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPareo() {
        return pareo;
    }

    public void setPareo(String pareo) {
        this.pareo = pareo;
    }

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public String getTipo_pareo() {
        return tipo_pareo;
    }

    public void setTipo_pareo(String tipo_pareo) {
        this.tipo_pareo = tipo_pareo;
    }

    public String getCorpo_chegada() {
        return corpo_chegada;
    }

    public void setCorpo_chegada(String corpo_chegada) {
        this.corpo_chegada = corpo_chegada;
    }

    public int getColocacao() {
        return colocacao;
    }

    public void setColocacao(int colocacao) {
        this.colocacao = colocacao;
    }
    public Cronometro(){
        
    }

    @Override
    public String toString() {
        return "id=" + idCavalo + ", crono=" + cronometro + ", raia=" + raia + ", data=" + data + ", pareo=" + pareo + ", tempo=" + tempo + ", tipo_pareo=" + tipo_pareo + ", corpo=" + corpo_chegada + ", colocacao=" + colocacao + '}';
    }
    
}

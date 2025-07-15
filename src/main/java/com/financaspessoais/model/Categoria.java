package com.financaspessoais.model;

public class Categoria {
    private Integer id;
    private String nome;
    private Transacao.TipoTransacao tipo;

    // Construtores
    public Categoria() {}

    public Categoria(String nome, Transacao.TipoTransacao tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Transacao.TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(Transacao.TipoTransacao tipo) {
        this.tipo = tipo;
    }
}
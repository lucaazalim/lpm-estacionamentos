package br.pucminas.titas;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class VagaTest {
    private Vaga vaga;

    @Before
    public void setUp() {
        vaga = new Vaga("A", 1);
    }

    @Test
    public void testEstacionar() {
        assertTrue(vaga.estacionar());
        assertFalse(vaga.disponivel());
    }

    @Test
    public void testEstacionarVagaJaOcupada() {
        vaga.estacionar(); 
        assertFalse(vaga.estacionar()); 
        assertFalse(vaga.disponivel());
    }

    @Test
    public void testSair() {
        vaga.estacionar(); 
        assertTrue(vaga.sair()); 
        assertTrue(vaga.disponivel());
    }

    @Test
    public void testSairVagaJaLivre() {
        assertTrue(vaga.sair()); 
        assertTrue(vaga.disponivel());
    }

    @Test
    public void testDisponivel() {
        assertTrue(vaga.disponivel());
        vaga.estacionar(); 
        assertFalse(vaga.disponivel());
        vaga.sair(); 
        assertTrue(vaga.disponivel());
    }
}

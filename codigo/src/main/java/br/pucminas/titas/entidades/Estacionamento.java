package br.pucminas.titas.entidades;

import br.pucminas.titas.excecoes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Estacionamento implements Serializable {

    private final String nome;
    private List<Cliente> clientes;
    private List<Vaga> vagas;
    private final int quantFileiras;
    private final int vagasPorFileira;

    public Estacionamento(String nome, int fileiras, int vagasPorFila) {
        this.nome = nome;
        this.clientes = new LinkedList<>();
        this.quantFileiras = fileiras;
        this.vagasPorFileira = vagasPorFila;
        gerarVagas();
    }

    /**
     * Adiciona um veículo para um cliente especificado no estacionamento.
     * Recebe como parâmetro um veículo válido e um id de cliente existente. Em caso de cliente inexistente, 
     * lança uma exceção.
     * @param veiculo O veículo a ser adicionado.
     * @param idCliente O ID do cliente proprietário do veículo.
     * @throws NoSuchElementException Em caso de cliente não existente.
     */
    public void addVeiculo(Veiculo veiculo, String idCliente) throws NoSuchElementException {
        this.encontrarCliente(idCliente)
            .get()
            .addVeiculo(veiculo);
    }

    /**
     * Localiza um cliente no estacionamento usando o ID do cliente fornecido.
     *
     * @param idCliente O ID do cliente a ser localizado.
     * @return O objeto cliente, se encontrado, caso contrário, retorna null.
     */
    public Optional<Cliente> encontrarCliente(String idCliente) {
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(idCliente))
                .findFirst();
    }

    /**
     * Adiciona um cliente ao estacionamento.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void addCliente(Cliente cliente) {
        if (cliente != null) {
            this.clientes.add(cliente);
        }
    }

    /**
     * Gera vagas de estacionamento com base no número de fileiras e vagas por fileira.
     */
    private void gerarVagas() {
        this.vagas = new ArrayList<>();
        for (int i = 1; i <= quantFileiras; i++) {
            for (int j = 1; j <= vagasPorFileira; j++) {
                vagas.add(new Vaga(i, j));
            }
        }
    }

    //Parte responsável pelo aluno Gabriel.

    /**
    * Procura por vagas disponíveis. Estaciona o veículo.
    *
    * @param placa. O veículo a ser estacionado.
    */
    public void estacionar(String placa) throws EstacionamentoLotadoException {

        Vaga vagaDisponivel = this.encontrarVagaDisponivel();
        Veiculo veiculo = this.procurarVeiculo(placa);

        if (vagaDisponivel == null) {
            throw new EstacionamentoLotadoException();
        } else {
            try {
                veiculo.estacionar(vagaDisponivel);
            } catch (VagaNaoDisponivelException ignored) {
                // Exceção pode ser ignorada porque já foi confirmado que a vaga está disponível
            }
        }
    }

    /**
    * Procura por vagas disponíveis.
    *
    * @return a vaga encontrada. Se nenhuma estiver disponível, retorna null.
    */
    private Vaga encontrarVagaDisponivel() {
        for (Vaga vaga : this.vagas) {
            if (vaga.disponivel()) {
                return vaga;
            }
        }
        return null;
    }

    /**
    * Verifica se o cliente possui veículo com a placa especificada.
    *
    * @param placa. A placa do veículo a ser procurado.
    * @return o veículo correspondente.
    */
    Veiculo procurarVeiculo(String placa) {

        Veiculo veiculo;

        for (Cliente cliente : clientes) {

            veiculo = cliente.possuiVeiculo(placa);

            if (veiculo != null) {
                return veiculo;
            }

        }

        throw new VeiculoNaoEncontradoException(placa);

    }

    /** 
    *Remove o veículo da vaga.
    *
    * @param placa. A placa correspondente ao veículo.
    */
    public double sair(String placa) throws ServicoNaoTerminadoException, VeiculoNaoEstaEstacionadoException {

        Veiculo veiculo = this.procurarVeiculo(placa);
        return veiculo.sair();

    }

    /**
    * Calcula o montante total arrecadado do estacionamento.
    *
    * @return total arrecadado do estacionamento.
    */
    public double totalArrecadado() {
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.arrecadadoTotal();
        }
        return total;
    }

    /**
    * Calcula o montante total arrecadado do estacionamento em determinado mês.
    *
    * @param mes O mês a ser considerado.
    * @return o total arrecadado do estacionamento no mês.
    */
    public double arrecadacaoNoMes(int mes) {
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.arrecadadoNoMes(mes);
        }
        return total;
    }

    /**
    * Calcula o valor médio de cada utilização do estacionamento.
    *
    *  @return media, o valor médio por uso
    */
    public double valorMedioPorUso() {
        double media = 0;
        double soma = 0;
        int numClientes = 0;
        for (Cliente cliente : clientes) {
            soma += cliente.arrecadadoTotal();
            numClientes++;
        }
        media = soma/numClientes;
        return media;
    }

    public String top5Clientes(int mes) {
        return null; // CRIAR MÉTODO PRIVADO: ORGANIZARTOP5CLIENTES(mes)
    }

    public String getNome() {
        return this.nome;
    }

    public List<Cliente> getClientes() {
        return this.clientes;
    }
}

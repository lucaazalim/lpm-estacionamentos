package br.pucminas.titas.entidades;

import br.pucminas.titas.plano.Plano;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Cliente implements Serializable {

    private final String nome;
    private final int id;
    private Plano plano;
    private final List<Veiculo> veiculos;
    private static int proximoCliente;

    static {
        proximoCliente = 1;
    }

    /**
     * Constrói um novo objeto Cliente com o nome e id fornecidos.
     * Inicializa um array vazio de veículos com um tamanho de 100.
     *
     * @param nome O nome do cliente.
     */
    public Cliente(String nome) {
        this.nome = nome;
        this.id = proximoCliente;
        proximoCliente++;
        this.plano = new Plano.Horista();
        this.veiculos = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public Plano getPlano() {
        return this.plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    /**
     * Adiciona um veículo à coleção do cliente.
     *
     * @param veiculo O veículo a ser adicionado.
     */
    public void addVeiculo(Veiculo veiculo) {
        this.veiculos.add(veiculo);
    }

    /**
     * Verifica se o cliente possui um veículo com uma determinada placa.
     *
     * @param placa A placa para verificar.
     * @return O veículo com a placa correspondente, ou null se não encontrado.
     */
    public Veiculo possuiVeiculo(String placa) {
        return this.veiculos.stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa))
                .findFirst().orElse(null);
    }

    /**
     * Calcula o total de usos de todos os veículos do cliente.
     *
     * @return O total de usos de todos os veículos.
     */
    public int totalDeUsos() {
        return this.veiculos.stream()
                .mapToInt(Veiculo::totalDeUsos)
                .sum();
    }

    /**
     * Recupera o montante total arrecadado por um veículo específico.
     *
     * @param placa A placa do veículo.
     * @return O montante total arrecadado pelo veículo, ou 0 se o veículo não for encontrado.
     */
    public double arrecadadoPorVeiculo(String placa) {

        Veiculo veiculo = possuiVeiculo(placa);

        if (veiculo != null) {
            return veiculo.totalArrecadado();
        }

        return 0;

    }

    /**
     * Calcula o montante total arrecadado por todos os veículos do cliente.
     *
     * @return O montante total arrecadado por todos os veículos.
     */
    public double arrecadadoTotal() {
        return this.veiculos.stream()
                .mapToDouble(Veiculo::totalArrecadado)
                .sum();
    }

    /**
     * Calcula o montante arrecadado por todos os veículos do cliente em um determinado mês.
     *
     * @param mes O mês a ser considerado.
     * @return O montante total arrecadado no mês especificado.
     */
    public double arrecadadoNoMes(int mes) {
        return this.veiculos.stream()
                .mapToDouble(veiculo -> veiculo.arrecadadoNoMes(mes))
                .sum();
    }

    /**
     * Recupera o histórico de uso de vaga do cliente em todos os seus veículos.
     *
     * @param de data inicial de entrada
     * @param ate data final de entrada
     * @return O histórico de uso de vaga do cliente em todos os seus veículos.
     */
    public List<UsoDeVaga> historico(LocalDate de, LocalDate ate) {

        Objects.requireNonNull(de);
        Objects.requireNonNull(ate);

        return this.veiculos.stream()
                .map(veiculo -> veiculo.historico(de, ate))
                .flatMap(List::stream)
                .toList();

    }

}

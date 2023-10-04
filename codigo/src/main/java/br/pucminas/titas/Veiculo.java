package br.pucminas.titas;

public class Veiculo {

	private final String placa;
	private final UsoDeVaga[] usos;

	/**
	 * Cria um novo veículo com a placa informada.
	 *
	 * @param placa Placa do veículo
	 */
	public Veiculo(String placa) {
		this.placa = placa;
		this.usos = new UsoDeVaga[1000];
	}

	/**
	 * Estaciona o veículo na vaga informada.
	 *
	 * @param vaga Vaga onde o veículo será estacionado
	 */
	public void estacionar(Vaga vaga) {

		for(int i = 0; i < this.usos.length; i++) {

			if(this.usos[i] == null) {
				this.usos[i] = new UsoDeVaga(vaga);
				break;
			}

		}

	}

	/**
	 * Dispara {@see UsoDeVaga#sair()} no último uso de vaga do veículo.
	 *
	 * @return valor pago pelo veículo.
	 */
	public double sair() {
		return this.usos[this.usos.length - 1].sair();
	}

	/**
	 * Retorna o total arrecadado com o veículo.
	 *
	 * @return total arrecadado com o veículo.
	 */
	public double totalArrecadado() {

		double totalArrecadado = 0;

		for(UsoDeVaga usoDeVaga : this.usos) {
			totalArrecadado += usoDeVaga.valorPago();
		}

		return totalArrecadado;

	}

	/**
	 * Retorna o total arrecadado com o veículo no mês informado.
	 *
	 * @param mes Mês a ser consultado
	 * @return total arrecadado com o veículo no mês informado.
	 */
	public double arrecadadoNoMes(int mes) {

		double arrecadadoNoMes = 0;

		for(UsoDeVaga usoDeVaga : this.usos) {

			if(usoDeVaga.getEntrada().getMonthValue() == mes) {
				arrecadadoNoMes += usoDeVaga.valorPago();
			}

		}

		return arrecadadoNoMes;

	}

	/**
	 * Retorna o total de usos do veículo.
	 *
	 * @return total de usos do veículo.
	 */
	public int totalDeUsos() {
		return this.usos.length;
	}

}
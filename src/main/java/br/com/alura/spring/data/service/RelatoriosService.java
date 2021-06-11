package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository funRepository;

	public RelatoriosService(FuncionarioRepository funRepository) {
		this.funRepository = funRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Qual acao de cargo deseja executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionário nome");
			System.out.println("2 - Busca funcionário nome, data contratação e salário maior");
			System.out.println("3 - Busca funcionário data contratação");
			System.out.println("4 - Busca funcionário salário");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioDataContratacao(scanner);
				break;
			case 4:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Informe o nome que deseja buscar");
		String nome = scanner.next();
		
		List<Funcionario> list = funRepository.findByNome(nome);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual o nome pesquisado");
		String nome = scanner.next();
		
		System.out.println("Qual a data de contratação que deseja pesquisar");
		String data = scanner.next();
		
		System.out.println("Qual o salário que deseja pesquisar");
		double salario = scanner.nextDouble();
		
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funRepository
				.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Informe a data que deseja buscar");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funRepository.findDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionario: id: " + f.getId() 
		+ "\t| nome: " + f.getNome() + " \t\t| salario: R$ " + f.getSalario()));
	}
}

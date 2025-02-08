package models;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Main {

	public static void main(String[] args) {
		
		List<Funcionario> funcionarios = new ArrayList<>();
		
		funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
		funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
		funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
		funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
		funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 05), new BigDecimal("2234.68"), "Recepcionista"));
		funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
		funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
		funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
		funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
		funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
		
		funcionarios.remove(1);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		DecimalFormat df = new DecimalFormat("#,##0.00");
		for (Funcionario f : funcionarios) {
			System.out.println("Nome: " + f.getNome() + ", Data Nascimento: " + f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", Salário: R$" + df.format(f.getSalario()) + ", Função: " + f.getFuncao());
		}
		
		System.out.println("\nLista de funcionários com aumento de 10% de salário: ");
		funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")).setScale(2, RoundingMode.HALF_UP)));
		for (Funcionario f : funcionarios) {
			System.out.println("Nome: " + f.getNome() + ", Data Nascimento: " + f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +", Salário: R$" + df.format(f.getSalario()) + ", Função: " + f.getFuncao());
		}
		
		System.out.println("\nFuncionários agrupados pela função: ");
		Map<String, List<Funcionario>> agrupamentoFuncao = new HashMap<>();
		for(Funcionario f : funcionarios) {
			String funcao = f.getFuncao();
			agrupamentoFuncao.computeIfAbsent(funcao, k -> new ArrayList<>()).add(f);
		}
		
		agrupamentoFuncao.forEach((funcao, listaFuncionarios) -> {
			System.out.println("Função: " + funcao);
			listaFuncionarios.forEach(funcionario -> System.out.println("\t" + funcionario.getNome()));
		});
		
		System.out.println("\nAniversariantes do mês de OUTUBRO e DEZEMBRO: ");
		for(Funcionario f : funcionarios) {
			int mesAniversario = f.getDataNascimento().getMonthValue();
			if(mesAniversario == 10 || mesAniversario == 12) {
				System.out.println(f.getNome());
			}
		}
		
		System.out.println("\nColaborador mais velho da empresa: ");
		Funcionario maisVelho = null;
		for(Funcionario f : funcionarios) {
			if(maisVelho == null || f.getDataNascimento().isBefore(maisVelho.getDataNascimento())) {
				maisVelho = f;
			}
		}
		if(maisVelho != null) {
			int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
			System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade);
		}
		
		System.out.println("\nLista de colaboradores ordenados de forma alfabética: ");
		funcionarios.sort((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()));
		for(Funcionario f : funcionarios) {
			System.out.println("Nome: " + f.getNome() + ", Data Nascimento: " + f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +", Salário: R$" + df.format(f.getSalario()) + ", Função: " + f.getFuncao());
		}
		
		BigDecimal totalSalarioEmpresa = BigDecimal.ZERO;
		for(Funcionario f : funcionarios) {
			totalSalarioEmpresa = totalSalarioEmpresa.add(f.getSalario());
		}
		System.out.println("\nTotal  dos salários: R$" + df.format(totalSalarioEmpresa));
		
		System.out.println("\nComparação dos salários dos funcinários em relação ao salário mínimo: ");
		for(Funcionario f : funcionarios) {
			BigDecimal salarioMinimo = new BigDecimal("1212.0");
			BigDecimal qtdSalarioMinimo = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
			System.out.println("Nome: " + f.getNome() + ", ganha " + qtdSalarioMinimo + " salarios mínimos");
		}
	}
}

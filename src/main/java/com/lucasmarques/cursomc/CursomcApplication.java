package com.lucasmarques.cursomc;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasmarques.cursomc.domain.Categoria;
import com.lucasmarques.cursomc.domain.Cidade;
import com.lucasmarques.cursomc.domain.Cliente;
import com.lucasmarques.cursomc.domain.Endereco;
import com.lucasmarques.cursomc.domain.Estado;
import com.lucasmarques.cursomc.domain.Produto;
import com.lucasmarques.cursomc.domain.enums.TipoCliente;
import com.lucasmarques.cursomc.repositories.CategoriaRepository;
import com.lucasmarques.cursomc.repositories.CidadeRepository;
import com.lucasmarques.cursomc.repositories.ClienteRepository;
import com.lucasmarques.cursomc.repositories.EnderecoRepository;
import com.lucasmarques.cursomc.repositories.EstadoRepository;
import com.lucasmarques.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.setProdutos(List.of(p1,p2,p3));
		cat2.setProdutos(List.of(p2));
		
		p1.setCategorias(List.of(cat1));
		p2.setCategorias(List.of(cat1, cat2));
		p3.setCategorias(List.of(cat1));
		
		categoriaRepository.saveAll(List.of(cat1, cat2));
		produtoRepository.saveAll(List.of(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.setCidades(List.of(c1));
		est2.setCidades(List.of(c2,c3));
		
		estadoRepository.saveAll(List.of(est1, est2));
		cidadeRepository.saveAll(List.of(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.setTelefones(Set.of("27363323", "9838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.setEnderecos(List.of(e1, e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(List.of(e1, e2));
	}

}

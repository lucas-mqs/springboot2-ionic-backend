package com.lucasmarques.cursomc;

import java.text.SimpleDateFormat;
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
import com.lucasmarques.cursomc.domain.ItemPedido;
import com.lucasmarques.cursomc.domain.Pagamento;
import com.lucasmarques.cursomc.domain.PagamentoComBoleto;
import com.lucasmarques.cursomc.domain.PagamentoComCartao;
import com.lucasmarques.cursomc.domain.Pedido;
import com.lucasmarques.cursomc.domain.Produto;
import com.lucasmarques.cursomc.domain.enums.EstadoPagamento;
import com.lucasmarques.cursomc.domain.enums.TipoCliente;
import com.lucasmarques.cursomc.repositories.CategoriaRepository;
import com.lucasmarques.cursomc.repositories.CidadeRepository;
import com.lucasmarques.cursomc.repositories.ClienteRepository;
import com.lucasmarques.cursomc.repositories.EnderecoRepository;
import com.lucasmarques.cursomc.repositories.EstadoRepository;
import com.lucasmarques.cursomc.repositories.ItemPedidoRepository;
import com.lucasmarques.cursomc.repositories.PagamentoRepository;
import com.lucasmarques.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.setPedidos(List.of(ped1, ped2));
		
		pedidoRepository.saveAll(List.of(ped1, ped2));
		pagamentoRepository.saveAll(List.of(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.setItens(Set.of(ip1, ip2));
		ped2.setItens(Set.of(ip3));
		
		p1.setItens(Set.of(ip1));
		p2.setItens(Set.of(ip3));
		p3.setItens(Set.of(ip2));
		
		itemPedidoRepository.saveAll(List.of(ip1, ip2, ip3));
	}

}

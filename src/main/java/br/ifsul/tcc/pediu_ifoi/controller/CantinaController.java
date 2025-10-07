package br.ifsul.tcc.pediu_ifoi.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import br.ifsul.tcc.pediu_ifoi.domain.dto.CantinaDTO;
import br.ifsul.tcc.pediu_ifoi.domain.dto.ProdutoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.domain.entity.ItemPedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.service.CantinaService;
import br.ifsul.tcc.pediu_ifoi.service.PedidoService;
import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@RequestMapping("/cantina")
public class CantinaController {

    @Autowired
    private CantinaService cantinaService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/cadastro_cantina")
    public String cadastrarCantina() {
        System.out.println("-> Cadastro de Cantina acessado");
        return "/cantina/cadastro_cantina";
    }

    @PostMapping(value = "/cadastro_cantina", consumes = "application/x-www-form-urlencoded")
    public String cadastrarCantina(@Valid @ModelAttribute Cantina cantina, BindingResult bindingResult,
            Model model) {
        System.out.println("-> Iniciando cadastro de cantina");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Dados inválidos no cadastro.");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/cantina/cadastro_cantina";
        }

        try {
            cantina = cantinaService.salvarCantina(cantina);
            System.out.println(cantina);

            System.out.println("-> Cadastro de Cantina realizado com sucesso");
        } catch (Exception e) {
            System.out.println("-> Erro ao cadastrar Cantina: " + e.getMessage());
            model.addAttribute("registrationError", e.getMessage());
            return "/cantina/cadastro_cantina";
        }

        return "/cantina/login_cantina";
    }

    @GetMapping("/login_cantina")
    public String loginCantina() {
        System.out.println("-> Acessando tela de login de Cantina");
        return "/cantina/login_cantina";
    }

    @PostMapping("/login_cantina")
    public String loginCantina(@Valid @ModelAttribute CantinaDTO cantinaDTO, BindingResult bindingResult,
            Model model, HttpServletResponse response) {

        System.out.println("-> Iniciando login de cantina");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Dados inválidos no login.");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/cantina/login_cantina";
        }

        try {
            Cantina cantina = cantinaService.login(cantinaDTO);
            System.out.println(cantina);

            // Gerar token e salvar no cookie
            String token = cantinaService.generateToken(cantina);
            Cookie cookie = new Cookie("cantina_token", token);
            cookie.setMaxAge(5 * 60); // 5 minutos
            cookie.setPath("/");
            response.addCookie(cookie);

            System.out.println("-> Login de Cantina realizado com sucesso");
            return "redirect:/cantina/home_cantina";
        } catch (Exception e) {
            System.out.println("-> Erro ao realizar login de Cantina: " + e.getMessage());
            model.addAttribute("loginError", "Login ou senha inválidos");
        }

        return "/cantina/login_cantina";
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("cantina_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token != null && cantinaService.isTokenValid(token);
    }

    @GetMapping("/home_cantina")
    public String homeCantina(HttpServletRequest request, Model model) {
        System.out.println("-> Acessando home da Cantina");
        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }

        model.addAttribute("pedidosPendentes", pedidoService.listarPedidosPendentes());
        model.addAttribute("pedidosEmAndamento", pedidoService.listarPedidosEmAndamento());
        model.addAttribute("pedidosFinalizados", pedidoService.listarPedidosFinalizados());
        System.out.println("-> Pedidos pendentes e em andamento carregados com sucesso.");

        return "/cantina/home_cantina";
    }

    @GetMapping("/cadastrar_produto")
    public String cadastrarProduto(HttpServletRequest request) {
        System.out.println("-> Acessando tela de cadastro de produto");
        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }
        return "/cantina/cadastrar_produto";
    }

    @PostMapping("/cadastrar_produto")
    public String cadastrarProduto(@Valid @ModelAttribute ProdutoDTO produtoDTO, BindingResult bindingResult,
            Model model, HttpServletRequest request) {

        System.out.println("-> Iniciando cadastro de produto");
        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }

        if (bindingResult.hasErrors()) {
            System.err.println("-> Erros de validação encontrados.");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/cantina/cadastrar_produto";
        }

        try {
            System.out.println(produtoDTO);
            Produto produto = new Produto(null, produtoDTO.nome(), produtoDTO.preco(), true);
            produto = produtoService.cadastrarProduto(produto);
            return "redirect:/cantina/listar_produtos";

        } catch (Exception e) {
            System.out.println("-> Erro ao cadastrar Cantina: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar Cantina");
        }
    }

    @GetMapping("/listar_produtos")
    public String listarProdutos(Model model, HttpServletRequest request) {
        System.out.println("-> Acessando tela de listagem de produtos");
        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }

        try {
            System.out.println("-> Buscando produtos");
            List<Produto> produtos = produtoService.listarProdutos();
            System.out.println("-> Produtos encontrados: " + produtos.size());
            model.addAttribute("produtos", produtos);
            return "/cantina/listar_produtos";
        } catch (Exception e) {
            System.out.println("-> Erro ao listar produtos: " + e.getMessage());
            throw new RuntimeException("Erro ao listar produtos");
        }
    }

    @PostMapping("/atualizar_produto/{id}")
    public String atualizarProduto(@PathVariable Long id,
            @ModelAttribute ProdutoDTO produtoDTO,
            Model model,
            HttpServletRequest request) {
        System.out.println("-> Iniciando atualização de produto " + id);

        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }

        try {
            produtoService.atualizarProduto(id, produtoDTO);
            System.out.println("-> Produto atualizado com sucesso");

            return "redirect:/cantina/listar_produtos";
        } catch (Exception e) {
            System.out.println("-> Erro ao atualizar produto: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar produto");
        }
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, org.springframework.beans.TypeMismatchException.class })
    public String handleValidationException(Exception ex, Model model, HttpServletRequest request) {
        String errorMsg = "Erro ao atualizar produto: ";
        if (ex instanceof MethodArgumentNotValidException) {
            errorMsg += "Verifique se o nome é texto e o preço é numérico.";
        } else if (ex instanceof org.springframework.beans.TypeMismatchException) {
            errorMsg += "O campo preço deve ser numérico e o nome deve ser texto.";
        } else {
            errorMsg += "Dados inválidos.";
        }
        model.addAttribute("alertError", errorMsg);

        // Recarrega a lista de produtos para mostrar na tela
        List<Produto> produtos = produtoService.listarProdutos();
        model.addAttribute("produtos", produtos);

        return "/cantina/listar_produtos";
    }

    @PostMapping("/remover_produto/{id}")
    public String removerProduto(@PathVariable Long id, HttpServletRequest request) {
        System.out.println("-> Iniciando remoção de produto " + id);

        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }

        try {
            produtoService.removerProduto(id);
            System.out.println("-> Produto removido com sucesso");
            return "redirect:/cantina/listar_produtos";
        } catch (Exception e) {
            System.out.println("-> Erro ao remover produto: " + e.getMessage());
            throw new RuntimeException("Erro ao remover produto");
        }
    }

    @GetMapping("/pedido/{id}/itens")
    @ResponseBody
    public List<ItemPedido> getItensPedido(@PathVariable Long id) {
        System.out.println("-> Buscando itens do pedido " + id);
        Pedido pedido = pedidoService.findById(id);
        System.out.println("-> Itens encontrados: " + pedido.getItensPedido().size());
        return pedido.getItensPedido();
    }

    @GetMapping("/pedidos_cantina")
    public String listarTodosPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodosPedidos());
        return "cantina/pedidos_cantina";
    }
}

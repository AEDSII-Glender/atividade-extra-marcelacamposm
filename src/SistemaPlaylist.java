import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.function.Predicate;

public class SistemaPlaylist {
    
    private static Scanner teclado;
    private static ABB<Integer, Musica> buscadorPorId;        // Árvore com todas as músicas disponíveis (por ID)
    private static ABB<Double, Musica> buscadorPorDuracao;    // Árvore com todas as músicas disponíveis (por duração)
    private static ListaPlaylist playlist;                     // Playlist do usuário
    private static PilhaHistorico historico;                   // Histórico de reprodução
    
    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        buscadorPorId = new ABB<>();
        buscadorPorDuracao = new ABB<>();
        playlist = new ListaPlaylist();
        historico = new PilhaHistorico();
        
        limparTela();
        
        // Carrega as músicas disponíveis do arquivo
        if (!carregarMusicasDisponiveis()) {
            System.out.println("Erro ao carregar músicas. Encerrando...");
            return;
        }
        
        System.out.println("Músicas carregadas com sucesso!");
        System.out.println("Total de músicas disponíveis: " + buscadorPorId.tamanho());
        pausa();
        
        // Menu principal
        boolean continuar = true;
        while (continuar) {
            limparTela();
            continuar = exibirMenuPrincipal();
        }
        
        System.out.println("\nObrigado por usar o Sistema de Playlist!");
        teclado.close();
    }
    
    /**
     * Limpa a tela do console.
     */
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Pausa a execução esperando um enter.
     */
    private static void pausa() {
        System.out.println("\nPressione ENTER para continuar...");
        teclado.nextLine();
    }
    
    /**
     * Carrega as músicas disponíveis do arquivo "musicas".
     * @return true se carregou com sucesso, false caso contrário
     */
    private static boolean carregarMusicasDisponiveis() {
        try {
            File arquivo = new File("musicas");
            Scanner leitor = new Scanner(arquivo);
            
            if (!leitor.hasNextLine()) {
                leitor.close();
                return false;
            }
            
            // Primeira linha contém a quantidade de músicas
            int quantidade = Integer.parseInt(leitor.nextLine().trim());
            
            // Lê cada música
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                
                if (linha.isEmpty()) {
                    continue;
                }
                
                String[] partes = linha.split(";");
                
                if (partes.length != 4) {
                    continue;
                }
                
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    String artista = partes[2].trim();
                    double duracao = Double.parseDouble(partes[3].trim());
                    
                    Musica musica = new Musica(id, titulo, artista, duracao);
                    
                    // Adiciona nas duas árvores de busca
                    buscadorPorId.inserir(id, musica);
                    buscadorPorDuracao.inserir(duracao, musica);
                    
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao processar linha: " + linha);
                }
            }
            
            leitor.close();
            return true;
            
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo 'musicas' não encontrado!");
            return false;
        }
    }
    
    /**
     * Exibe o menu principal e processa a escolha do usuário.
     * @return true para continuar, false para sair
     */
    private static boolean exibirMenuPrincipal() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE PLAYLIST - SPOTIFY      ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("1. Adicionar música à playlist");
        System.out.println("2. Remover música da playlist");
        System.out.println("3. Exibir playlist");
        System.out.println("4. Ordenar playlist");
        System.out.println("5. Reproduzir playlist na ordem");
        System.out.println("6. Reproduzir música aleatoriamente");
        System.out.println("7. Exibir histórico de reprodução");
        System.out.println("8. Exibir músicas disponíveis");
        System.out.println("0. Sair");
        System.out.println();
        System.out.print("Escolha uma opção: ");
        
        String opcao = teclado.nextLine().trim();
        
        switch (opcao) {
            case "1":
                adicionarMusicaPlaylist();
                break;
            case "2":
                removerMusicaPlaylist();
                break;
            case "3":
                exibirPlaylist();
                break;
            case "4":
                ordenarPlaylist();
                break;
            case "5":
                reproduzirNaOrdem();
                break;
            case "6":
                reproduzirAleatoriamente();
                break;
            case "7":
                exibirHistorico();
                break;
            case "8":
                exibirMusicasDisponiveis();
                break;
            case "0":
                return false;
            default:
                System.out.println("\nOpção inválida!");
                pausa();
        }
        
        return true;
    }
    
    /**
     * Adiciona uma música à playlist.
     */
    private static void adicionarMusicaPlaylist() {
        limparTela();
        System.out.println("=== ADICIONAR MÚSICA À PLAYLIST ===\n");
        
        System.out.print("Digite o ID da música que deseja adicionar (ou 0 para ver a lista): ");
        String input = teclado.nextLine().trim();
        
        if (input.equals("0")) {
            exibirMusicasDisponiveis();
            System.out.print("\nDigite o ID da música que deseja adicionar: ");
            input = teclado.nextLine().trim();
        }
        
        try {
            int id = Integer.parseInt(input);
            
            // Busca a música no buscador
            Musica musica = buscadorPorId.pesquisar(id);
            
            if (musica == null) {
                System.out.println("\nMúsica não encontrada!");
            } else {
                // Adiciona à playlist
                if (playlist.adicionar(musica)) {
                    System.out.println("\nMúsica adicionada com sucesso!");
                    System.out.println(musica.toString());
                    
                    // Exibe a playlist atualizada
                    System.out.println("\n" + playlist.exibir());
                } else {
                    System.out.println("\nMúsica já está na playlist!");
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\nID inválido!");
        } catch (NoSuchElementException e) {
            System.out.println("\nMúsica não encontrada!");
        }
        
        pausa();
    }
    
    /**
     * Remove uma música da playlist.
     */
    private static void removerMusicaPlaylist() {
        limparTela();
        System.out.println("=== REMOVER MÚSICA DA PLAYLIST ===\n");
        
        if (playlist.vazia()) {
            System.out.println("Playlist vazia!");
            pausa();
            return;
        }
        
        System.out.println(playlist.exibir());
        
        System.out.println("\nRemover por:");
        System.out.println("1. ID");
        System.out.println("2. Título");
        System.out.print("\nEscolha: ");
        
        String opcao = teclado.nextLine().trim();
        
        if (opcao.equals("1")) {
            System.out.print("Digite o ID da música a remover: ");
            try {
                int id = Integer.parseInt(teclado.nextLine().trim());
                Musica removida = playlist.remover(id);
                
                if (removida != null) {
                    System.out.println("\nMúsica removida com sucesso!");
                    System.out.println(removida.toString());
                    
                    // Exibe a playlist atualizada
                    System.out.println("\n" + playlist.exibir());
                } else {
                    System.out.println("\nMúsica não encontrada na playlist!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nID inválido!");
            }
        } else if (opcao.equals("2")) {
            System.out.print("Digite o título da música a remover: ");
            String titulo = teclado.nextLine().trim();
            Musica removida = playlist.removerPorTitulo(titulo);
            
            if (removida != null) {
                System.out.println("\nMúsica removida com sucesso!");
                System.out.println(removida.toString());
                
                // Exibe a playlist atualizada
                System.out.println("\n" + playlist.exibir());
            } else {
                System.out.println("\nMúsica não encontrada na playlist!");
            }
        } else {
            System.out.println("\nOpção inválida!");
        }
        
        pausa();
    }
    
    /**
     * Exibe a playlist atual.
     */
    private static void exibirPlaylist() {
        limparTela();
        System.out.println(playlist.exibir());
        pausa();
    }
    
    /**
     * Filtra músicas da playlist usando Predicate.
     */
    /**
     * Ordena a playlist.
     */
    private static void ordenarPlaylist() {
        limparTela();
        System.out.println("=== ORDENAR PLAYLIST ===\n");
        
        if (playlist.vazia()) {
            System.out.println("Playlist vazia!");
            pausa();
            return;
        }
        
        System.out.println("Ordenar por:");
        System.out.println("1. ID");
        System.out.println("2. Título");
        System.out.println("3. Duração");
        System.out.print("\nEscolha o critério: ");
        
        String criterio = teclado.nextLine().trim();
        
        System.out.println("\nMétodo de ordenação:");
        System.out.println("1. BubbleSort (iterativo)");
        System.out.println("2. QuickSort (particionamento)");
        System.out.print("\nEscolha o método: ");
        
        String metodo = teclado.nextLine().trim();
        
        System.out.println("\nOrdenando...");
        
        if (criterio.equals("1")) {
            if (metodo.equals("1")) {
                playlist.ordenarPorIdBubbleSort();
            } else if (metodo.equals("2")) {
                playlist.ordenarPorIdQuickSort();
            } else {
                System.out.println("Método inválido!");
                pausa();
                return;
            }
            System.out.println("Playlist ordenada por ID!");
        } else if (criterio.equals("2")) {
            if (metodo.equals("1")) {
                playlist.ordenarPorTituloBubbleSort();
            } else if (metodo.equals("2")) {
                playlist.ordenarPorTituloQuickSort();
            } else {
                System.out.println("Método inválido!");
                pausa();
                return;
            }
            System.out.println("Playlist ordenada por título!");
        } else if (criterio.equals("3")) {
            if (metodo.equals("1")) {
                playlist.ordenarPorDuracaoBubbleSort();
            } else if (metodo.equals("2")) {
                playlist.ordenarPorDuracaoQuickSort();
            } else {
                System.out.println("Método inválido!");
                pausa();
                return;
            }
            System.out.println("Playlist ordenada por duração!");
        } else {
            System.out.println("Critério inválido!");
            pausa();
            return;
        }
        
        // Exibe a playlist ordenada
        System.out.println("\n" + playlist.exibir());
        
        pausa();
    }
    
    /**
     * Reproduz a playlist na ordem.
     */
    private static void reproduzirNaOrdem() {
        limparTela();
        System.out.println("=== REPRODUZIR NA ORDEM ===\n");
        
        if (playlist.vazia()) {
            System.out.println("Playlist vazia!");
            pausa();
            return;
        }
        
        System.out.println("Iniciar reprodução do:");
        System.out.println("1. Início da playlist");
        System.out.println("2. Fim da playlist");
        System.out.print("\nEscolha: ");
        
        String opcao = teclado.nextLine().trim();
        
        if (opcao.equals("1")) {
            playlist.iniciarDoInicio();
        } else if (opcao.equals("2")) {
            playlist.iniciarDoFim();
        } else {
            System.out.println("Opção inválida!");
            pausa();
            return;
        }
        
        boolean continuar = true;
        
        while (continuar) {
            limparTela();
            
            Musica atual = playlist.getMusicaAtual();
            
            if (atual == null) {
                System.out.println("Nenhuma música selecionada!");
                break;
            }
            
            System.out.println("♪ Reproduzindo: " + atual.toStringReproduzir() + " ♪\n");
            System.out.println(atual.toString());
            
            // Adiciona ao histórico
            historico.empilhar(atual);
            
            System.out.println("\n1. Reproduzir próxima");
            System.out.println("2. Reproduzir anterior");
            System.out.println("0. Parar reprodução");
            System.out.print("\nEscolha: ");
            
            String escolha = teclado.nextLine().trim();
            
            if (escolha.equals("1")) {
                if (!playlist.proximaMusica()) {
                    System.out.println("\nPlaylist finalizada. Deseja reiniciar?");
                    System.out.println("1. Sim, do início");
                    System.out.println("2. Sim, do fim");
                    System.out.println("0. Não");
                    System.out.print("\nEscolha: ");
                    
                    String reiniciar = teclado.nextLine().trim();
                    
                    if (reiniciar.equals("1")) {
                        playlist.iniciarDoInicio();
                    } else if (reiniciar.equals("2")) {
                        playlist.iniciarDoFim();
                    } else {
                        continuar = false;
                    }
                }
            } else if (escolha.equals("2")) {
                if (!playlist.musicaAnterior()) {
                    System.out.println("\nEstá no início da playlist!");
                    pausa();
                }
            } else if (escolha.equals("0")) {
                continuar = false;
            } else {
                System.out.println("\nOpção inválida!");
                pausa();
            }
        }
        
        playlist.resetarNavegacao();
        System.out.println("\nReprodução encerrada!");
        pausa();
    }
    
    /**
     * Reproduz músicas aleatoriamente (usuário escolhe).
     */
    private static void reproduzirAleatoriamente() {
        limparTela();
        System.out.println("=== REPRODUZIR ALEATORIAMENTE ===\n");
        
        if (playlist.vazia()) {
            System.out.println("Playlist vazia!");
            pausa();
            return;
        }
        
        boolean continuar = true;
        
        while (continuar) {
            limparTela();
            System.out.println(playlist.exibir());
            
            System.out.println("\n1. Reproduzir música (por ID)");
            System.out.println("2. Voltar reprodução (histórico)");
            System.out.println("0. Parar reprodução");
            System.out.print("\nEscolha: ");
            
            String opcao = teclado.nextLine().trim();
            
            if (opcao.equals("1")) {
                System.out.print("\nDigite o ID da música: ");
                try {
                    int id = Integer.parseInt(teclado.nextLine().trim());
                    
                    if (playlist.irParaMusica(id)) {
                        Musica musica = playlist.getMusicaAtual();
                        
                        limparTela();
                        System.out.println("♪ Reproduzindo: " + musica.toStringReproduzir() + " ♪\n");
                        System.out.println(musica.toString());
                        
                        // Adiciona ao histórico
                        historico.empilhar(musica);
                        
                        pausa();
                    } else {
                        System.out.println("\nMúsica não encontrada na playlist!");
                        pausa();
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("\nID inválido!");
                    pausa();
                }
                
            } else if (opcao.equals("2")) {
                if (historico.vazia()) {
                    System.out.println("\nHistórico vazio!");
                    pausa();
                } else {
                    try {
                        Musica ultima = historico.desempilhar();
                        
                        limparTela();
                        System.out.println("♪ Voltando para: " + ultima.toStringReproduzir() + " ♪\n");
                        System.out.println(ultima.toString());
                        
                        pausa();
                    } catch (NoSuchElementException e) {
                        System.out.println("\nHistórico vazio!");
                        pausa();
                    }
                }
                
            } else if (opcao.equals("0")) {
                continuar = false;
            } else {
                System.out.println("\nOpção inválida!");
                pausa();
            }
        }
        
        playlist.resetarNavegacao();
        System.out.println("\nReprodução encerrada!");
        pausa();
    }
    
    /**
     * Exibe o histórico de reprodução.
     */
    private static void exibirHistorico() {
        limparTela();
        System.out.println(historico.exibir());
        pausa();
    }
    
    /**
     * Exibe todas as músicas disponíveis.
     */
    private static void exibirMusicasDisponiveis() {
        limparTela();
        System.out.println("=== MÚSICAS DISPONÍVEIS ===\n");
        System.out.println("Total: " + buscadorPorId.tamanho() + " músicas\n");
        System.out.println(buscadorPorId.caminhamentoEmOrdem());
        pausa();
    }
}

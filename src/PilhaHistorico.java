import java.util.NoSuchElementException;

public class PilhaHistorico {
    
    private class NoPilha {
        Musica musica;
        NoPilha proximo;
        
        public NoPilha(Musica musica) {
            this.musica = musica;
            this.proximo = null;
        }
    }
    
    private NoPilha topo;
    private int tamanho;
    
    /**
     * Construtor da pilha de histórico vazia.
     */
    public PilhaHistorico() {
        this.topo = null;
        this.tamanho = 0;
    }
    
    /**
     * Verifica se a pilha está vazia.
     * @return true se vazia, false caso contrário
     */
    public boolean vazia() {
        return topo == null;
    }
    
    /**
     * Retorna o tamanho da pilha.
     * @return número de músicas no histórico
     */
    public int tamanho() {
        return tamanho;
    }
    
    /**
     * Empilha uma música no topo do histórico.
     * @param musica Música a ser adicionada ao histórico
     */
    public void empilhar(Musica musica) {
        if (musica == null) {
            return;
        }
        
        NoPilha novoNo = new NoPilha(musica);
        novoNo.proximo = topo;
        topo = novoNo;
        tamanho++;
    }
    
    /**
     * Desempilha a música do topo do histórico.
     * @return Música removida do topo
     * @throws NoSuchElementException se a pilha estiver vazia
     */
    public Musica desempilhar() {
        if (vazia()) {
            throw new NoSuchElementException("Histórico vazio!");
        }
        
        Musica musicaRemovida = topo.musica;
        topo = topo.proximo;
        tamanho--;
        
        return musicaRemovida;
    }
    
    /**
     * Consulta a música no topo do histórico sem removê-la.
     * @return Música no topo
     * @throws NoSuchElementException se a pilha estiver vazia
     */
    public Musica topo() {
        if (vazia()) {
            throw new NoSuchElementException("Histórico vazio!");
        }
        
        return topo.musica;
    }
    
    /**
     * Limpa todo o histórico.
     */
    public void limpar() {
        topo = null;
        tamanho = 0;
    }
    
    /**
     * Exibe o histórico de reprodução.
     * @return String formatada com o histórico (do mais recente ao mais antigo)
     */
    public String exibir() {
        if (vazia()) {
            return "Histórico vazio!";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== HISTÓRICO DE REPRODUÇÃO ===\n");
        sb.append(String.format("Total de reproduções: %d\n\n", tamanho));
        
        NoPilha atual = topo;
        int posicao = 1;
        
        while (atual != null) {
            sb.append(String.format("%d. %s\n", posicao, atual.musica.toString()));
            atual = atual.proximo;
            posicao++;
        }
        
        return sb.toString();
    }
    
    /**
     * Exibe apenas as últimas N músicas do histórico.
     * @param quantidade Quantidade de músicas a exibir
     * @return String formatada com as últimas N músicas
     */
    public String exibir(int quantidade) {
        if (vazia()) {
            return "Histórico vazio!";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== HISTÓRICO DE REPRODUÇÃO (ÚLTIMAS ").append(quantidade).append(" MÚSICAS) ===\n\n");
        
        NoPilha atual = topo;
        int posicao = 1;
        int contador = 0;
        
        while (atual != null && contador < quantidade) {
            sb.append(String.format("%d. %s\n", posicao, atual.musica.toString()));
            atual = atual.proximo;
            posicao++;
            contador++;
        }
        
        return sb.toString();
    }
}

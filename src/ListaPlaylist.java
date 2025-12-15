public class ListaPlaylist {
    
    private NoPlaylist inicio;
    private NoPlaylist fim;
    private int tamanho;
    private NoPlaylist noAtual;
    
    public ListaPlaylist() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
        this.noAtual = null;
    }
    
    public boolean vazia() {
        return tamanho == 0;
    }
    
    public int tamanho() {
        return tamanho;
    }
    
    // Adiciona música ao final da playlist
    public boolean adicionar(Musica musica) {
        if (musica == null) {
            return false;
        }
        
        // Verifica se a música já está na playlist
        if (buscar(musica.getId()) != null) {
            return false; // Música já existe na playlist
        }
        
        NoPlaylist novoNo = new NoPlaylist(musica.getId(), musica);
        
        if (vazia()) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.setProximo(novoNo);
            novoNo.setAnterior(fim);
            fim = novoNo;
        }
        
        tamanho++;
        return true;
    }
    
    // Busca música por ID
    public Musica buscar(int id) {
        NoPlaylist atual = inicio;
        
        while (atual != null) {
            if (atual.getChave() == id) {
                return atual.getMusica();
            }
            atual = atual.getProximo();
        }
        
        return null;
    }
    
    // Busca música por título
    public Musica buscarPorTitulo(String titulo) {
        NoPlaylist atual = inicio;
        
        while (atual != null) {
            if (atual.getMusica().getTitulo().equalsIgnoreCase(titulo)) {
                return atual.getMusica();
            }
            atual = atual.getProximo();
        }
        
        return null;
    }
    
    // Remove música por ID
    public Musica remover(int id) {
        NoPlaylist atual = inicio;
        
        while (atual != null) {
            if (atual.getChave() == id) {
                Musica musicaRemovida = atual.getMusica();
                
                // Remove o nó da lista
                if (atual == inicio && atual == fim) {
                    // Único elemento
                    inicio = null;
                    fim = null;
                } else if (atual == inicio) {
                    // Primeiro elemento
                    inicio = atual.getProximo();
                    inicio.setAnterior(null);
                } else if (atual == fim) {
                    // Último elemento
                    fim = atual.getAnterior();
                    fim.setProximo(null);
                } else {
                    // Elemento do meio
                    atual.getAnterior().setProximo(atual.getProximo());
                    atual.getProximo().setAnterior(atual.getAnterior());
                }
                
                tamanho--;
                
                // Ajusta noAtual se foi removido
                if (noAtual == atual) {
                    noAtual = null;
                }
                
                return musicaRemovida;
            }
            atual = atual.getProximo();
        }
        
        return null;
    }
    
    // Remove música por título
    public Musica removerPorTitulo(String titulo) {
        NoPlaylist atual = inicio;
        
        while (atual != null) {
            if (atual.getMusica().getTitulo().equalsIgnoreCase(titulo)) {
                return remover(atual.getChave());
            }
            atual = atual.getProximo();
        }
        
        return null;
    }
    
    // Exibe a playlist
    public String exibir() {
        if (vazia()) {
            return "Playlist vazia!";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== PLAYLIST ===\n");
        sb.append(String.format("Total de músicas: %d\n\n", tamanho));
        
        NoPlaylist atual = inicio;
        int posicao = 1;
        
        while (atual != null) {
            sb.append(String.format("%d. %s\n", posicao, atual.getMusica().toString()));
            atual = atual.getProximo();
            posicao++;
        }
        
        return sb.toString();
    }
    
    // BubbleSort por ID
    public void ordenarPorIdBubbleSort() {
        if (tamanho <= 1) {
            return;
        }
        
        boolean trocou;
        
        do {
            trocou = false;
            NoPlaylist atual = inicio;
            
            while (atual != null && atual.getProximo() != null) {
                if (atual.getMusica().getId() > atual.getProximo().getMusica().getId()) {
                    // Troca as músicas (não os nós)
                    Musica temp = atual.getMusica();
                    int tempChave = atual.getChave();
                    
                    atual.setMusica(atual.getProximo().getMusica());
                    atual.setChave(atual.getProximo().getChave());
                    
                    atual.getProximo().setMusica(temp);
                    atual.getProximo().setChave(tempChave);
                    
                    trocou = true;
                }
                atual = atual.getProximo();
            }
        } while (trocou);
    }
    
    // BubbleSort por título
    public void ordenarPorTituloBubbleSort() {
        if (tamanho <= 1) {
            return;
        }
        
        boolean trocou;
        
        do {
            trocou = false;
            NoPlaylist atual = inicio;
            
            while (atual != null && atual.getProximo() != null) {
                if (atual.getMusica().getTitulo().compareToIgnoreCase(atual.getProximo().getMusica().getTitulo()) > 0) {
                    // Troca as músicas
                    Musica temp = atual.getMusica();
                    int tempChave = atual.getChave();
                    
                    atual.setMusica(atual.getProximo().getMusica());
                    atual.setChave(atual.getProximo().getChave());
                    
                    atual.getProximo().setMusica(temp);
                    atual.getProximo().setChave(tempChave);
                    
                    trocou = true;
                }
                atual = atual.getProximo();
            }
        } while (trocou);
    }
    
    // BubbleSort por duração
    public void ordenarPorDuracaoBubbleSort() {
        if (tamanho <= 1) {
            return;
        }
        
        boolean trocou;
        
        do {
            trocou = false;
            NoPlaylist atual = inicio;
            
            while (atual != null && atual.getProximo() != null) {
                if (atual.getMusica().compareTo(atual.getProximo().getMusica()) > 0) {
                    // Troca as músicas
                    Musica temp = atual.getMusica();
                    int tempChave = atual.getChave();
                    
                    atual.setMusica(atual.getProximo().getMusica());
                    atual.setChave(atual.getProximo().getChave());
                    
                    atual.getProximo().setMusica(temp);
                    atual.getProximo().setChave(tempChave);
                    
                    trocou = true;
                }
                atual = atual.getProximo();
            }
        } while (trocou);
    }
    
    // QuickSort por ID
    public void ordenarPorIdQuickSort() {
        if (tamanho <= 1) {
            return;
        }
        
        Musica[] array = toArray();
        quickSortId(array, 0, tamanho - 1);
        fromArray(array);
    }
    
    // QuickSort por título
    public void ordenarPorTituloQuickSort() {
        if (tamanho <= 1) {
            return;
        }
        
        Musica[] array = toArray();
        quickSortTitulo(array, 0, tamanho - 1);
        fromArray(array);
    }
    
    // QuickSort por duração
    public void ordenarPorDuracaoQuickSort() {
        if (tamanho <= 1) {
            return;
        }
        
        Musica[] array = toArray();
        quickSortDuracao(array, 0, tamanho - 1);
        fromArray(array);
    }
    
    // Converte lista para array
    private Musica[] toArray() {
        Musica[] array = new Musica[tamanho];
        NoPlaylist atual = inicio;
        int i = 0;
        
        while (atual != null) {
            array[i++] = atual.getMusica();
            atual = atual.getProximo();
        }
        
        return array;
    }
    
    // Reconstrói lista a partir de array
    private void fromArray(Musica[] array) {
        inicio = null;
        fim = null;
        tamanho = 0;
        
        for (Musica musica : array) {
            adicionar(musica);
        }
    }
    
    // QuickSort recursivo por ID
    private void quickSortId(Musica[] array, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionarId(array, inicio, fim);
            quickSortId(array, inicio, indicePivo - 1);
            quickSortId(array, indicePivo + 1, fim);
        }
    }
    
    /**
     * Particiona o array para QuickSort por ID.
     */
    private int particionarId(Musica[] array, int inicio, int fim) {
        Musica pivo = array[fim];
        int i = inicio - 1;
        
        for (int j = inicio; j < fim; j++) {
            if (array[j].getId() <= pivo.getId()) {
                i++;
                Musica temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        Musica temp = array[i + 1];
        array[i + 1] = array[fim];
        array[fim] = temp;
        
        return i + 1;
    }
    
    // QuickSort recursivo por título
    private void quickSortTitulo(Musica[] array, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionarTitulo(array, inicio, fim);
            quickSortTitulo(array, inicio, indicePivo - 1);
            quickSortTitulo(array, indicePivo + 1, fim);
        }
    }
    
    // Particiona array por título
    private int particionarTitulo(Musica[] array, int inicio, int fim) {
        Musica pivo = array[fim];
        int i = inicio - 1;
        
        for (int j = inicio; j < fim; j++) {
            if (array[j].getTitulo().compareToIgnoreCase(pivo.getTitulo()) <= 0) {
                i++;
                Musica temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        Musica temp = array[i + 1];
        array[i + 1] = array[fim];
        array[fim] = temp;
        
        return i + 1;
    }
    
    // QuickSort recursivo por duração
    private void quickSortDuracao(Musica[] array, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionarDuracao(array, inicio, fim);
            quickSortDuracao(array, inicio, indicePivo - 1);
            quickSortDuracao(array, indicePivo + 1, fim);
        }
    }
    
    // Particiona array por duração
    private int particionarDuracao(Musica[] array, int inicio, int fim) {
        Musica pivo = array[fim];
        int i = inicio - 1;
        
        for (int j = inicio; j < fim; j++) {
            if (array[j].compareTo(pivo) <= 0) {
                i++;
                Musica temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        Musica temp = array[i + 1];
        array[i + 1] = array[fim];
        array[fim] = temp;
        
        return i + 1;
    }
    
    // ===== MÉTODOS PARA NAVEGAÇÃO DURANTE REPRODUÇÃO =====
    
    /**
     * Inicia a reprodução do início da playlist.
     */
    public void iniciarDoInicio() {
        noAtual = inicio;
    }
    
    /**
     * Inicia a reprodução do fim da playlist.
     */
    public void iniciarDoFim() {
        noAtual = fim;
    }
    
    // Retorna música atual
    public Musica getMusicaAtual() {
        return (noAtual != null) ? noAtual.getMusica() : null;
    }
    
    // Avança para próxima música
    public boolean proximaMusica() {
        if (noAtual != null && noAtual.getProximo() != null) {
            noAtual = noAtual.getProximo();
            return true;
        }
        return false;
    }
    
    // Retrocede para música anterior
    public boolean musicaAnterior() {
        if (noAtual != null && noAtual.getAnterior() != null) {
            noAtual = noAtual.getAnterior();
            return true;
        }
        return false;
    }
    
    // Define música atual por ID
    public boolean irParaMusica(int id) {
        NoPlaylist temp = inicio;
        
        while (temp != null) {
            if (temp.getChave() == id) {
                noAtual = temp;
                return true;
            }
            temp = temp.getProximo();
        }
        
        return false;
    }
    
    public boolean temProxima() {
        return (noAtual != null && noAtual.getProximo() != null);
    }
    
    public boolean temAnterior() {
        return (noAtual != null && noAtual.getAnterior() != null);
    }
    
    public void resetarNavegacao() {
        noAtual = null;
    }
}

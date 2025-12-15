public class NoPlaylist {
    
    private int chave;           // Chave: ID da música
    private Musica musica;       // Valor: objeto Musica
    private NoPlaylist proximo;  // Referência para o próximo nó
    private NoPlaylist anterior; // Referência para o nó anterior
    
    /**
     * Construtor do nó da lista duplamente encadeada.
     * @param chave ID da música (chave do par chave-valor)
     * @param musica Objeto Musica a ser armazenado
     */
    public NoPlaylist(int chave, Musica musica) {
        this.chave = chave;
        this.musica = musica;
        this.proximo = null;
        this.anterior = null;
    }
    
    // Getters e Setters
    public int getChave() {
        return chave;
    }
    
    public void setChave(int chave) {
        this.chave = chave;
    }
    
    public Musica getMusica() {
        return musica;
    }
    
    public void setMusica(Musica musica) {
        this.musica = musica;
    }
    
    public NoPlaylist getProximo() {
        return proximo;
    }
    
    public void setProximo(NoPlaylist proximo) {
        this.proximo = proximo;
    }
    
    public NoPlaylist getAnterior() {
        return anterior;
    }
    
    public void setAnterior(NoPlaylist anterior) {
        this.anterior = anterior;
    }
    
    @Override
    public String toString() {
        return musica.toString();
    }
}

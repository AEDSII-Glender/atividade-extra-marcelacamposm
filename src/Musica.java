public class Musica implements Comparable<Musica> {
    
    private int id;
    private String titulo;
    private String artista;
    private double duracao; // duração em minutos (formato decimal)
    
    /**
     * Construtor completo da música.
     * @param id Identificador único da música
     * @param titulo Título da música (mínimo 1 caractere)
     * @param artista Nome do artista (mínimo 1 caractere)
     * @param duracao Duração da música em minutos (deve ser positiva)
     * @throws IllegalArgumentException em caso de valores inválidos
     */
    public Musica(int id, String titulo, String artista, double duracao) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título da música não pode ser vazio!");
        }
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do artista não pode ser vazio!");
        }
        if (duracao <= 0.0) {
            throw new IllegalArgumentException("Duração da música deve ser positiva!");
        }
        
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.duracao = duracao;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public String getArtista() {
        return artista;
    }
    
    public double getDuracao() {
        return duracao;
    }
    
    /**
     * Formata a duração em minutos para o formato MM:SS
     * @return String no formato "MM:SS"
     */
    public String getDuracaoFormatada() {
        int minutos = (int) duracao;
        int segundos = (int) Math.round((duracao - minutos) * 60);
        return String.format("%d:%02d", minutos, segundos);
    }
    
    /**
     * Comparação por duração (critério primário) e ID (critério secundário)
     * @param outra Outra música para comparação
     * @return negativo se esta < outra; 0 se igual; positivo se esta > outra
     */
    @Override
    public int compareTo(Musica outra) {
        // Compara por duração primeiro
        int comparacaoDuracao = Double.compare(this.duracao, outra.duracao);
        
        if (comparacaoDuracao != 0) {
            return comparacaoDuracao;
        }
        
        // Se durações iguais, compara por ID
        return Integer.compare(this.id, outra.id);
    }
    
    /**
     * Representação em string da música
     * @return String formatada com informações da música
     */
    @Override
    public String toString() {
        return String.format("%d;%s;%s;%.2f", id, titulo, artista, duracao);
    }
    
    /**
     * Representação compacta para mensagem de reprodução
     * @return String formatada "Título - Artista"
     */
    public String toStringReproduzir() {
        return String.format("%s - %s", titulo, artista);
    }
}

package poo;

public abstract class Personagem {
    private String imagem; // Identificador da imagem
    private int energia;
    private boolean infectado;
    private boolean dano;
    private Celula celula;

    public Personagem(int energiaInicial, String imagemInicial, int linInicial, int colInicial) {
        this.imagem = imagemInicial;
        this.energia = energiaInicial;
        Jogo.getInstance().getCelula(linInicial, colInicial).setPersonagem(this);
        this.infectado = false;
        this.dano = false;
    }
    

    public int getEnergia() {
        return energia;
    }

    public void incrementaEnergia(int valor) {
        if (valor < 0)
            throw new IllegalArgumentException("Valor de energia invalido");
        energia += valor;
    }

    public void diminuiEnergia(int valor) {
        if (valor < 0)
            throw new IllegalArgumentException("Valor de energia invalido");
        energia -= valor;
        if (energia < 0) {
            energia = 0;
        }
    }

    public boolean infectado() {
        return infectado;
    }
    public boolean dano() {
        return dano;
    }
    
    public void danificado (){
        dano = true;
    }
    public void infecta() {
        infectado = true;
    }

   
    public void cura() {
        infectado = false;
    }

    public boolean estaVivo() {
        return getEnergia() > 0;
    }
    public boolean ZumbiVivo() {
        return getEnergia() > 0;
    }
    public boolean ZumbiMorto() {
        return getEnergia() == 0;
    }
    public boolean estaMorto() {
        return getEnergia() == 0;
    }

    public void setEnergia(int energia){
        this.energia = energia;
    }
    public String getImage() {
        return imagem;
    }

    public void setImage(String imagem) {
        this.imagem = imagem;
    }

    public Celula getCelula() {
        return celula;
    }

    public void setCelula(Celula celula) {
        this.celula = celula;
    }

    // Define próximo movimento
    public abstract void atualizaPosicao();

    // Verifica possiveis atualizações de estado a cada passo
    public abstract void verificaEstado();

    // Define como o personagem influencia os vizinhos
    // Toda vez que chega em uma célula analisa os vizinhos
    // e influencia os mesmos
    public abstract void influenciaVizinhos();
}

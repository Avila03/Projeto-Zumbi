package poo;

public class Bobao extends Personagem {
    public Bobao(int linInicial, int colInicial) {
        super(20, "Normal", linInicial, colInicial);
    }

    @Override
    public void infecta() {
        if (this.infectado()) {
            return;
        }
        super.infecta();
        this.setImage("Infectado");
        this.getCelula().setImageFromPersonagem();
    }

    @Override
    public void atualizaPosicao() {
        int dirLin = Jogo.getInstance().aleatorio(3) - 1;
        int dirCol = Jogo.getInstance().aleatorio(3) - 1;
        int oldLin = this.getCelula().getLinha();
        int oldCol = this.getCelula().getColuna();
        int lin = oldLin + dirLin;
        int col = oldCol + dirCol;
        if (this.infectado()) {
            return;
        }
        if (!this.estaVivo()) {
            return;
        }
       
        if (lin < 0)
            lin = 0;
        if (lin >= Jogo.NLIN)
            lin = Jogo.NLIN - 1;
        if (col < 0)
            col = 0;
        if (col >= Jogo.NCOL)
            col = Jogo.NCOL - 1;
        if (Jogo.getInstance().getCelula(lin, col).getPersonagem() != null) {
            return;
        } else {
            // Limpa celula atual
            Jogo.getInstance().getCelula(oldLin, oldCol).setPersonagem(null);
            // Coloca personagem na nova posição
            Jogo.getInstance().getCelula(lin, col).setPersonagem(this);

        
        }
    }
    

    @Override
    public void influenciaVizinhos() {
        // Não influencia ninguém
    }

    @Override
    public void verificaEstado() {
        // Verifica se esta vivo
        if (!this.estaVivo()) {
            return;
        }
    
        // Se esta infectado perde energia a cada passo
        if (this.infectado()) {
            diminuiEnergia(2);
            // Se não tem mais energia morre
            if (this.getEnergia() == 0) {
                this.setImage("Morto");
                this.getCelula().setImageFromPersonagem();
            }
        }
    }
}
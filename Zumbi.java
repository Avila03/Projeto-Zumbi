package poo;

public class Zumbi extends Personagem {
    public Zumbi(int linInicial, int colInicial) {
        super(10, "Zumbi", linInicial, colInicial);
    }
    
    // Caso tenha sofrido dano
    @Override
    public void danificado() {
        if (this.dano()) {
            return;
        }
        super.danificado();
        this.setImage("Rastejando");
        this.getCelula().setImageFromPersonagem();
    }

// Movimentação dos personagens
    @Override
    public void atualizaPosicao() {
        int dirLin = Jogo.getInstance().aleatorio(3) - 1;
        int dirCol = Jogo.getInstance().aleatorio(3) - 1;
        int oldLin = this.getCelula().getLinha();
        int oldCol = this.getCelula().getColuna();
        int lin = oldLin + dirLin;
        int col = oldCol + dirCol;
        if (!this.ZumbiVivo()) {
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
// Oque causa nos vizinhos ao lado
    @Override
    public void influenciaVizinhos() {
        int lin = this.getCelula().getLinha();
        int col = this.getCelula().getColuna();

        if (this.ZumbiVivo()) {
            diminuiEnergia(1);
            // Se não tem mais energia morre
            if (this.getEnergia() == 0) {
                this.setImage("MortoZumbi");
                this.getCelula().setImageFromPersonagem();
            }
        
        for (int l = lin - 1; l <= lin + 1; l++) {
            for (int c = col - 1; c <= col + 1; c++) {
                // Se a posição é dentro do tabuleiro
                if (l >= 0 && l < Jogo.NLIN && c >= 0 && c < Jogo.NCOL) {
                    // Se não é a propria celula
                    if (!(lin == l && col == c)) {
                        // Recupera o personagem da célula vizinha
                        Personagem p = Jogo.getInstance().getCelula(l, c).getPersonagem();
                        // Se não for nulo, infecta
                        if (!this.ZumbiVivo()) {
                            return;
                        }
                        if (p != null) {
                            p.infecta();
                        }
                    }
                }
            }
        }
    }

    }
// Verifica atual estado do zumbi
    @Override
    public void verificaEstado() {
        // Se esta morto retorna
        if (!this.ZumbiVivo()) {
            return;
        }
        // Se esta danificado perde energia a cada passo
        if (this.dano()) {
            diminuiEnergia(5);
            // Se não tem mais energia morre
            if (this.getEnergia() == 0) {
                this.setImage("MortoZumbi");
                this.getCelula().setImageFromPersonagem();
            }
        }
    }
}
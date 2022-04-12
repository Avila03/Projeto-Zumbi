package poo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Jogo extends Application {
    public static final int CELL_WIDTH = 60;
    public static final int CELL_HEIGHT = 60;
    public static final int NLIN = 15;
    public static final int NCOL = 15;
    public static int QTDADEBOBOES = 0;
    public static int QTDADEZUMBIS = 0;
    public static int QTDADEZUMBISTANK = 0;
    public static int QTDADESAGE = 0;
    public static int QTDADEBRAD = 0;
    public int n_zumbi = 0;
    public int n_pessoa = 0;
    public int n_rodadas = 0;
    public int count = 0;
    public Text text = new Text();
    public Text textP = new Text();
    public Text textD = new Text();
    public Text textQTAZ = new Text();
    public Text textQTAB = new Text();
    public Text textQTAS = new Text();
    public Text textQTAZTANK = new Text();
    public Text textQTABOB = new Text();

    public static Jogo jogo = null;

    private Random random;
    private Map<String, Image> imagens;
    private List<Celula> celulas;
    private List<Personagem> personagens;

    public static Jogo getInstance() {
        return jogo;
    }

    public Jogo() {
        jogo = this;
        random = new Random();
    }

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage Stage1) {

        GridPane QTA = new GridPane();
        QTA.setAlignment(Pos.CENTER);
        QTA.setHgap(10);
        QTA.setVgap(10);
        QTA.setPadding(new Insets(5, 5, 5, 5));

        // Botão para começar
        Button começar = new Button("Começar");
        HBox qTABox = new HBox(10);
        qTABox.setAlignment(Pos.CENTER);
        qTABox.getChildren().add(começar);
        QTA.add(qTABox, 0, 5, 2, 1);

        // Alterar a quantidade de zumbis
        Label QuantidadeZumbi = new Label("Quantidade de Zumbis normais na área");
        TextField ZumbiField = new TextField("0");
        QTA.add(QuantidadeZumbi, 0, 0);
        QTA.add(ZumbiField, 1, 0);

        // Alterar a quantidade de Zumbis Tanks
        Label QuantidadeZumbiTank = new Label("Quantidade de Zumbis Tank na área");
        TextField ZumbiTankField = new TextField("0");
        QTA.add(QuantidadeZumbiTank, 0, 1);
        QTA.add(ZumbiTankField, 1, 1); // Coluna , linha

        // Alterar a quantidade de Brad's
        Label QuantidadeBrad = new Label("Quantidade unidades 'Brad'");
        TextField BradField = new TextField("0");
        QTA.add(QuantidadeBrad, 0, 2);
        QTA.add(BradField, 1, 2);

        // Alterar a quantidade de Sage
        Label QuantidadeSage = new Label("Quantidade de unidades 'Sage'");
        TextField SageField = new TextField("0");
        QTA.add(QuantidadeSage, 0, 3);
        QTA.add(SageField, 1, 3);

        // Alterar a quantidade de bobões
        Label QuantidadeBobao = new Label("Quantidade de unidades 'Bobões'");
        TextField BobaoField = new TextField("0");
        QTA.add(QuantidadeBobao, 0, 4);
        QTA.add(BobaoField, 1, 4);

        começar.setOnAction(e -> {
            QTDADEZUMBIS = Integer.parseInt(ZumbiField.getText());
            QTDADEZUMBISTANK = Integer.parseInt(ZumbiTankField.getText());
            QTDADEBRAD = Integer.parseInt(BradField.getText());
            QTDADESAGE = Integer.parseInt(SageField.getText());
            QTDADEBOBOES = Integer.parseInt(BobaoField.getText());
            startt(Stage1);
        });

        Scene um = new Scene(QTA);
        Stage1.setScene(um);
        Stage1.show();

    }

    // Retorna um número aleatorio a partir do gerador unico
    public int aleatorio(int limite) {
        return random.nextInt(limite);
    }

    // Retorna a celula de uma certa linha,coluna
    public Celula getCelula(int nLin, int nCol) {
        int pos = (nLin * NCOL) + nCol;
        return celulas.get(pos);
    }

    private void loadImagens() {
        imagens = new HashMap<>();

        // Armazena as imagens dos personagens

        Image aux = new Image("file:Imagens/img16.jpg");
        imagens.put("Infectado", aux);
        aux = new Image("file:Imagens/zumbi.jpg");
        imagens.put("Zumbi", aux);
        aux = new Image("file:Imagens/img11.jpg");
        imagens.put("Brad", aux);
        aux = new Image("file:Imagens/img12.jpg");
        imagens.put("Sage", aux);
        aux = new Image("file:Imagens/img13.jpg");
        imagens.put("Normal", aux);
        aux = new Image("file:Imagens/tumulo.jpg");
        imagens.put("Morto", aux);
        aux = new Image("file:Imagens/zumimorto.jpg");
        imagens.put("MortoZumbi", aux);
        aux = new Image("file:Imagens/preto.jpg");
        imagens.put("Vazio", aux);
        aux = new Image("file:Imagens/img10.jpg");
        imagens.put("ZumbiTank", aux);
        aux = new Image("file:Imagens/img14.jpg");
        imagens.put("BradMachucado", aux);
        aux = new Image("file:Imagens/img15.jpg");
        imagens.put("SageMachucada", aux);
        aux = new Image("file:Imagens/rastejando.jpg");
        imagens.put("Rastejando", aux);
        aux = new Image("file:Imagens/tank.jpg");
        imagens.put("tank", aux);

        // Armazena a imagem da celula null
        imagens.put("Null", null);
    }

    public Image getImage(String id) {
        return imagens.get(id);
    }

    public void startt(Stage primaryStage) {
        // Carrega imagens
        {
            loadImagens();

            text.setText("Zumbis: -");
            text.setX(50);
            text.setY(50);
            textP.setText("Pessoas: -");
            textP.setX(50);
            textP.setY(50);
            textD.setText("Dias: -");
            textD.setX(50);
            textD.setY(50);

            // Configura a interface com o usuario
            primaryStage.setTitle("Walkers");
            GridPane tab = new GridPane();
            tab.setAlignment(Pos.CENTER);
            tab.setHgap(10);
            tab.setVgap(10);
            tab.setPadding(new Insets(25, 25, 25, 25));

            // Monta o "tabuleiro"
            celulas = new ArrayList<>(NLIN * NCOL);
            for (int lin = 0; lin < NLIN; lin++) {
                for (int col = 0; col < NCOL; col++) {
                    Celula cel = new Celula(lin, col);
                    celulas.add(cel);
                    tab.add(cel, col, lin);
                }
            }

            // Cria a lista de personagens
            personagens = new ArrayList<>(NLIN * NCOL);

            // Cria Bobões em posições aleatórias.
            for (int i = 0; i < QTDADEBOBOES; i++) {
                boolean posOk = false;
                while (!posOk) {
                    int lin = random.nextInt(NLIN);
                    int col = random.nextInt(NCOL);
                    if (this.getCelula(lin, col).getPersonagem() == null) {
                        personagens.add(new Bobao(lin, col));
                        posOk = true;
                    }
                }
            }

            // Cria Sages em posições aleatórias.
            for (int i = 0; i < QTDADESAGE; i++) {
                boolean posOk = false;
                while (!posOk) {
                    int lin = random.nextInt(NLIN);
                    int col = random.nextInt(NCOL);
                    if (this.getCelula(lin, col).getPersonagem() == null) {
                        personagens.add(new Sage(lin, col));
                        posOk = true;
                    }
                }
            }

            // Cria Brad's em posições aleatórias.
            for (int i = 0; i < QTDADEBRAD; i++) {
                boolean posOk = false;
                while (!posOk) {
                    int lin = random.nextInt(NLIN);
                    int col = random.nextInt(NCOL);
                    if (this.getCelula(lin, col).getPersonagem() == null) {
                        personagens.add(new Brad(lin, col));
                        posOk = true;
                    }
                }
            }

            // Cria Zumbis aleatórios
            for (int i = 0; i < QTDADEZUMBIS; i++) {
                boolean posOk = false;
                while (!posOk) {
                    int lin = random.nextInt(NLIN);
                    int col = random.nextInt(NCOL);
                    if (this.getCelula(lin, col).getPersonagem() == null) {
                        personagens.add(new Zumbi(lin, col));
                        posOk = true;
                    }
                }
            }

            // Cria Zumbis Tank aleatórios
            for (int i = 0; i < QTDADEZUMBISTANK; i++) {
                boolean posOk = false;
                while (!posOk) {
                    int lin = random.nextInt(NLIN);
                    int col = random.nextInt(NCOL);
                    if (this.getCelula(lin, col).getPersonagem() == null) {
                        personagens.add(new ZumbiTank(lin, col));
                        posOk = true;
                    }
                }
            }

            // Define o botao que avança a simulação
            Button avanca = new Button("Avançar");
            avanca.setOnAction(e -> avancaSimulacao());

            // Define o botão exit
            Button exit = new Button("Encerrar");
            exit.setOnAction(e -> exit());

            // Define um botão de Restart
            Button Restart = new Button("Recomeçar");

            Restart.setOnAction(e -> {

                Alert msgBox = new Alert(AlertType.CONFIRMATION);
                msgBox.setHeaderText("Você realmente deseja recomeçar o jogo?");
                msgBox.setContentText("Lembre-se, ao clicar em 'OK' você perderá todo seu progresso atual.");

                n_rodadas = 0;
                count = 0;
                start(primaryStage);
            });

            Button save = new Button("Salvar o jogo");
            save.setOnAction(e -> {
                try {
                    save();
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            });

            Button load = new Button("Carregar jogo salvo");
            load.setOnAction(e -> load(tab));

            // Monta a cena e exibe

            HBox B = new HBox(10);
            B.getChildren().add(tab);
            VBox hb = new VBox(10);
            hb.setAlignment(Pos.CENTER);
            hb.setPadding(new Insets(25, 25, 25, 25));
            hb.getChildren().add(avanca);
            hb.getChildren().add(exit);
            hb.getChildren().add(load);
            hb.getChildren().add(Restart);
            hb.getChildren().add(save);
            hb.getChildren().add(text);
            hb.getChildren().add(textP);
            hb.getChildren().add(textD);

            B.getChildren().add(hb);

            Scene scene = new Scene(B);
            primaryStage.setScene(scene);
        }
        primaryStage.show();
    }

    // Encerrar o jogo
    public void exit() {
        System.exit(0);
    }

    // Recomeçar o jogo
    public void reset() {
        Alert msgBox = new Alert(AlertType.CONFIRMATION);
        msgBox.setHeaderText("Você realmente deseja recomeçar o jogo?");
        msgBox.setContentText("Lembre-se, ao clicar em 'OK' você perderá todo seu progresso atual.");
        msgBox.showAndWait();

    }

    // Salvar o jogo
    public void save() throws IOException {
        try (FileWriter myWriter = new FileWriter("Save.txt")) {

            for (Personagem p : personagens) {
                myWriter.write(p.getClass().toString().substring(10) + ";");
                myWriter.write(p.getCelula().getLinha() + ";" + p.getCelula().getColuna() + ";");
                myWriter.write(p.getEnergia() + "\n");

            }

            System.out.println("Jogo salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar o jogo.");
            e.printStackTrace();
        }

    }

    // Carregar o jogo
    public void load(GridPane tab) {
        try {
            File myObj = new File("Save.txt");
            celulas = new ArrayList<>(NLIN * NCOL);
            for (int lin = 0; lin < NLIN; lin++) {
                for (int col = 0; col < NCOL; col++) {
                    Celula cel = new Celula(lin, col);
                    celulas.add(cel);
                    tab.add(cel, col, lin);
                }
            }
            Scanner myReader = new Scanner(myObj);

            personagens = new ArrayList<>(NLIN * NCOL);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] Dados = data.split(";");
                System.out.println(data);
                String Nome = Dados[0];
                int linha = Integer.parseInt(Dados[1]);
                int coluna = Integer.parseInt(Dados[2]);
                int energia = Integer.parseInt(Dados[3]);
                Personagem p = null;
                switch (Nome) {
                    case "Bobao":
                        p = new Bobao(linha, coluna);
                        p.setEnergia(energia);
                        if (p.getEnergia() == 0) {
                            p.setImage("Morto");
                            p.getCelula().setImageFromPersonagem();
                        }
                        break;

                    case "Brad":
                        p = new Brad(linha, coluna);
                        p.setEnergia(energia);
                        if (p.getEnergia() == 0) {
                            p.setImage("Morto");
                            p.getCelula().setImageFromPersonagem();
                        }
                        break;

                    case "Sage":
                        p = new Sage(linha, coluna);
                        p.setEnergia(energia);
                        if (p.getEnergia() == 0) {
                            p.setImage("Morto");
                            p.getCelula().setImageFromPersonagem();
                        }
                        break;

                    case "Zumbi":
                        p = new Zumbi(linha, coluna);
                        p.setEnergia(energia);
                        if (p.getEnergia() == 0) {
                            p.setImage("MortoZumbi");
                            p.getCelula().setImageFromPersonagem();
                        }
                        break;

                    case "ZumbiTank":
                        p = new ZumbiTank(linha, coluna);
                        p.setEnergia(energia);
                        if (p.getEnergia() == 0) {
                            p.setImage("MortoZumbi");
                            p.getCelula().setImageFromPersonagem();
                        }
                        break;
                }
                personagens.add(p);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro ao salvar o arquivo.");
            e.printStackTrace();
        }
    }

    // Avança um passo em todos os personagens
    public void avancaSimulacao() {
        count++;
        System.out.println(count);
        personagens.forEach(p -> {
            p.atualizaPosicao();
            p.verificaEstado();
            p.influenciaVizinhos();

        });
        // Verifica se ainda há humanos vivos.
        long vivos = personagens.stream()
                .filter(p -> (p instanceof Brad) || (p instanceof Sage) || (p instanceof Bobao))
                .filter(p -> p.estaVivo()).count();
        if (vivos == 0) {
            Alert msgBox = new Alert(AlertType.INFORMATION);
            msgBox.setHeaderText("Fim de Jogo!");
            msgBox.setContentText("Todos os humanos morreram!");
            msgBox.showAndWait();
            System.exit(0);
        }
        // Verifica se ainda há zumbis vivos.

        long Zumbis = (((personagens.stream()).filter(p -> ((p instanceof Zumbi) || (p instanceof ZumbiTank))))
                .filter(p -> p.ZumbiVivo())).count();
        if (Zumbis == 0) {
            Alert msgBox = new Alert(AlertType.INFORMATION);
            msgBox.setHeaderText("Fim de Jogo!");
            msgBox.setContentText("Todos os Zumbis morreram!");
            msgBox.showAndWait();
            System.exit(0);
        }

        System.out.print("Pessoas: " + vivos + '\n');
        System.out.print("Zumbis: " + Zumbis + '\n');
        System.out.print("Rodadas: " + count + '\n');

        n_zumbi = (int) Zumbis;
        n_pessoa = (int) vivos;
        text.setText("Zumbis: " + String.valueOf(n_zumbi));
        textP.setText("Pessoas: " + String.valueOf(n_pessoa));
        textD.setText("Rodadas: " + String.valueOf(count));

    }
}
package acc.br.projetoquatro.service;

import acc.br.projetoquatro.model.Score;
import acc.br.projetoquatro.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public Score getScore() {
        Score score = scoreRepository.findFirst();
        if (score == null) {
            score = new Score();
            score = scoreRepository.save(score);
        }
        return score;
    }

    public String processarEscolha(String escolhaUsuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};
        Random random = new Random();
        String escolhaComputador = opcoes[random.nextInt(opcoes.length)];

        Score score = getScore();

        String resultado = determinarResultado(escolhaUsuario, escolhaComputador);

        switch (resultado) {
            case "Vitória.":
                score.setVitorias(score.getVitorias() + 1);
                break;
            case "Derrota.":
                score.setDerrotas(score.getDerrotas() + 1);
                break;
            case "Empate.":
                score.setEmpates(score.getEmpates() + 1);
                break;
        }

        scoreRepository.save(score);

        return resultado + " (Computador escolheu: " + escolhaComputador + ")";
    }

    private String determinarResultado(String escolhaUsuario, String escolhaComputador) {
        if (escolhaUsuario.equals(escolhaComputador)) {
            return "Empate.";
        }

        if ((escolhaUsuario.equals("pedra") && escolhaComputador.equals("tesoura")) ||
            (escolhaUsuario.equals("papel") && escolhaComputador.equals("pedra")) ||
            (escolhaUsuario.equals("tesoura") && escolhaComputador.equals("papel"))) {
            return "Vitória.";
        } else {
            return "Derrota.";
        }
    }

    public void resetScore() {
        Score score = getScore();
        score.setVitorias(0);
        score.setDerrotas(0);
        score.setEmpates(0);
        scoreRepository.save(score);
    }
}
package acc.br.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import acc.br.projetoquatro.model.Score;
import acc.br.projetoquatro.service.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class WebController {
    
    private final ScoreService scoreService;

    @Autowired
    public WebController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    
    @GetMapping("/score/reset")
    @PostMapping("/score/reset")
    public String resetScore() {
        scoreService.resetScore();
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/score")
    public Score getScore() {
        return scoreService.getScore();
    }

    @GetMapping("/teste")
    public String teste(@RequestParam(name="escolha") String aEscolha, Model model) {
        String resultado = scoreService.processarEscolha(aEscolha);
        Score score = scoreService.getScore();
        
        int separadorIndex = resultado.indexOf(" (");
        String saida = resultado.substring(0, separadorIndex);
        String computadorEscolha = resultado.substring(resultado.indexOf(": ") + 2, resultado.length() - 1);

        model.addAttribute("saida", saida);
        model.addAttribute("aEscolha", aEscolha);
        model.addAttribute("computadorEscolha", computadorEscolha);
        model.addAttribute("score", score);
        return "resultado";
    }
}
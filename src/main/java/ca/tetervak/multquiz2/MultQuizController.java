package ca.tetervak.multquiz2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MultQuizController {

    private final Logger log = LoggerFactory.getLogger(MultQuizController.class);

    @RequestMapping(value={"/", "new-problem"})
    public String newProblem(HttpSession session){
        log.trace("newProblem() is called");
        MultProblem problem = new MultProblem();
        log.debug("a={}, b={}, answer={}", problem.getA(), problem.getB(), problem.getAnswer());
        session.setAttribute("problem", problem);
        return "NewProblem";
    }

    @RequestMapping( "see-answer")
    public String seeAnswer(HttpSession session){
        log.trace("seeAnswer() is called");
        MultProblem problem = (MultProblem) session.getAttribute("problem");
        if (problem == null) {
            log.debug("The session data is not found.");
            return "SessionExpired";
        } else {
            return "SeeAnswer";
        }
    }

    @RequestMapping("try-again")
    public String tryAgain(HttpSession session){
        log.trace("tryAgain() is called");
        MultProblem problem = (MultProblem) session.getAttribute("problem");
        if (problem == null) {
            log.debug("The session data is not found.");
            return "SessionExpired";
        } else {
            return "TryAgain";
        }
    }

    @RequestMapping("check-answer")
    public ModelAndView checkAnswer(
            @RequestParam String userAnswer,
            HttpSession session){
        log.trace("checkAnswer() is called");
        log.debug("userAnswer=" + userAnswer);
        ModelAndView mv;
        MultProblem problem = (MultProblem) session.getAttribute("problem");
        if (problem == null) {
            log.debug("The session data is not found.");
            mv = new ModelAndView("SessionExpired");
        } else {
            try {
                if (problem.getAnswer() == Double.parseDouble(userAnswer)) {
                    mv = new ModelAndView("RightAnswer", "userAnswer", userAnswer);
                } else {
                    mv = new ModelAndView("WrongAnswer", "userAnswer", userAnswer);
                }
            } catch(NumberFormatException e){
                mv = new ModelAndView("BadInput", "userAnswer", userAnswer);
            }
            mv.addObject("userAnswer", userAnswer);
        }
        return mv;
    }
}

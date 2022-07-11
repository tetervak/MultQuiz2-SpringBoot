package ca.tetervak.multquiz2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MultQuizController {

    @RequestMapping(value={"/", "new-problem"})
    public String newProblem(HttpSession session){
        MultProblem problem = new MultProblem();
        session.setAttribute("problem", problem);
        return "NewProblem";
    }

    @RequestMapping( "see-answer")
    public String seeAnswer(HttpSession session){
        MultProblem problem = (MultProblem) session.getAttribute("problem");
        if (problem == null) {
            return "SessionExpired";
        } else {
            return "SeeAnswer";
        }
    }

    @RequestMapping("try-again")
    public String tryAgain(HttpSession session){
        MultProblem problem = (MultProblem) session.getAttribute("problem");
        if (problem == null) {
            return "SessionExpired";
        } else {
            return "TryAgain";
        }
    }

    @RequestMapping("check-answer")
    public ModelAndView checkAnswer(
            @RequestParam String userAnswer,
            HttpSession session){

        ModelAndView mv;
        MultProblem problem = (MultProblem) session.getAttribute("problem");
        if (problem == null) {
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

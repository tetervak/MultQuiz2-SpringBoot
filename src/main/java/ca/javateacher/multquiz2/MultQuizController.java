package ca.javateacher.multquiz2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MultQuizController {

    @RequestMapping(value={"/", "InputAnswer.do"})
    public String inputAnswer(HttpSession session){
        MultProblem problem = new MultProblem();
        session.setAttribute("problem", problem);
        return "InputAnswer";
    }

    @RequestMapping("CheckAnswer.do")
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

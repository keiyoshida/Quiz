package com.example.quiz;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
	
	private UserDao userDao;
	
	private ScoreDao scoreDao;
	
	private QuestionDao questionDao;
	
	public MainController(
			final UserDao userDao,
			final ScoreDao scoreDao,
			final QuestionDao questionDao) {
		this.userDao = userDao;
		this.scoreDao = scoreDao;
		this.questionDao = questionDao;
	}

	// トップページ
	@GetMapping("/")
	public String top() {
		return "top";
	}
	
	// 新規ユーザ登録ページ
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	// 登録完了ページ
	@GetMapping("/complete-register")
	public String completeRegister() {
		return "complete-register";
	}
	
	// ユーザページ
	// ユーザページ遷移過程で取得したユーザ名を表示したトップページ
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	// ユーザページへの遷移過程
	// URLからユーザ名を取得し、ユーザページへ反映したうえでユーザページへ遷移
	@GetMapping("/index/{userName}")
	public String index(@PathVariable String userName, RedirectAttributes attr) {
		User user = userDao.findUserByName(userName);
		
		printRecentlyData(scoreDao.findRecentlyScores(userName), attr);
		
		attr.addFlashAttribute("userName", user.getName());
		attr.addFlashAttribute("userId", user.getId());
		
		return "redirect:/index";
	}
	
	// 新規ユーザ登録確認
	// 同名のユーザがすでに登録されている場合にはアラートを表示
	// 登録が完了したら登録完了ページへ遷移
	@PostMapping("/confirmation-register")
	public String newRegister(String userName, String password, RedirectAttributes attr) {
		if(userDao.findUserByName(userName) == null) {
			userDao.insert(new User(userName, password));
			return "complete-register";
		} else {
			attr.addFlashAttribute("alert", "このユーザ名は既に使用されています。");
			return "redirect:/register";
		}
	}
	
	// ログイン確認
	// 入力情報が正しければユーザページのトップへ遷移
	// 入力情報に誤りがある場合にはアラートを表示して、トップページへリダイレクト
	@PostMapping("/login")
	public String login(String userName, String password, RedirectAttributes attr) {
		User user = userDao.findUserByName(userName);
		
		if (user != null && user.getPassword().equals(password)) {
			return "redirect:/index/"+userName;
		} else {
			attr.addFlashAttribute("alert", "ユーザー名もしくはパスワードが違います。");
			return "redirect:/";
		}
	}
	
	// テスト設定画面
	@PostMapping("/test-configuration")
	public String testConfiguration(int userId, Model model) {
		model.addAttribute("userId", userId);
		
		return "test-configuration";
	}
	
	// テスト受験画面
	@PostMapping("/examination")
	public String examination(String genre, int userId, Model model) {
		
		if (genre.equals("random")) {
			printQuestions(questionDao.findQuestionAtRandom(), model);
		} else {
			printQuestions(questionDao.findQuestionByGenreAtRandom(genre), model);
		}
		
		Date dt = new Date();
		Timestamp startTime = new Timestamp(dt.getTime());
		
		model.addAttribute("userId", userId);
		model.addAttribute("genre", genre);
		model.addAttribute("startTime", startTime);
		
		return "examination";
	}
	
	// 採点処理
	// 処理完了後、採点結果ページへ遷移
	@PostMapping("/scoring")
	public String scoring(int[] sentenceId
/*			List<String> sentenceMain,
			List<String> sentenceFirst,
			List<String> sentenceSecond,
			List<String> answer,
			List<String> writeText,
			int userId,
			String genre,
			Timestamp startTime*/) {
		/*sentenceId.forEach(id -> {
			System.out.println(id);
		});*/
		List<int[]> ids = Arrays.asList(sentenceId);
		ids.forEach(id -> {
			System.out.println(id[0]);
		});
		return "scoring";
	}
	
	// ユーザトップページにそのユーザの最近の受験結果を表示する
	public void printRecentlyData(List<Score> scores, RedirectAttributes attr) {
		List<UserData> userData = new ArrayList<>();
		// stream()
		// 拡張for
		
		// foreach
		scores.forEach(score -> {
			userData.add(new UserData(score.getScore(), score.getGenre(), score.getStartDateTime()));
		});
		
		attr.addFlashAttribute("dataList", userData);
	}
	
	// 問題表示用関数(採点の際にも利用)
	public void printQuestions(List<Question> questions, Model model) {
		List<Sentence> sentences = new ArrayList<>();
		
		questions.forEach(question -> {
			sentences.add(new Sentence(
					question.getId(),
					question.getMainText(),
					question.getGenre(),
					question.getFirstText(),
					question.getSecondText(),
					"",
					false,
					"")
			);
		});
		
		model.addAttribute("sentences", sentences);
	}
}

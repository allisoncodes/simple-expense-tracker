package expensetracker.ui.controllers;


import expensetracker.ui.commands.ExpenseCommand;
import expensetracker.ui.dto.ExpenseDTO;
import expensetracker.ui.model.Expense;
import expensetracker.ui.services.AggregateService;
import expensetracker.ui.services.CategoryService;
import expensetracker.ui.services.ExpenseService;
import expensetracker.ui.util.JWTUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final AggregateService aggregateService;
    private final JWTUtils jwtUtils;

    ExpenseController(ExpenseService expenseService,
                   CategoryService categoryService,
                   AggregateService aggregateService,
                   JWTUtils jwtUtils) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.aggregateService = aggregateService;
        this.jwtUtils = jwtUtils;
    }

    @RequestMapping({"/expenses", "/expenses.html"})
    public String showExpenses(Model model,
                               @RequestParam(value = "date", required = false) String date,
                               @RequestParam(value = "amount", required = false) String amount,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "description", required = false) String description,
                               @RequestParam(value = "error", required = false) String error) {

        BearerTokenAuthenticationToken authentication = (BearerTokenAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getToken();//jwtUtils.generateJwtToken(authentication);
        //String token = authorizedClient.getAccessToken().getTokenValue();

        //JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //String token = authentication.getToken().getTokenValue();//jwtUtils.generateJwtToken(authentication);
//        OAuth2LoginAuthenticationToken authentication = (OAuth2LoginAuthenticationToken
//                ) /*(BearerTokenAuthenticationToken)*/ SecurityContextHolder.getContext().getAuthentication();
//        String token = authentication.getAccessToken().getTokenValue();//jwtUtils.generateJwtToken(authentication);


        model.addAttribute("expense", new ExpenseCommand());
        model.addAttribute("expenses", expenseService.getExpenses(token));
        model.addAttribute("categories", categoryService.getCategories(token));
        model.addAttribute("aggregates", aggregateService.getAggregates(token));

        model.addAttribute("defaultDate", date);
        model.addAttribute("defaultAmount", amount);
        model.addAttribute("defaultCategory", category);
        model.addAttribute("defaultDescription", description);
        model.addAttribute("error", error);

        return "expenses";
    }

    @PostMapping("expense")
    public String addExpense(@Valid ExpenseCommand command,
                             BindingResult result,
                             RedirectAttributes redirectAttr) {

        BearerTokenAuthenticationToken authentication = (BearerTokenAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getToken();//jwtUtils.generateJwtToken(authentication);
//        OAuth2LoginAuthenticationToken authentication = (OAuth2LoginAuthenticationToken
//                ) /*(BearerTokenAuthenticationToken)*/ SecurityContextHolder.getContext().getAuthentication();
//        String token = authentication.getAccessToken().getTokenValue();//jwtUtils.generateJwtToken(authentication);


        Expense savedExpense = !result.hasErrors() ? expenseService.saveExpenseCommand(command, token) : null;

        // If form is invalid, return with failed form data
        if (savedExpense == null) {

            String categoryDescription = categoryService.getCategoryDescription(command.getCategoryId(), token);

            // Adding null date overrides the placeholder in template
            if (command.getDate() != null && command.getDate().length() != 0) {
                redirectAttr.addAttribute("date", command.getDate());
            }
            redirectAttr.addAttribute("amount", command.getAmount());
            redirectAttr.addAttribute("category", categoryDescription);
            // Adding null description overrides the placeholder in template
            if (command.getDescription() != null && command.getDescription().length() != 0) {
                redirectAttr.addAttribute("description", command.getDescription());
            }
            redirectAttr.addAttribute("error", true);
        }

        return "redirect:/expenses";
    }
}

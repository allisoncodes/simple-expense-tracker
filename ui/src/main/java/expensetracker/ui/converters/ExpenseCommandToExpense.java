package expensetracker.ui.converters;

import expensetracker.ui.commands.ExpenseCommand;
import expensetracker.ui.dto.ExpenseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Component
public class ExpenseCommandToExpense implements Converter<ExpenseCommand, ExpenseDTO> {

    public ExpenseCommandToExpense() {
    }


    @Override
    public ExpenseDTO convert(ExpenseCommand source) {

        if (source == null) {
            return null;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(source.getDate());
        } catch (DateTimeParseException ex) {
            return null;
        }

        final ExpenseDTO expense = new ExpenseDTO(
                date,
                source.getAmount(),
                source.getCategoryId(),
                source.getDescription()
        );

        return expense;
    }
}

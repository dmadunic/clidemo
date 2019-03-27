package com.ag04.clidemo.command;

import com.ag04.clidemo.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;


@ShellComponent
public class TableExamplesCommand {

    public String[] CONTINENTS = {"Europe", "North America", "South America", "Africa", "Asia", "Austraila and Oceania"};
    public String[] COUNTRIES1 = {"Germany", "USA", "Brasil", "Nigeria", "China", "Australia"};
    public String[] COUNTRIES2 = {"France", "Canada", "Argentina", "Egypt", "India", "New Zeeland"};

    @Autowired
    ShellHelper shellHelper;

    @ShellMethod("Display sample tables")
    public void sampleTables() {
        Object[][] sampleData = new String[][] {
                CONTINENTS,
                COUNTRIES1,
                COUNTRIES2
        };
        TableModel model = new ArrayTableModel(sampleData);
        TableBuilder tableBuilder = new TableBuilder(model);

        shellHelper.printInfo("air border style");
        tableBuilder.addFullBorder(BorderStyle.air);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("fancy_light border style");
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("fancy_double border style");
        tableBuilder.addFullBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("mixed border style");
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));
    }

}

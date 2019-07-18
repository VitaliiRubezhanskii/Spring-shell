package com.homoloa.shell;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Input;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShellCommandsIntegrationTest {

    @Autowired
    private Shell shell;

    @Test
    public void testParse() {
        Map<String, MethodTarget> commands = shell.listCommands();

        MethodTarget methodTarget = commands.get("parse");
        assertThat(methodTarget, notNullValue());
        Assertions.assertThat(methodTarget.getGroup()).isEqualTo(
                "Parsing csv");
        assertThat(methodTarget.getHelp(), is("Parse csv file to Jsone."));
        assertThat(methodTarget.getMethod(), is(
                ReflectionUtils.findMethod(ShellCommands.class, "parse", String.class,
                        String.class)));
        assertThat(methodTarget.getAvailability().isAvailable(), is(true));
        assertEquals("Json file has been successfully saved", shell.evaluate(new Input() {
                    @Override
                    public String rawText() {
                        return "parse src/test/resources/test-csv/test.csv src/test/resources/test-csv/test.json";
                    }
            }
        ));
    }

    @Test
    public void testParseZip() {
        Map<String, MethodTarget> commands = shell.listCommands();

        MethodTarget methodTarget = commands.get("parse-zip");
        assertThat(methodTarget, notNullValue());
        Assertions.assertThat(methodTarget.getGroup()).isEqualTo(
                "Parsing zip csv");
        assertThat(methodTarget.getHelp(), is("Parse zip csv file to Jsone."));
        assertThat(methodTarget.getMethod(), is(
                ReflectionUtils.findMethod(ShellCommands.class, "parseZip", String.class,
                        String.class)));
        assertThat(methodTarget.getAvailability().isAvailable(), is(true));
        assertEquals("Json file has been successfully saved", shell.evaluate(new Input() {
                    @Override
                    public String rawText() {
                        return "parse-zip src/test/resources/test-csv/test.zip src/test/resources/test-csv/test-zip.json";
                    }
        }
                ));
    }
}

package com.homoloa.shell;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Input;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShellCommandsIntegrationTest {

    @Autowired
    private Shell shell;

    @Test
    public void testParseCsv() {
        Map<String, MethodTarget> commands = shell.listCommands();
        MethodTarget methodTarget = commands.get("parse");
        assertThat(methodTarget, notNullValue());
        Assertions.assertThat(methodTarget.getGroup()).isEqualTo(
                "Parsing csv");
        assertThat(methodTarget.getHelp(), is("Parse csv file to Jsone."));
        assertThat(methodTarget.getMethod(), is(
                ReflectionUtils.findMethod(ShellCommands.class, "parse", int.class,
                        int.class)));
        assertThat(methodTarget.getAvailability().isAvailable(), is(true));
        assertEquals("Parsing........", shell.evaluate(new Input() {
            public String rawText() {
                return "parse PathOne PathTwo";
            }

            public List<String> words() {
                return Arrays.asList("parse", "one", "two");
            }
        }
        ));
    }
}

package com.homoloa.shell;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.ConfigurableCommandRegistry;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.standard.StandardMethodTargetRegistrar;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ShellCommandsTest {

    private StandardMethodTargetRegistrar registrar =
            new StandardMethodTargetRegistrar();
    private ConfigurableCommandRegistry registry =
            new ConfigurableCommandRegistry();

    @Before
    public void setUp() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ShellCommands.class);
        registrar.setApplicationContext(context);
        registrar.register(registry);
    }

    @Test
    public void testParseCsv() {
        Map<String, MethodTarget> commands = registry.listCommands();

        MethodTarget methodTarget = commands.get("parse");
        assertThat(methodTarget, notNullValue());
        Assertions.assertThat(methodTarget.getGroup()).isEqualTo(
                "Parsing csv");
        assertThat(methodTarget.getHelp(), is("Parse csv file to Jsone."));
        assertThat(methodTarget.getMethod(), is(
                ReflectionUtils.findMethod(ShellCommands.class, "parse", String.class,
                        String.class)));
        assertThat(methodTarget.getAvailability().isAvailable(), is(true));
        assertEquals("Parsing........", ReflectionUtils.invokeMethod(methodTarget.getMethod(),
                methodTarget.getBean(), "one", "two"));
    }
}

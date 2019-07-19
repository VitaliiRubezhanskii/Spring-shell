//package com.homoloa.shell;
//
//import com.homoloa.service.impl.ParseServiceImpl;
//import org.assertj.core.api.Assertions;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.shell.ConfigurableCommandRegistry;
//import org.springframework.shell.MethodTarget;
//import org.springframework.shell.standard.StandardMethodTargetRegistrar;
//import org.springframework.util.ReflectionUtils;
//
//import java.util.Map;
//
//import static org.hamcrest.core.Is.is;
//import static org.hamcrest.core.IsNull.notNullValue;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//
//public class ShellCommandsTest {
//
//    private StandardMethodTargetRegistrar registrar =
//            new StandardMethodTargetRegistrar();
//    private ConfigurableCommandRegistry registry =
//            new ConfigurableCommandRegistry();
//
//    @Before
//    public void setUp() {
//        ApplicationContext context =
//                new AnnotationConfigApplicationContext(ShellCommands.class, ParseServiceImpl.class);
//        registrar.setApplicationContext(context);
//        registrar.register(registry);
//    }
//
//    @Test
//    public void testParse() {
//        Map<String, MethodTarget> commands = registry.listCommands();
//
//        MethodTarget methodTarget = commands.get("parse");
//        assertThat(methodTarget, notNullValue());
//        Assertions.assertThat(methodTarget.getGroup()).isEqualTo(
//                "Parsing csv");
//        assertThat(methodTarget.getHelp(), is("Parse csv file to Jsone."));
//        assertThat(methodTarget.getMethod(), is(
//                ReflectionUtils.findMethod(ShellCommands.class, "parse", String.class,
//                        String.class)));
//        assertThat(methodTarget.getAvailability().isAvailable(), is(true));
//        assertEquals("Json file has been successfully saved", ReflectionUtils.invokeMethod(methodTarget.getMethod(),
//                methodTarget.getBean(), "src/test/resources/test-csv/test.csv", "src/test/resources/test-csv/test.json"));
//    }
//    @Test
//    public void testParseZip() {
//        Map<String, MethodTarget> commands = registry.listCommands();
//
//        MethodTarget methodTarget = commands.get("parse-zip");
//        assertThat(methodTarget, notNullValue());
//        Assertions.assertThat(methodTarget.getGroup()).isEqualTo(
//                "Parsing zip csv");
//        assertThat(methodTarget.getHelp(), is("Parse zip csv file to Jsone."));
//        assertThat(methodTarget.getMethod(), is(
//                ReflectionUtils.findMethod(ShellCommands.class, "parseZip", String.class,
//                        String.class)));
//        assertThat(methodTarget.getAvailability().isAvailable(), is(true));
//        assertEquals("Json file has been successfully saved", ReflectionUtils.invokeMethod(methodTarget.getMethod(),
//                methodTarget.getBean(), "src/test/resources/test-csv/test.zip", "src/test/resources/test-csv/test-zip.json"));
//    }
//
//    @Test
//    public void testParseWithWrongCsvFilePath() {
//        Map<String, MethodTarget> commands = registry.listCommands();
//
//        MethodTarget methodTarget = commands.get("parse");
//        assertThat(methodTarget, notNullValue());
//
//        assertEquals("Json file hasn't been saved", ReflectionUtils.invokeMethod(methodTarget.getMethod(),
//                methodTarget.getBean(), "Wrong Path", "src/test/resources/test-csv/test.json"));
//    }
//    @Test
//    public void testParseZipWithWrongCsvFilePath() {
//        Map<String, MethodTarget> commands = registry.listCommands();
//
//        MethodTarget methodTarget = commands.get("parse-zip");
//        assertThat(methodTarget, notNullValue());
//
//        assertEquals("Json file hasn't been saved", ReflectionUtils.invokeMethod(methodTarget.getMethod(),
//                methodTarget.getBean(), "Wrong Path", "src/test/resources/test-csv/test-zip.json"));
//    }
//}

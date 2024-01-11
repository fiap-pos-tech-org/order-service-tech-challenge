package br.com.fiap.techchallenge.lanchonete;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "br.com.fiap.techchallenge.lanchonete",
        importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    @ArchTest
    static ArchRule repositoryInterfacesShouldResideInRepositoryPackage = classes().that().resideInAPackage("..adapters.repository.jpa")
            .should().haveSimpleNameEndingWith("Repository")
            .andShould().beAnnotatedWith(Repository.class)
            .as("Interfaces Repository devem ser criadas dentro do pacote adapters.repository.jpa");

    @ArchTest
    static ArchRule mapperClassesShouldResideInMappersPackage = classes().that().resideInAPackage("..adapters.repository.mappers")
            .and().resideInAPackage("..adapters.repository.mappers")
            .should().haveSimpleNameEndingWith("Mapper")
            .as("Classes Mapper devem ser implementadas dentro do pacote adapters.repository.mappers");

    @ArchTest
    static ArchRule entityClassesShouldResideInModelsPackage = classes().that().areAnnotatedWith(Entity.class)
            .should().resideInAPackage("..adapters.repository.models")
            .as("Classes de entidade devem ser implementadas dentro do pacote adapters.repository.models");

    @ArchTest
    static ArchRule repositoryClassesShouldResideInRepositoryPackage = classes().that().resideInAPackage("..adapters.repository")
            .should().haveSimpleNameEndingWith("Repository")
            .andShould().beAnnotatedWith(Repository.class)
            .as("Classes Repository devem ser implementadas dentro do pacote adapters.repository");

    @ArchTest
    static ArchRule mapperClassesShouldResideInWebMappersPackage = classes().that().resideInAPackage("..adapters.web.mappers")
            .and().resideInAPackage("..adapters.web.mappers")
            .should().haveSimpleNameEndingWith("Mapper")
            .as("Classes Mapper devem ser implementadas dentro do pacote adapters.web.mappers");

    @ArchTest
    static ArchRule controllerClassesShouldResideInWebPackage = classes().that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("..adapters.web")
            .andShould().beAnnotatedWith(RestController.class)
            .as("Classes Controller devem ser implementadas dentro do pacote adapters.web");

    @ArchTest
    static ArchRule configClassesShouldResideInConfigPackage = classes().that().haveSimpleNameEndingWith("Config")
            .should().resideInAPackage("..config")
            .as("Classes Config devem ser implementadas dentro do pacote config");

    @ArchTest
    static ArchRule exceptionClassesShouldResideInExceptionsPackage = classes().that().haveSimpleNameEndingWith("Exception")
            .should().resideInAPackage("..core.domain.exceptions")
            .as("Classes Exception devem ser implementadas dentro do pacote core.domain.exceptions");

    @ArchTest
    static ArchRule handlerClassesShouldResideInHandlersPackage = classes().that().haveSimpleNameEndingWith("Handler")
            .should().resideInAPackage("..adapters.web.handlers")
            .andShould().beAnnotatedWith(ControllerAdvice.class)
            .as("Classes Handler devem ser implementadas dentro do pacote adapters.web.handlers");

    @ArchTest
    static ArchRule enumsShouldHaveNameEndingWithEnum = classes().that().areEnums()
            .should().haveSimpleNameEndingWith("Enum")
            .andShould().resideInAPackage("..core.domain.entities.enums")
            .as("Enums devem terminar com sufixo Enum");

    @ArchTest
    static ArchRule inputPortClassesShouldResideInPortsInPackage = classes().that().haveSimpleNameEndingWith("InputPort")
            .should().resideInAPackage("..core.ports.in..")
            .as("Classes InputPort devem ser implementadas dentro do pacote core.ports.in");

    @ArchTest
    static ArchRule outputPortClassesShouldResideInPortsOutPackage = classes().that().haveSimpleNameEndingWith("OutputPort")
            .should().resideInAPackage("..core.ports.out..")
            .as("Classes OutputPort devem ser implementadas dentro do pacote core.ports.out");

    @ArchTest
    static ArchRule useCaseClassesShouldResideInUseCasesPackage = classes().that().haveSimpleNameEndingWith("UseCase")
            .should().resideInAPackage("..core.usecases..")
            .andShould().dependOnClassesThat().haveSimpleNameEndingWith("InputPort")
            .as("Classes UseCase devem ser implementadas dentro do pacote core.usecases");

}
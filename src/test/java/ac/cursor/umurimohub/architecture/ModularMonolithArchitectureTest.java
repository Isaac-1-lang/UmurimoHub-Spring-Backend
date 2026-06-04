package ac.cursor.umurimohub.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ModularMonolithArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void loadClasses() {
        classes = new ClassFileImporter().importPackages("ac.cursor.umurimohub");
    }

    @Test
    void attendance_must_not_use_foreign_repositories() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..attendance..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..auth.repository..", "..worker.repository..",
                        "..payroll.repository..", "..discipline.repository..")
                .because("Attendance module uses worker.port for worker data");

        rule.check(classes);
    }

    @Test
    void payroll_must_not_use_foreign_repositories() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..payroll..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..auth.repository..", "..worker.repository..",
                        "..attendance.repository..", "..discipline.repository..")
                .because("Payroll module uses ports for cross-module lookups");

        rule.check(classes);
    }

    @Test
    void discipline_must_not_use_foreign_repositories() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..discipline..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..auth.repository..", "..worker.repository..",
                        "..attendance.repository..", "..payroll.repository..")
                .because("Discipline module uses ports for cross-module lookups");

        rule.check(classes);
    }

    @Test
    void worker_must_not_use_foreign_repositories() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..worker..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..auth.repository..", "..attendance.repository..",
                        "..payroll.repository..", "..discipline.repository..")
                .because("Worker module uses auth.port for user data");

        rule.check(classes);
    }

    @Test
    void bounded_contexts_must_not_depend_on_foreign_jpa_entities() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..attendance..", "..payroll..", "..discipline..")
                .should().dependOnClassesThat().resideInAnyPackage("..auth.model..", "..worker.model..")
                .because("Cross-module references use Long IDs and port DTOs, not foreign JPA entities");

        rule.check(classes);
    }

    @Test
    void worker_module_must_not_depend_on_auth_infrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..worker..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..auth.service..", "..auth.repository..", "..auth.controller..")
                .because("Worker module talks to auth only via auth.port");

        rule.check(classes);
    }

    @Test
    void cross_module_controllers_use_ports_not_foreign_services() {
        ArchRule attendancePayrollDiscipline = noClasses()
                .that().resideInAnyPackage("..payroll.controller..", "..discipline.controller..", "..attendance.controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..auth.service..", "..worker.service..")
                .because("Controllers resolve cross-module data via ports");

        ArchRule workerController = noClasses()
                .that().resideInAnyPackage("..worker.controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..auth.service..")
                .because("Worker controller uses auth.port, not auth.service");

        attendancePayrollDiscipline.check(classes);
        workerController.check(classes);
    }
}

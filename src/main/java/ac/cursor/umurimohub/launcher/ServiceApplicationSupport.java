package ac.cursor.umurimohub.launcher;

import org.springframework.boot.builder.SpringApplicationBuilder;

final class ServiceApplicationSupport {

    private ServiceApplicationSupport() {
    }

    static void run(Class<?> source, String serviceProfile, String[] args) {
        new SpringApplicationBuilder(source)
                .profiles(serviceProfile, "distributed")
                .run(args);
    }
}

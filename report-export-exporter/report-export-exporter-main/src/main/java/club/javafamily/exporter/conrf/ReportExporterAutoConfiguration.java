package club.javafamily.exporter.conrf;

import club.javafamily.graph.ChartGraphService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ReportExporterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ChartGraphService chartGraphService() {
        return new ChartGraphService();
    }

}

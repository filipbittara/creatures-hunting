package cz.muni.fi.pa165.data;

import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Filip Bittara
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {DataLoadingFacadeImpl.class})
public class DataLoadingConfiguration {

    @Autowired
    DataLoadingFacade dataLoadingFacade;

    @PostConstruct
    public void dataLoading() {
        dataLoadingFacade.loadData();
    }
}

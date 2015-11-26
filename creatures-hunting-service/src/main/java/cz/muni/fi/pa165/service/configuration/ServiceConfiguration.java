package cz.muni.fi.pa165.service.configuration;

import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.service.CreatureServiceImpl;
import cz.muni.fi.pa165.service.facade.WeaponFacadeImpl;
import cz.muni.fi.pa165.service.WeaponServiceImpl;
import cz.muni.fi.pa165.service.facade.CreatureFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={WeaponServiceImpl.class, WeaponFacadeImpl.class, CreatureServiceImpl.class, CreatureFacadeImpl.class})
public class ServiceConfiguration {
	

	@Bean
	public Mapper dozer(){
		return new DozerBeanMapper();		
	}
}


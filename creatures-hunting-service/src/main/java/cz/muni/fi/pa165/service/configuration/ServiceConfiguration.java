package cz.muni.fi.pa165.service.configuration;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.User;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.CreatureServiceImpl;
import cz.muni.fi.pa165.service.facade.WeaponFacadeImpl;
import cz.muni.fi.pa165.service.WeaponServiceImpl;
import cz.muni.fi.pa165.service.facade.CreatureFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
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
		DozerBeanMapper dozer = new DozerBeanMapper();
		dozer.addMapping(new DozerCustomConfig());
		return dozer;
	}

	/**
	 * Custom config for Dozer if needed
	 * @author David Kizivat
	 *
	 */
	public class DozerCustomConfig extends BeanMappingBuilder {
		@Override
		protected void configure() {
			mapping(Creature.class, CreatureDTO.class);
			mapping(User.class, UserDTO.class);
			mapping(Weapon.class, WeaponDTO.class);
			mapping(Area.class, AreaDTO.class);
		}
	}
}


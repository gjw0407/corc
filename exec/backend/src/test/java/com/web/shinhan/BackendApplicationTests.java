package com.web.shinhan;

import com.web.shinhan.config.SecurityJavaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class BackendApplicationTests {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SecurityJavaConfig.class);

	@Test
	@DisplayName("test bean")
	void contextLoads() {
		String []beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

			if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
				Object bean = ac.getBean(beanDefinitionName);
				System.out.println("names = " + beanDefinitionNames + " obj " + bean);
			}
		}
	}

}

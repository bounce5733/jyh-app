package com.jyh.app.plat.service.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInterceptor;
import com.jyh.app.plat.service.AppConst;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @author jiangyonghua
 * @date 2018年4月21日 上午9:33:19
 */
@Component
public class MybatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {

		Configuration config = new Configuration();
		config.setLazyLoadingEnabled(true);
		config.setMapUnderscoreToCamelCase(true);

		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTypeAliasesPackage(AppConst.MODEL_PACKAGE);
		factory.setConfiguration(config);

		// 配置分页插件，详情请查阅官方文档
		PageInterceptor pageInterceptor = new PageInterceptor();
		Properties properties = new Properties();
		properties.setProperty("pageSizeZero", "true");// 分页尺寸为0时查询所有纪录不再执行分页
		properties.setProperty("reasonable", "true");// 页码<=0 查询第一页，页码>=总页数查询最后一页
		properties.setProperty("supportMethodsArguments", "true");// 支持通过 Mapper 接口参数来传递分页参数
		pageInterceptor.setProperties(properties);
		// 添加插件
		factory.setPlugins(new Interceptor[] { pageInterceptor });

		// ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		// factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
		return factory.getObject();
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		mapperScannerConfigurer.setBasePackage(AppConst.MAPPER_PACKAGE);

		// 配置通用Mapper，详情请查阅官方文档
		Properties properties = new Properties();
		properties.setProperty("mappers", AppConst.MAPPER_INTERFACE_REFERENCE);
		properties.setProperty("notEmpty", "false");// insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str
													// != ''
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);

		return mapperScannerConfigurer;
	}
}
